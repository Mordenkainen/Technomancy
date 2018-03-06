package theflogat.technomancy.common.tiles.thaumcraft.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;
import theflogat.technomancy.lib.compat.Thaumcraft;

public class TileEssentiaReservoir extends TileTechnomancy implements IEssentiaTransport, IAspectContainer {

    private static final int MAX_AMOUNT = 256;

    public Aspect suction;
    private Aspect as;
    private int amount;

    @Override
    public void updateEntity() {
        if (as != null) {
            suction = as;
        }
        fill();
    }

    public void fill() {
        if (amount < MAX_AMOUNT) {
            for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                final TileEntity te = Thaumcraft.getConnectableTile(worldObj, xCoord, yCoord, zCoord, dir);
                if (te != null) {
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
    public void readCustomNBT(final NBTTagCompound comp) {}

    @Override
    public void writeCustomNBT(final NBTTagCompound comp) {}

    @Override
    public void writeSyncData(final NBTTagCompound comp) {
        comp.setInteger("amount", amount);
        if (as != null) {
            comp.setString("as", as.getTag());
        }
        if (suction != null) {
            comp.setString("suction", suction.getTag());
        }
    }

    @Override
    public void readSyncData(final NBTTagCompound comp) {
        amount = comp.getInteger("amount");
        as = Aspect.getAspect(comp.getString("as"));
        suction = Aspect.getAspect(comp.getString("suction"));
    }

    @Override
    public boolean isConnectable(final ForgeDirection face) {
        return true;
    }

    @Override
    public boolean canInputFrom(final ForgeDirection face) {
        return true;
    }

    @Override
    public boolean canOutputTo(final ForgeDirection face) {
        return true;
    }

    @Override
    public void setSuction(final Aspect aspect, final int amount) {

    }

    @Override
    public Aspect getSuctionType(final ForgeDirection face) {
        return suction;
    }

    @Override
    public int getSuctionAmount(final ForgeDirection face) {
        return 128;
    }

    @Override
    public int takeEssentia(final Aspect aspect, final int amount, final ForgeDirection face) {
        return takeFromContainer(aspect, amount) ? 0 : amount;
    }

    @Override
    public int addEssentia(final Aspect aspect, final int amount, final ForgeDirection face) {
        return addToContainer(aspect, amount);
    }

    @Override
    public Aspect getEssentiaType(final ForgeDirection face) {
        return as;
    }

    @Override
    public int getEssentiaAmount(final ForgeDirection face) {
        return amount;
    }

    @Override
    public int getMinimumSuction() {
        return 0;
    }

    @Override
    public boolean renderExtendedTube() {
        return true;
    }

    @Override
    public AspectList getAspects() {
        if (as != null && amount > 0) {
            final AspectList al = new AspectList();
            al.add(as, amount);
            return al;
        }
        return null;
    }

    @Override
    public void setAspects(final AspectList aspects) {

    }

    @Override
    public boolean doesContainerAccept(final Aspect tag) {
        return as == null || as.equals(tag);
    }

    @Override
    public int addToContainer(final Aspect tag, int amt) {
        if (amt != 0 && (amount < MAX_AMOUNT && tag == as || amount == 0)) {
            as = tag;
            final int added = Math.min(amt, MAX_AMOUNT - amount);
            amount += added;
            amt -= added;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
        return amt;
    }

    @Override
    public boolean takeFromContainer(final Aspect tag, int amount) {
        if (as != null && tag == as && amount <= this.amount) {
            this.amount -= amount;
            return true;
        }
        return false;
    }

    @Override
    public boolean takeFromContainer(final AspectList ot) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(final Aspect tag, final int amount) {
        return as != null && tag == as && amount <= this.amount;
    }

    @Override
    public boolean doesContainerContain(final AspectList ot) {
        return false;
    }

    @Override
    public int containerContains(final Aspect tag) {
        return as != null && tag == as ? amount : 0;
    }

}
