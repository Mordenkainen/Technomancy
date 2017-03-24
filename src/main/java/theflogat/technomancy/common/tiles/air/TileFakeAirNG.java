package theflogat.technomancy.common.tiles.air;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.wands.IWandable;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockNodeGenerator;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.base.IUpgradable;
import theflogat.technomancy.common.tiles.base.IWrenchable;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.util.Coords;

public class TileFakeAirNG extends TileFakeAirCore implements IEnergyHandler, IEssentiaTransport, IAspectContainer, IWandable, IUpgradable, IRedstoneSensitive, IWrenchable {

    @Override
    public void updateEntity() {
        if (worldObj.getBlock(x, y, z) instanceof BlockNodeGenerator) {
            final TileNodeGenerator tile = getTE();
            if (tile != null) {
                fill(tile);
            }
        } else {
            worldObj.setBlockToAir(xCoord, yCoord, zCoord);
        }
    }

    public void fill(final TileNodeGenerator tile) {
        for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (dir != ForgeDirection.getOrientation(tile.facing)) {
                final TileEntity te = Thaumcraft.getConnectableTile(worldObj, xCoord, yCoord, zCoord, dir);
                if (te != null && !(te instanceof TileNodeGenerator) && !(te instanceof TileFakeAirNG)) {
                    final IEssentiaTransport ic = (IEssentiaTransport) te;
                    final Aspect ta = ic.getEssentiaType(dir.getOpposite());
                    if (ic.getEssentiaAmount(dir.getOpposite()) > 0 && ic.getSuctionAmount(dir.getOpposite()) < getSuctionAmount(null) && getSuctionAmount(null) >= ic.getMinimumSuction()) {
                        addToContainer(ta, ic.takeEssentia(ta, 1, dir.getOpposite()));
                    }
                }
            }
        }
    }

    @Override
    public Coords getMain() {
        return new Coords(x, y, z, worldObj);
    }

    public void addMain(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean canConnectEnergy(final ForgeDirection from) {
        return getTE() != null;
    }

    @Override
    public int receiveEnergy(final ForgeDirection from, final int maxReceive, final boolean simulate) {
        final TileNodeGenerator te = getTE();
        return te == null ? 0 : te.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(final ForgeDirection from, final int maxExtract, final boolean simulate) {
        final TileNodeGenerator te = getTE();
        return te == null ? 0 : te.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(final ForgeDirection from) {
        final TileNodeGenerator te = getTE();
        return te == null ? 0 : te.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(final ForgeDirection from) {
        final TileNodeGenerator te = getTE();
        return te == null ? 0 : te.getMaxEnergyStored();
    }

    @Override
    public boolean toggleBoost() {
        final TileNodeGenerator te = getTE();
        return te != null && te.toggleBoost();
    }

    @Override
    public boolean isBoosted() {
        final TileNodeGenerator te = getTE();
        return te != null && te.isBoosted();
    }

    @Override
    public void setBoost(final boolean newBoost) {
        final TileNodeGenerator te = getTE();
        if (te != null) {
            te.setBoost(newBoost);
        }
    }

    @Override
    public int onWandRightClick(final World world, final ItemStack wandstack, final EntityPlayer player, final int x, final int y, final int z, final int side, final int md) {
        final TileNodeGenerator te = getTE();
        return te == null ? -1 : te.onWandRightClick(world, wandstack, player, this.x, this.y, this.z, side, md);
    }

    @Override
    public ItemStack onWandRightClick(final World world, final ItemStack wandstack, final EntityPlayer player) {
        final TileNodeGenerator te = getTE();
        return te == null ? wandstack : te.onWandRightClick(world, wandstack, player);
    }

    @Override
    public void onUsingWandTick(final ItemStack wandstack, final EntityPlayer player, final int count) {
        final TileNodeGenerator te = getTE();
        if (te != null) {
            te.onUsingWandTick(wandstack, player, count);
        }
    }

    @Override
    public void onWandStoppedUsing(final ItemStack wandstack, final World world, final EntityPlayer player, final int count) {
        final TileNodeGenerator te = getTE();
        if (te != null) {
            te.onWandStoppedUsing(wandstack, world, player, count);
        }
    }

    @Override
    public AspectList getAspects() {
        final TileNodeGenerator te = getTE();
        return te == null ? new AspectList() : te.getAspects();
    }

    @Override
    public void setAspects(final AspectList aspects) {
        final TileNodeGenerator te = getTE();
        if (te != null) {
            te.setAspects(aspects);
        }
    }

    @Override
    public boolean doesContainerAccept(final Aspect tag) {
        final TileNodeGenerator te = getTE();
        return te != null && te.doesContainerAccept(tag);
    }

    @Override
    public int addToContainer(final Aspect tag, final int amount) {
        final TileNodeGenerator te = getTE();
        return te == null ? amount : te.addToContainer(tag, amount);
    }

    @Override
    public boolean takeFromContainer(final Aspect tag, final int amount) {
        final TileNodeGenerator te = getTE();
        return te != null && te.takeFromContainer(tag, amount);
    }

    @Override
    public boolean takeFromContainer(final AspectList ot) {
        final TileNodeGenerator te = getTE();
        return te != null && te.takeFromContainer(ot);
    }

    @Override
    public boolean doesContainerContainAmount(final Aspect tag, final int amount) {
        final TileNodeGenerator te = getTE();
        return te != null && te.doesContainerContainAmount(tag, amount);
    }

    @Override
    public boolean doesContainerContain(final AspectList ot) {
        final TileNodeGenerator te = getTE();
        return te != null && te.doesContainerContain(ot);
    }

    @Override
    public int containerContains(final Aspect tag) {
        final TileNodeGenerator te = getTE();
        return te == null ? 0 : te.containerContains(tag);
    }

    @Override
    public boolean isConnectable(final ForgeDirection face) {
        final TileNodeGenerator te = getTE();
        return te != null && te.isConnectable(face);
    }

    @Override
    public boolean canInputFrom(final ForgeDirection face) {
        final TileNodeGenerator te = getTE();
        return te != null && te.canInputFrom(face);
    }

    @Override
    public boolean canOutputTo(final ForgeDirection face) {
        final TileNodeGenerator te = getTE();
        return te != null && te.canOutputTo(face);
    }

    @Override
    public void setSuction(final Aspect aspect, final int amount) {
        final TileNodeGenerator te = getTE();
        if (te != null) {
            te.setSuction(aspect, amount);
        }
    }

    @Override
    public Aspect getSuctionType(final ForgeDirection face) {
        final TileNodeGenerator te = getTE();
        return te == null ? null : te.getSuctionType(face);
    }

    @Override
    public int getSuctionAmount(final ForgeDirection face) {
        final TileNodeGenerator te = getTE();
        return te == null ? 0 : te.getSuctionAmount(face);
    }

    @Override
    public int takeEssentia(final Aspect aspect, final int amount, final ForgeDirection face) {
        final TileNodeGenerator te = getTE();
        return te == null ? 0 : te.takeEssentia(aspect, amount, face);
    }

    @Override
    public int addEssentia(final Aspect aspect, final int amount, final ForgeDirection face) {
        final TileNodeGenerator te = getTE();
        return te == null ? 0 : te.addEssentia(aspect, amount, face);
    }

    @Override
    public Aspect getEssentiaType(final ForgeDirection face) {
        final TileNodeGenerator te = getTE();
        return te == null ? null : te.getEssentiaType(face);
    }

    @Override
    public int getEssentiaAmount(final ForgeDirection face) {
        final TileNodeGenerator te = getTE();
        return te == null ? 0 : te.getEssentiaAmount(face);
    }

    @Override
    public int getMinimumSuction() {
        final TileNodeGenerator te = getTE();
        return te == null ? 0 : te.getMinimumSuction();
    }

    @Override
    public boolean renderExtendedTube() {
        final TileNodeGenerator te = getTE();
        return te != null && te.renderExtendedTube();
    }

    @Override
    public RedstoneSet getCurrentSetting() {
        final TileNodeGenerator te = getTE();
        return te == null ? RedstoneSet.LOW : te.getCurrentSetting();
    }

    @Override
    public void setNewSetting(final RedstoneSet newSet) {
        final TileNodeGenerator te = getTE();
        if (te != null) {
            te.setNewSetting(newSet);
        }
    }

    @Override
    public boolean isModified() {
        final TileNodeGenerator te = getTE();
        return te != null && te.isModified();
    }

    @Override
    public boolean canBeModified() {
        final TileNodeGenerator te = getTE();
        return te != null && te.canBeModified();
    }

    @Override
    public boolean onWrenched(final boolean sneaking) {
        final TileNodeGenerator te = getTE();
        return te != null && te.onWrenched(sneaking);
    }

    @Override
    public String getInfo() {
        final TileNodeGenerator te = getTE();
        return te == null ? "" : te.getInfo();
    }

    private TileNodeGenerator getTE() {
        final TileEntity tile = worldObj.getTileEntity(x, y, z);
        return tile instanceof TileNodeGenerator ? (TileNodeGenerator) tile : null;
    }
}
