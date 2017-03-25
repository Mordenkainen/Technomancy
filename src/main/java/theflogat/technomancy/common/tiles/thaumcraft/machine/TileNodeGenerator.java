package theflogat.technomancy.common.tiles.thaumcraft.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.api.wands.IWandable;
import thaumcraft.client.fx.bolt.FXLightningBolt;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
import thaumcraft.common.lib.world.biomes.BiomeHandler;
import thaumcraft.common.tiles.TileNode;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.common.tiles.base.IUpgradable;
import theflogat.technomancy.common.tiles.base.IWrenchable;
import theflogat.technomancy.common.tiles.base.TileMachineRedstone;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.util.helpers.MathHelper;
import theflogat.technomancy.util.helpers.WorldHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileNodeGenerator extends TileMachineRedstone implements IEssentiaTransport, IAspectContainer, IWandable, IUpgradable, IWrenchable {

    private boolean regenDummyBlocks = true;
    private Aspect aspect;
    public int amount;
    private final static int MAXAMOUNT = 256;
    public boolean active;
    private boolean canSpawn;
    private boolean addNode;
    public byte facing = 2;
    public int rotation;
    private boolean initiator;
    public boolean running;
    public int step;
    public boolean boost;
    private static Map<Byte, double[][]> lightning = new HashMap<Byte, double[][]>();

    static {
        lightning.put((byte) 2, new double[][] { { 1.5, 2.5, -.5, -.5, .5, -4.5, 0 }, { -.5, 2.5, -.5, 1.5, .5, -4.5, 0 }, { 1.5, .5, -.5, -.5, 2.5, -4.5, 0 }, { -.5, .5, -.5, 1.5, 2.5, -4.5, 0 } });
        lightning.put((byte) 3, new double[][] { { -.5, 2.5, 1.5, 1.5, .5, 5.5, 5 }, { 1.5, 2.5, 1.5, -.5, .5, 5.5, 5 }, { 1.5, .5, 1.5, -.5, 2.5, 5.5, 5 }, { -.5, .5, 1.5, 1.5, 2.5, 5.5, 5 } });
        lightning.put((byte) 4, new double[][] { { -.5, 2.5, 1.5, -4.5, .5, -.5, 0 }, { -.5, 2.5, -.5, -4.5, .5, 1.5, 0 }, { -.5, .5, 1.5, -4.5, 2.5, -.5, 0 }, { -.5, .5, -.5, -4.5, 2.5, 1.5, 0 } });
        lightning.put((byte) 5, new double[][] { { 1.5, 2.5, -.5, 5.5, .5, 1.5, 5 }, { 1.5, 2.5, 1.5, 5.5, .5, -.5, 5 }, { 1.5, .5, 1.5, 5.5, 2.5, -.5, 5 }, { 1.5, .5, -.5, 5.5, 2.5, 1.5, 5 } });
    }

    public TileNodeGenerator() {
        super(50000000, RedstoneSet.LOW);
    }

    @Override
    public void updateEntity() {
        if (isWholeTileLoaded()) {
            if (regenDummyBlocks) {
                destroyDummyBlocks();
                createDummyBlocks();
                regenDummyBlocks = false;
            }
            if (worldObj.isRemote) {
                if (active) {
                    rotation += 1 + step / 5;
                    if (rotation >= 360) {
                        rotation -= 360;
                    }
                    if (worldObj.rand.nextInt(Math.max(60 - step / 4, 1)) == 0) {
                        shootLightning();
                    }
                }
            } else {
                final TileNodeGenerator partner = getTE(worldObj, xCoord + ForgeDirection.getOrientation(facing).offsetX * 6, yCoord, zCoord + ForgeDirection.getOrientation(facing).offsetZ * 6);
                active = partner != null ? partner.isWholeTileLoaded() ? ForgeDirection.getOrientation(facing).getOpposite() == ForgeDirection.getOrientation(partner.facing) : false : false;
                if (active) {
                    int xx = xCoord + ForgeDirection.getOrientation(facing).offsetX * 3;
                    int zz = zCoord + ForgeDirection.getOrientation(facing).offsetZ * 3;
                    TileEntity entity = worldObj.getTileEntity(xx, yCoord + 1, zz);
                    if (entity == null && worldObj.isAirBlock(xx, yCoord + 1, zz)) {
                        canSpawn = true;
                        addNode = false;
                    } else if (entity instanceof TileNode) {
                        INode node = (INode) entity;
                        addNode = true;
                        if (canRun() && !worldObj.isRemote && amount > 0 && aspect != null) {
                            if (getEnergyStored() >= 1000 && node.getAspects().aspects.containsKey(aspect) && node.getAspects().getAmount(aspect) < node.getAspectsBase().getAmount(aspect)) {
                                extractEnergy(1000, false);
                                node.addToContainer(aspect, 1);
                                takeFromContainer(aspect, 1);
                                worldObj.markBlockForUpdate(xx, yCoord + 1, zz);
                            } else if (boost && getEnergyStored() >= 10000 && amount >= 10) {
                                if (node.getNodeVisBase(aspect) < Short.MAX_VALUE) {
                                    extractEnergy(10000, false);
                                    node.setNodeVisBase(aspect, (short) (node.getNodeVisBase(aspect) + 1));
                                    node.addToContainer(aspect, 1);
                                    takeFromContainer(aspect, 10);
                                    worldObj.markBlockForUpdate(xx, yCoord + 1, zz);
                                }
                            }
                        }
                        canSpawn = running = initiator = false;
                        step = 0;
                    }
                    if (step == 200 && initiator) {
                        AspectList al = new AspectList();
                        al.add(aspect, amount);
                        al.add(partner.aspect, partner.amount);
                        generateNode(al);
                        takeFromContainer(aspect, amount);
                        partner.takeFromContainer(partner.aspect, partner.amount);
                        extractEnergy(MathHelper.round(Math.pow((amount + partner.amount) / 2, 2) * 762.939453125), false);
                        worldObj.markBlockForUpdate(xx, yCoord + 1, zz);
                    }
                    fill();
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                }
            }

            if (active) {
                if (running) {
                    step++;
                }
            } else {
                canSpawn = addNode = running = initiator = false;
                step = 0;
            }
        }
    }

    private boolean isWholeTileLoaded() {
        for (int w = -1; w < 2; w++) {
            if (facing == 2 || facing == 3) {
                if (!WorldHelper.isChunkLoaded(worldObj, xCoord + w, zCoord))
                    return false;
            } else {
                if (!WorldHelper.isChunkLoaded(worldObj, xCoord, zCoord + w))
                    return false;
            }
        }
        return true;
    }

    private void createDummyBlocks() {
        for (int h = 0; h < 3; h++) {
            for (int w = -1; w < 2; w++) {
                if (!(h == 0 && w == 0)) {
                    if (facing == 2 || facing == 3) {
                        if (WorldHelper.destroyAndDrop(worldObj, xCoord + w, yCoord + h, zCoord)) {
                            worldObj.setBlock(xCoord + w, yCoord + h, zCoord, TMBlocks.fakeAirNG);
                            ((TileFakeAirNG) worldObj.getTileEntity(xCoord + w, yCoord + h, zCoord)).addMain(xCoord, yCoord, zCoord);
                        } else {
                            WorldHelper.destroyAndDrop(worldObj, xCoord, yCoord, zCoord);
                            return;
                        }
                    } else {
                        if (WorldHelper.destroyAndDrop(worldObj, xCoord, yCoord + h, zCoord + w)) {
                            worldObj.setBlock(xCoord, yCoord + h, zCoord + w, TMBlocks.fakeAirNG);
                            ((TileFakeAirNG) worldObj.getTileEntity(xCoord, yCoord + h, zCoord + w)).addMain(xCoord, yCoord, zCoord);
                        } else {
                            WorldHelper.destroyAndDrop(worldObj, xCoord, yCoord, zCoord);
                            return;
                        }
                    }
                }
            }
        }
    }

    private void destroyDummyBlocks() {
        for (int h = 0; h < 3; h++) {
            for (int w = -1; w < 2; w++) {
                if (!(h == 0 && w == 0)) {
                    if (facing == 2 || facing == 3) {
                        worldObj.setBlockToAir(xCoord + w, yCoord + h, zCoord);
                    } else {
                        worldObj.setBlockToAir(xCoord, yCoord + h, zCoord + w);
                    }
                }
            }
        }
    }

    // Just using as a reference
    // NORMAL, UNSTABLE, DARK, TAINTED, HUNGRY, PURE
    void generateNode(AspectList aspects) {
        int xx = xCoord + ForgeDirection.getOrientation(facing).offsetX * 3;
        int zz = zCoord + ForgeDirection.getOrientation(facing).offsetZ * 3;
        if (worldObj.isRemote) {
            for (int a = 0; a < 6; a++) {
                for (int b = 0; b < 6; b++) {
                    float fx = (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.5F;
                    float fy = (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.5F;
                    float fz = (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.5F;
                    Thaumcraft.wispFX3(worldObj, xx + .5 + fx, yCoord + 1.5 + fy, zz + .5 + fz, xx + .5 + fx * 10.0F, yCoord + 1.5 + fy * 10.0F, zz + .5 + fz * 10.0F, 0.4F, b, true, 0.05F);
                }
            }
            worldObj.playSound(xx + 0.5F, yCoord + 1.5, zz + 0.5F, "thaumcraft:craftstart", 1.0F, 1.0F, false);
        } else {
            NodeType type = NodeType.NORMAL;
            NodeModifier mod = null;
            Aspect ra = null;
            int aurum = aspects.getAmount(Aspect.AURA);
            int taint = aspects.getAmount(Aspect.TAINT);
            ra = BiomeHandler.getRandomBiomeTag(worldObj.getBiomeGenForCoords(xx, zz).biomeID, worldObj.rand);
            if (ra == null) {
                ArrayList<Aspect> primals = Aspect.getPrimalAspects();
                ra = primals.get(worldObj.rand.nextInt(primals.size()));
            }
            if (aurum == taint && (aurum + taint == 122 || aurum + taint == 152 || aurum + taint == 218 || aurum + taint == 510)) {
                type = NodeType.PURE;
            } else if (aurum + taint > 256) {
                type = NodeType.HUNGRY;
            } else if (aurum - 64 > taint) {
                type = NodeType.UNSTABLE;
            } else if (taint - 64 > aurum) {
                if (taint > 96) {
                    type = NodeType.TAINTED;
                } else {
                    type = NodeType.DARK;
                }
            }
            if (aurum + taint < 80) {
                mod = NodeModifier.FADING;
            } else if (aurum + taint > 200 && aurum + taint < 256 || aurum + taint == 510) {
                mod = NodeModifier.BRIGHT;
            } else if (aurum + taint > 350 && aurum + taint != 510) {
                mod = NodeModifier.PALE;
            }
            ThaumcraftWorldGenerator.createNodeAt(worldObj, xx, yCoord + 1, zz, type, mod, new AspectList().add(ra, (aurum + taint) / 2));
        }
    }

    void fill() {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (dir != ForgeDirection.getOrientation(facing)) {
                TileEntity te = Thaumcraft.getConnectableTile(worldObj, xCoord, yCoord, zCoord, dir);
                if (te != null) {
                    IEssentiaTransport ic = (IEssentiaTransport) te;
                    Aspect ta = ic.getEssentiaType(dir.getOpposite());
                    if (ic.getEssentiaAmount(dir.getOpposite()) > 0 && ic.getSuctionAmount(dir.getOpposite()) < getSuctionAmount(null) && getSuctionAmount(null) >= ic.getMinimumSuction()) {
                        addToContainer(ta, ic.takeEssentia(ta, 1, dir.getOpposite()));
                    }
                }
            }
        }
    }

    void shootLightning() {
        double[] boltdata = lightning.get(facing)[worldObj.rand.nextInt(4)];
        FXLightningBolt bolt = new FXLightningBolt(worldObj, xCoord + boltdata[0], yCoord + boltdata[1], zCoord + boltdata[2], xCoord + boltdata[3], yCoord + boltdata[4], zCoord + boltdata[5], worldObj.rand.nextLong(), 6, 0.5F);
        bolt.defaultFractal();
        bolt.setType((int) boltdata[6]);
        bolt.setWidth(0.02F);
        bolt.finalizeBolt();
        worldObj.playSound(xCoord + 0.5F, yCoord + 0.5F, zCoord + 0.5F, "thaumcraft:zap", 0.5F, worldObj.rand.nextFloat(), false);
    }

    @Override
    public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md) {
        if (canRun() && player != null && !player.isSneaking() && world != null && active && canSpawn && !running) {
            int xx = xCoord + ForgeDirection.getOrientation(facing).offsetX * 6;
            int zz = zCoord + ForgeDirection.getOrientation(facing).offsetZ * 6;
            TileNodeGenerator partner = getTE(worldObj, xx, yCoord, zz);
            if (partner != null && amount + partner.amount > 64) {
                if (getEnergyStored() >= MathHelper.round(Math.pow((amount + partner.amount) / 2, 2) * 762.939453125)) {
                    initiator = true;
                    partner.running = running = true;
                    partner.step = step = 0;
                    if (world.isRemote) {
                        player.swingItem();
                        world.playSound(x, y, z, "thaumcraft:wand", 1.0F, 1.0F, false);
                    }
                    return 0;
                }
            }
        }
        return -1;
    }

    @Override
    public ItemStack onWandRightClick(World world, ItemStack items, EntityPlayer player) {
        return items;
    }

    @Override
    public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {}

    @Override
    public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {}

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        if (this.facing == 1 || this.facing == 3) {
            return AxisAlignedBB.getBoundingBox(this.xCoord - 1.0F, this.yCoord, this.zCoord, this.xCoord + 2.0F, this.yCoord + 3.0F, this.zCoord + 1.0F);
        } else {
            return AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord - 1.0F, this.xCoord + 1.0F, this.yCoord + 3.0F, this.zCoord + 2.0F);
        }
    }

    @Override
    public void writeSyncData(NBTTagCompound compound) {
        super.writeSyncData(compound);
        if (aspect != null) {
            compound.setString("Aspect", aspect.getTag());
        }
        compound.setShort("Amount", (short) amount);
        compound.setBoolean("Active", active);
        compound.setBoolean("Spawn", canSpawn);
        compound.setByte("Facing", facing);
        compound.setBoolean("Running", running);
        compound.setBoolean("Initiator", initiator);
        compound.setInteger("Step", step);
        compound.setBoolean("Boost", boost);
        compound.setBoolean("RegenDummyBlocks", regenDummyBlocks);
    }

    @Override
    public void readSyncData(NBTTagCompound compound) {
        super.readSyncData(compound);
        aspect = Aspect.getAspect(compound.getString("Aspect"));
        amount = compound.getShort("Amount");
        active = compound.getBoolean("Active");
        canSpawn = compound.getBoolean("Spawn");
        facing = compound.getByte("Facing");
        running = compound.getBoolean("Running");
        initiator = compound.getBoolean("Initiator");
        step = compound.getInteger("Step");
        boost = compound.getBoolean("Boost");
        regenDummyBlocks = compound.getBoolean("RegenDummyBlocks");
    }

    @Override
    public AspectList getAspects() {
        AspectList al = new AspectList();
        if (aspect != null && amount > 0) {
            al.add(aspect, amount);
        }
        return al;
    }

    @Override
    public int addToContainer(Aspect tag, int amt) {
        if (amt != 0 && (amount < MAXAMOUNT && tag == aspect || amount == 0)) {
            aspect = tag;
            int added = Math.min(amt, MAXAMOUNT - amount);
            amount += added;
            amt -= added;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
        return amt;
    }

    @Override
    public boolean takeFromContainer(Aspect tag, int amt) {
        if (doesContainerContainAmount(tag, amt)) {
            amount -= amt;
            if (amount <= 0) {
                aspect = null;
                amount = 0;
            }
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            return true;
        }
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList ot) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect tag, int amt) {
        return amount >= amt && tag == aspect;
    }

    @Override
    public int containerContains(Aspect tag) {
        return aspect == tag ? amount : 0;
    }

    @Override
    public boolean isConnectable(ForgeDirection face) {
        return face != ForgeDirection.getOrientation(facing);
    }

    @Override
    public boolean canInputFrom(ForgeDirection face) {
        return face != ForgeDirection.getOrientation(facing);
    }

    @Override
    public boolean canOutputTo(ForgeDirection face) {
        return face != ForgeDirection.getOrientation(facing);
    }

    @Override
    public void setSuction(Aspect aspect, int amount) {}

    @Override
    public int takeEssentia(Aspect aspect, int amount, ForgeDirection dir) {
        return takeFromContainer(aspect, amount) ? amount : 0;
    }

    @Override
    public int getMinimumSuction() {
        return 32;
    }

    @Override
    public boolean renderExtendedTube() {
        return true;
    }

    @Override
    public void setAspects(AspectList aspects) {}

    @Override
    public boolean doesContainerAccept(Aspect tag) {
        if (!addNode && active) {
            if ((facing == 2 || facing == 4)) {
                return tag == Aspect.AURA && amount < MAXAMOUNT;
            } else {
                return tag == Aspect.TAINT && amount < MAXAMOUNT;
            }
        } else {
            return aspect == null ? true : (aspect == tag && amount < MAXAMOUNT);
        }
    }

    @Override
    public Aspect getSuctionType(ForgeDirection face) {
        if (aspect != null) {
            return aspect;
        }
        if (!addNode && active) {
            if ((facing == 2 || facing == 4)) {
                return Aspect.AURA;
            } else {
                return Aspect.TAINT;
            }
        }
        return null;
    }

    @Override
    public int getSuctionAmount(ForgeDirection face) {
        return amount < MAXAMOUNT ? 48 : 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, ForgeDirection dir) {
        return amount - addToContainer(aspect, amount);
    }

    @Override
    public Aspect getEssentiaType(ForgeDirection face) {
        return aspect;
    }

    @Override
    public int getEssentiaAmount(ForgeDirection face) {
        return amount;
    }

    @Override
    public boolean doesContainerContain(AspectList ot) {
        if (aspect != null && amount > 0 && ot.size() == 1) {
            if (ot.getAspects()[0] == aspect && ot.getAmount(aspect) <= amount) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean toggleBoost() {
        boost = !boost;
        return boost;
    }

    @Override
    public boolean isBoosted() {
        return boost;
    }

    @Override
    public void setBoost(boolean newBoost) {
        boost = newBoost;
    }

    private static TileNodeGenerator getTE(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        return tile instanceof TileNodeGenerator ? (TileNodeGenerator) tile : null;
    }

    public boolean canRun() {
        boolean state = true;
        for (int h = 0; h < 3 && state; h++) {
            for (int w = -1; w < 2 && state; w++) {
                if (facing == 2 || facing == 3) {
                    state = set.canRun(worldObj, xCoord + w, yCoord + h, zCoord);
                } else {
                    state = set.canRun(worldObj, xCoord, yCoord + h, zCoord + w);
                }
            }
        }
        return state;
    }

    @Override
    public boolean onWrenched(boolean sneaking) {
        if (sneaking) {
            destroyDummyBlocks();
            if (!worldObj.isRemote) {
                facing = (byte) (facing == 5 ? 2 : facing + 1);
                regenDummyBlocks = true;
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
        return false;
    }

    @Override
    public String getInfo() {
        return "Add Apsects And Increase Vis";
    }
}