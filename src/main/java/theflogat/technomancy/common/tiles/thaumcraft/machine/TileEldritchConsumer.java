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

        public final static Range[] VALIDRANGES = { LARGE, SMALL, TINY, MEDIUM, AVERAGE, GIGANTIC };

        private static final String LOC = "RangeIdentificator";

        public int r;
        public int h;
        public int id;
        public String chat;

        @Override
        public String toString() {
            return chat;
        }

        Range(final int range, final int height, final int id, final String chat) {
            r = range;
            h = height;
            this.id = id;
            this.chat = chat;
        }

        public void writeToNbt(final NBTTagCompound comp) {
            comp.setInteger(LOC, id);
        }

        public Range getNext() {
            return this == VALIDRANGES[VALIDRANGES.length - 1] ? VALIDRANGES[0] : VALIDRANGES[id + 1];
        }

        public static Range readFromNbt(final NBTTagCompound comp) {
            final int i = comp.getInteger(LOC);
            for (final Range r : VALIDRANGES) {
                if (r.id == i) {
                    return r;
                }
            }
            return LARGE;
        }
    }

    public Range current = Range.LARGE;
    private final AspectList list = new AspectList();
    public int cooldown;
    public int time;
    public float panelRotation;
    public int cost = Rate.consumerCost;

    public TileEldritchConsumer() {
        super(Rate.consumerCost * 50, RedstoneSet.HIGH);
    }

    @Override
    public void updateEntity() {
        if (worldObj.isRemote) {
            if (cooldown > 0) {
                panelRotation = Math.min((float) -Math.PI / 4, panelRotation -= 0.02F);
            } else {
                if (panelRotation > 0) {
                    panelRotation = Math.min(0, panelRotation -= 0.02F);
                } else {
                    panelRotation = Math.max(0, panelRotation += 0.02F);
                }
            }
        } else {
            if (set.canRun(this)) {
                if (time <= 0) {
                    if (canFillList(list) && getEnergyStored() >= cost) {
                        boolean flag = dealWithMobs();
                        flag |= dealWithItems();
                        if (getEnergyStored() >= cost) {
                            final Coords c = seekForBlock();
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
        }
    }

    private static boolean canFillList(final AspectList list) {
        if (list.getAspects().length >= 4) {
            return false;
        }

        boolean flag = true;

        for (final Aspect as : list.getAspects()) {
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

        final ArrayList<EntityLiving> mobs = (ArrayList<EntityLiving>) worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(xCoord - current.r, current.h == -1 ? 0 : yCoord - current.h - 1, zCoord - current.r, xCoord + current.r, yCoord - 1, zCoord + current.r));

        if (!mobs.isEmpty()) {
            for (final EntityLiving mob : mobs) {
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

        final ArrayList<EntityItem> items = (ArrayList<EntityItem>) worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - current.r, current.h == -1 ? 0 : yCoord - current.h - 1, zCoord - current.r, xCoord + current.r, yCoord - 1, zCoord + current.r));

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
                for (final Aspect as : al.getAspects()) {
                    if (as != null) {
                        list.add(as, al.getAmount(as));
                    }
                }
            }
        }
        return flag;
    }

    private void dealWithFluid(final int x, final int y, final int z) {
        final Block block = worldObj.getBlock(x, y, z);

        if (block instanceof BlockFluidBase || block instanceof BlockLiquid) {
            worldObj.setBlockToAir(x, y, z);
        }
    }

    private void stopFlows(final int x, final int y, final int z) {
        final Block block = worldObj.getBlock(x, y, z);

        if (block instanceof BlockFluidBase || block instanceof BlockLiquid) {
            worldObj.setBlock(x, y, z, Blocks.stone, 0, 2);
        }
    }

    private boolean isBlockOk(final int x, final int y, final int z) {
        final Block block = worldObj.getBlock(x, y, z);

        return block != null && !block.isAir(worldObj, x, y, z) && block.getBlockHardness(worldObj, x, y, z) != -1;
    }

    private void processFromCoords(final Coords c) {
        final ArrayList<ItemStack> drops = c.w.getBlock(c.x, c.y, c.z).getDrops(worldObj, c.x, c.y, c.z, c.w.getBlockMetadata(c.x, c.y, c.z), 0);

        for (final ItemStack items : drops) {
            AspectList al = ThaumcraftCraftingManager.getObjectTags(items);
            al = ThaumcraftCraftingManager.getBonusTags(items, al);

            for (final Aspect as : al.getAspects()) {
                if (as != null) {
                    list.add(as, al.getAmount(as));
                }
            }
        }

        c.w.getBlock(c.x, c.y, c.z).breakBlock(c.w, c.x, c.y, c.z, c.w.getBlock(c.x, c.y, c.z), c.w.getBlockMetadata(c.x, c.y, c.z));
        c.w.setBlockToAir(c.x, c.y, c.z);
    }

    @Override
    public void writeSyncData(final NBTTagCompound compound) {
        super.writeSyncData(compound);
        list.writeToNBT(compound);
        compound.setInteger("cooldown", cooldown);
        current.writeToNbt(compound);
    }

    @Override
    public void readSyncData(final NBTTagCompound compound) {
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
    public void setAspects(final AspectList al) {}

    @Override
    public boolean doesContainerAccept(final Aspect paramAspect) {
        return false;
    }

    @Override
    public int addToContainer(final Aspect paramAspect, final int paramInt) {
        return 0;
    }

    @Override
    public boolean takeFromContainer(final Aspect paramAspect, final int paramInt) {
        if (!list.aspects.containsKey(paramAspect) || list.getAmount(paramAspect) < paramInt) {
            return false;
        }
        list.remove(paramAspect, paramInt);
        return true;
    }

    @Override
    public boolean takeFromContainer(final AspectList al) {
        for (final Aspect as : al.getAspects()) {
            if (!takeFromContainer(as, al.getAmount(as))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean doesContainerContainAmount(final Aspect as, final int amount) {
        return list.aspects.containsKey(as) && list.getAmount(as) >= amount;
    }

    @Override
    public boolean doesContainerContain(final AspectList al) {
        for (final Aspect as : al.getAspects()) {
            if (!doesContainerContainAmount(as, al.getAmount(as))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int containerContains(final Aspect as) {
        return list.aspects.containsKey(as) ? list.getAmount(as) : 0;
    }

    @Override
    public boolean isConnectable(final ForgeDirection dir) {
        return true;
    }

    @Override
    public boolean canInputFrom(final ForgeDirection dir) {
        return false;
    }

    @Override
    public boolean canOutputTo(final ForgeDirection dir) {
        return true;
    }

    @Override
    public void setSuction(final Aspect paramAspect, final int paramInt) {}

    @Override
    public Aspect getSuctionType(final ForgeDirection dir) {
        return null;
    }

    @Override
    public int getSuctionAmount(final ForgeDirection dir) {
        return 0;
    }

    @Override
    public int takeEssentia(final Aspect paramAspect, final int paramInt, final ForgeDirection dir) {
        int amountToRemove = 0;
        if (list.aspects.containsKey(paramAspect)) {
            amountToRemove = Math.min(paramInt, list.getAmount(paramAspect));
            list.remove(paramAspect, amountToRemove);
        }

        return amountToRemove;
    }

    @Override
    public int addEssentia(final Aspect paramAspect, final int paramInt, final ForgeDirection dir) {
        return 0;
    }

    @Override
    public Aspect getEssentiaType(final ForgeDirection dir) {
        return list.getAspects()[0];
    }

    @Override
    public int getEssentiaAmount(final ForgeDirection dir) {
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
