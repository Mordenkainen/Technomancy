package theflogat.technomancy.common.tiles.thaumcraft.machine;

import java.util.ArrayList;

import cpw.mods.fml.common.Optional;
import me.jezza.thaumicpipes.api.interfaces.IThaumicOutput;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidBase;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import theflogat.technomancy.common.tiles.base.TileMachineRedstone;
import theflogat.technomancy.lib.handlers.Rate;
import theflogat.technomancy.util.Coords;

@Optional.Interface(iface = "me.jezza.thaumicpipes.api.interfaces.IThaumicOutput", modid = "ThaumicPipes")
public class TileEldritchConsumer extends TileMachineRedstone implements IAspectContainer, IEssentiaTransport, IThaumicOutput {

    public enum Range {
        LARGE(9, -1, 0, "Large"),
        SMALL(1, 1, 1, "Small"),
        TINY(0, 1, 2, "Tiny"),
        MEDIUM(5, -1, 3, "Medium"),
        AVERAGE(4, 9, 4, "Average"),
        GIGANTIC(16, -1, 5, "Gigantic");

        static Range[] ValidRanges = { LARGE, SMALL, TINY, MEDIUM, AVERAGE, GIGANTIC };

        private static final String loc = "RangeIdentificator";

        public int r;
        public int h;
        public int id;
        public String chat;

        @Override
        public String toString() {
            return chat;
        }

        Range(int range, int height, int id, String chat) {
            r = range;
            h = height;
            this.id = id;
            this.chat = chat;
        }

        public void writeToNbt(NBTTagCompound comp) {
            comp.setInteger(loc, id);
        }

        public Range getNext() {
            return this == ValidRanges[ValidRanges.length - 1] ? ValidRanges[0] : ValidRanges[id + 1];
        }

        public static Range readFromNbt(NBTTagCompound comp) {
            int i = comp.getInteger(loc);
            for (Range r : ValidRanges) {
                if (r.id == i) {
                    return r;
                }
            }
            return LARGE;
        }
    }

    public Range current = Range.LARGE;
    AspectList list = new AspectList();
    public int cooldown = 0;
    public int time = 0;
    public float panelRotation = 0;
    public int cost = Rate.consumerCost;

    public TileEldritchConsumer() {
        super(Rate.consumerCost * 50, RedstoneSet.HIGH);
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (set.canRun(this)) {
                if (time <= 0) {
                    if (canFillList(list) && getEnergyStored() >= cost) {
                        boolean flag = dealWithMobs();
                        flag |= dealWithItems();
                        if (getEnergyStored() >= cost) {
                            Coords c = seekForBlock();
                            if (c != null) {
                                processFromCoords(c);
                                extractEnergy(cost, false);
                                flag = true;
                            }
                        }
                        if (flag) {
                            cooldown = 40;
                            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                        } else {
                            time = 80;
                        }
                    }
                } else {
                    time--;
                }
            }

            if (cooldown > 0) {
                cooldown--;
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        } else {
            if (cooldown > 0) {
                panelRotation = Math.min((float) -Math.PI / 4, panelRotation -= 0.02F);
            } else {
                if (panelRotation > 0) {
                    panelRotation = Math.min(0, panelRotation -= 0.02F);
                } else {
                    panelRotation = Math.max(0, panelRotation += 0.02F);
                }
            }
        }
    }

    private static boolean canFillList(AspectList list) {
        if (list.getAspects().length >= 4) {
            return false;
        }

        boolean flag = true;

        for (Aspect as : list.getAspects()) {
            if (list.getAmount(as) > 4) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    private Coords seekForBlock() {
        for (int yy = yCoord - 1; yy > (current.h == -1 ? 0 : yCoord - current.h - 1); yy--) {
            for (int xx = -current.r; xx <= current.r; xx++) {
                for (int zz = -current.r; zz <= current.r; zz++) {
                    if (xx == -current.r || xx == current.r) {
                        stopFlows(xx == -current.r ? xCoord + xx - 1 : xCoord + xx + 1, yy, zCoord + zz);
                    }
                    if (zz == -current.r || zz == current.r) {
                        stopFlows(xCoord + xx, yy, zz == -current.r ? zCoord + zz - 1 : zCoord + zz + 1);
                    }
                    dealWithFluid(xCoord + xx, yy, zCoord + zz);
                    if (isBlockOk(xCoord + xx, yy, zCoord + zz)) {
                        return new Coords(xCoord + xx, yy, zCoord + zz, worldObj);
                    }
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private boolean dealWithMobs() {
        boolean flag = false;

        ArrayList<EntityLiving> mobs = (ArrayList<EntityLiving>) worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(xCoord - current.r, current.h == -1 ? 0 : yCoord - current.h - 1, zCoord - current.r, xCoord + current.r, yCoord - 1, zCoord + current.r));

        if (!mobs.isEmpty()) {
            for (EntityLiving mob : mobs) {
                if (!mob.isDead && mob.deathTime <= 0 && !mob.isEntityInvulnerable()) {
                    if (getEnergyStored() < cost) {
                        break;
                    }
                    flag = true;
                    extractEnergy(cost, false);
                    mob.onDeath(DamageSource.magic);
                    mob.setDead();
                }
            }
        }
        return flag;
    }

    @SuppressWarnings("unchecked")
    private boolean dealWithItems() {
        boolean flag = false;

        ArrayList<EntityItem> items = (ArrayList<EntityItem>) worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - current.r, current.h == -1 ? 0 : yCoord - current.h - 1, zCoord - current.r, xCoord + current.r, yCoord - 1, zCoord + current.r));

        if (!items.isEmpty()) {
            for (EntityItem item : items) {
                if (getEnergyStored() < cost) {
                    break;
                }
                flag = true;
                extractEnergy(cost, false);
                AspectList al = ThaumcraftCraftingManager.getObjectTags(item.getEntityItem());
                al = ThaumcraftCraftingManager.getBonusTags(item.getEntityItem(), al);
                if (--item.getEntityItem().stackSize <= 0) {
                    item.setDead();
                }
                for (Aspect as : al.getAspects()) {
                    if (as != null) {
                        int amount = al.getAmount(as);
                        list.add(as, amount);
                    }
                }
            }
        }
        return flag;
    }

    private void dealWithFluid(int x, int y, int z) {
        Block block = worldObj.getBlock(x, y, z);

        if (block instanceof BlockFluidBase || block instanceof BlockLiquid) {
            worldObj.setBlockToAir(x, y, z);
        }
    }

    private void stopFlows(int x, int y, int z) {
        Block block = worldObj.getBlock(x, y, z);

        if (block instanceof BlockFluidBase || block instanceof BlockLiquid) {
            worldObj.setBlock(x, y, z, Blocks.stone, 0, 2);
        }
    }

    private boolean isBlockOk(int x, int y, int z) {
        Block block = worldObj.getBlock(x, y, z);

        if (block == null || block.isAir(worldObj, x, y, z) || block.getBlockHardness(worldObj, x, y, z) == -1) {
            return false;
        }

        return true;
    }

    private void processFromCoords(Coords c) {
        ArrayList<ItemStack> drops = c.w.getBlock(c.x, c.y, c.z).getDrops(worldObj, c.x, c.y, c.z, c.w.getBlockMetadata(c.x, c.y, c.z), 0);

        for (ItemStack items : drops) {
            AspectList al = ThaumcraftCraftingManager.getObjectTags(items);
            al = ThaumcraftCraftingManager.getBonusTags(items, al);

            for (Aspect as : al.getAspects()) {
                if (as != null) {
                    int amount = al.getAmount(as);
                    list.add(as, amount);
                }
            }
        }

        c.w.getBlock(c.x, c.y, c.z).breakBlock(c.w, c.x, c.y, c.z, c.w.getBlock(c.x, c.y, c.z), c.w.getBlockMetadata(c.x, c.y, c.z));
        c.w.setBlockToAir(c.x, c.y, c.z);
    }

    @Override
    public void writeSyncData(NBTTagCompound compound) {
        super.writeSyncData(compound);
        list.writeToNBT(compound);
        compound.setInteger("cooldown", cooldown);
        current.writeToNbt(compound);
    }

    @Override
    public void readSyncData(NBTTagCompound compound) {
        super.readSyncData(compound);
        list.readFromNBT(compound);
        cooldown = compound.getInteger("cooldown");
        current = Range.readFromNbt(compound);
    }

    @Override
    public AspectList getAspects() {
        return list;
    }

    @Override
    public void setAspects(AspectList al) {}

    @Override
    public boolean doesContainerAccept(Aspect paramAspect) {
        return false;
    }

    @Override
    public int addToContainer(Aspect paramAspect, int paramInt) {
        return 0;
    }

    @Override
    public boolean takeFromContainer(Aspect paramAspect, int paramInt) {
        if (!list.aspects.containsKey(paramAspect) || list.getAmount(paramAspect) < paramInt) {
            return false;
        }
        list.remove(paramAspect, paramInt);
        return true;
    }

    @Override
    public boolean takeFromContainer(AspectList al) {
        for (Aspect as : al.getAspects()) {
            if (!takeFromContainer(as, al.getAmount(as))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect as, int amount) {
        return list.aspects.containsKey(as) && list.getAmount(as) >= amount;
    }

    @Override
    public boolean doesContainerContain(AspectList al) {
        for (Aspect as : al.getAspects()) {
            if (!doesContainerContainAmount(as, al.getAmount(as))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int containerContains(Aspect as) {
        return list.aspects.containsKey(as) ? list.getAmount(as) : 0;
    }

    @Override
    public boolean isConnectable(ForgeDirection paramForgeDirection) {
        return true;
    }

    @Override
    public boolean canInputFrom(ForgeDirection paramForgeDirection) {
        return false;
    }

    @Override
    public boolean canOutputTo(ForgeDirection paramForgeDirection) {
        return true;
    }

    @Override
    public void setSuction(Aspect paramAspect, int paramInt) {}

    @Override
    public Aspect getSuctionType(ForgeDirection paramForgeDirection) {
        return null;
    }

    @Override
    public int getSuctionAmount(ForgeDirection paramForgeDirection) {
        return 0;
    }

    @Override
    public int takeEssentia(Aspect paramAspect, int paramInt, ForgeDirection paramForgeDirection) {
        int amountToRemove = 0;
        if (list.aspects.containsKey(paramAspect)) {
            amountToRemove = Math.min(paramInt, list.getAmount(paramAspect));
            list.remove(paramAspect, amountToRemove);
        }

        return amountToRemove;
    }

    @Override
    public int addEssentia(Aspect paramAspect, int paramInt, ForgeDirection paramForgeDirection) {
        return 0;
    }

    @Override
    public Aspect getEssentiaType(ForgeDirection paramForgeDirection) {
        return list.getAspects()[0];
    }

    @Override
    public int getEssentiaAmount(ForgeDirection paramForgeDirection) {
        return list.getAmount(list.getAspects()[0]);
    }

    @Override
    public int getMinimumSuction() {
        return 0;
    }

    @Override
    public boolean renderExtendedTube() {
        return true;
    }
}
