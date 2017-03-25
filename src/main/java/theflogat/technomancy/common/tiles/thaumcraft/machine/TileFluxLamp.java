package theflogat.technomancy.common.tiles.thaumcraft.machine;

import cpw.mods.fml.common.Optional;
import me.jezza.thaumicpipes.api.interfaces.IThaumicInput;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.tiles.TileInfusionMatrix;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.util.helpers.WorldHelper;

@Optional.Interface(iface = "me.jezza.thaumicpipes.api.interfaces.IThaumicInput", modid = "ThaumicPipes")
public class TileFluxLamp extends TileTechnomancy implements IAspectContainer, IEssentiaTransport, IFluidHandler, IThaumicInput {

    private int amount;
    private final static int MAXAMOUNT = 32;
    public FluidTank tank = new FluidTank(1000);
    private boolean stabilize = true;
    public boolean placed;
    private int count;

    @Override
    public void writeCustomNBT(final NBTTagCompound compound) {
        compound.setBoolean("Stabilize", stabilize);
    }

    @Override
    public void readCustomNBT(final NBTTagCompound compound) {
        stabilize = compound.getBoolean("Stabilize");
    }

    @Override
    public void writeSyncData(final NBTTagCompound compound) {
        compound.setInteger("AspectAmount", amount);
        compound.setBoolean("Placed", placed);
        tank.writeToNBT(compound);
    }

    @Override
    public void readSyncData(final NBTTagCompound compound) {
        amount = compound.getInteger("AspectAmount");
        placed = compound.getBoolean("Placed");
        tank = new FluidTank(1000);
        tank.readFromNBT(compound);
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            TileEntity tile = null;
            if (!worldObj.isRemote && ++count % 10 == 0) {
                if (amount < MAXAMOUNT) {
                    fill();
                }
                tile = getMatrix();
            }
            if (tile == null) {
                return;
            }
            if (!((TileInfusionMatrix) tile).crafting) {
                this.stabilize = true;
            }
            if (((TileInfusionMatrix) tile).instability > 0 && stabilize) {
                for (int i = 0; i < 5; i++) {
                    if (amount >= 5 && (tank.getCapacity() - tank.getFluidAmount()) >= 200 && stabilize) {
                        takeFromContainer(Aspect.ORDER, 5);
                        ((TileInfusionMatrix) tile).instability -= 1;
                        tank.fill(FluidRegistry.getFluidStack(Thaumcraft.FLUXGOO.getName(), 200), true);
                    } else {
                        break;
                    }
                }
                this.stabilize = false;
            }
        }
    }

    private TileEntity getMatrix() {
        for (int yy = -5; yy < 5; yy++) {
            for (int xx = -10; xx < 10; xx++) {
                for (int zz = -10; zz < 10; zz++) {
                    final TileEntity te = this.worldObj.getTileEntity(this.xCoord + xx, this.yCoord + yy, this.zCoord + zz);
                    if (te instanceof TileInfusionMatrix) {
                        return te;
                    }
                }
            }
        }
        return null;
    }

    public void fill() {
        final TileEntity te = Thaumcraft.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.UP);
        if (te != null) {
            final IEssentiaTransport ic = (IEssentiaTransport) te;
            if (!ic.canOutputTo(ForgeDirection.DOWN)) {
                return;
            }
            if (ic.getEssentiaAmount(ForgeDirection.DOWN) > 0 && ic.getEssentiaType(ForgeDirection.DOWN) == Aspect.ORDER && ic.getSuctionAmount(ForgeDirection.DOWN) < getSuctionAmount(ForgeDirection.UP) && getSuctionAmount(ForgeDirection.UP) >= ic.getMinimumSuction()) {
                addToContainer(Aspect.ORDER, ic.takeEssentia(Aspect.ORDER, 1, ForgeDirection.DOWN));
            }
        }
    }

    @Override
    public AspectList getAspects() {
        final AspectList al = new AspectList();
        if (this.amount > 0) {
            al.add(Aspect.ORDER, this.amount);
        }
        return al;
    }

    @Override
    public int addToContainer(final Aspect tag, int amount) {
        if (amount == 0) {
            return amount;
        }
        if (this.amount < MAXAMOUNT && tag == Aspect.ORDER) {
            final int added = Math.min(amount, MAXAMOUNT - this.amount);
            this.amount += added;
            amount -= added;
        }
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        return amount;
    }

    @Override
    public boolean takeFromContainer(final Aspect tag, final int amount) {
        if (this.amount >= amount && tag == Aspect.ORDER) {
            this.amount -= amount;
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            return true;
        }
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(final Aspect tag, final int amt) {
        return amount >= amt && tag == Aspect.ORDER;
    }

    @Override
    public boolean doesContainerContain(final AspectList ot) {
        if (ot.size() > 1) {
            return false;
        }
        return ot.getAspects()[1] == Aspect.ORDER && this.amount >= ot.getAmount(Aspect.ORDER);
    }

    @Override
    public int getMinimumSuction() {
        return 128;
    }

    @Override
    public int takeEssentia(final Aspect aspect, final int amount, final ForgeDirection dir) {
        return takeFromContainer(aspect, amount) ? amount : 0;
    }

    @Override
    public void setAspects(final AspectList aspects) {}

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public boolean takeFromContainer(final AspectList ot) {
        return false;
    }

    @Override
    public boolean renderExtendedTube() {
        return true;
    }

    @Override
    public int containerContains(final Aspect tag) {
        return tag == Aspect.ORDER ? amount : 0;
    }

    @Override
    public boolean doesContainerAccept(final Aspect tag) {
        return tag == Aspect.ORDER && amount < MAXAMOUNT;
    }

    @Override
    public boolean isConnectable(final ForgeDirection face) {
        return face == ForgeDirection.UP;
    }

    @Override
    public boolean canInputFrom(final ForgeDirection face) {
        return face == ForgeDirection.UP;
    }

    @Override
    public boolean canOutputTo(final ForgeDirection face) {
        return false;
    }

    @Override
    public Aspect getSuctionType(final ForgeDirection face) {
        if (this.amount < MAXAMOUNT) {
            return Aspect.ORDER;
        }
        return null;
    }

    @Override
    public int getSuctionAmount(final ForgeDirection loc) {
        if (this.amount < MAXAMOUNT) {
            return 128;
        }
        return 0;
    }

    @Override
    public int addEssentia(final Aspect aspect, final int amount, final ForgeDirection dir) {
        return amount - addToContainer(aspect, amount);
    }

    @Override
    public Aspect getEssentiaType(final ForgeDirection face) {
        return amount > 0 ? Aspect.ORDER : null;
    }

    @Override
    public int getEssentiaAmount(final ForgeDirection face) {
        return this.amount;
    }

    @Override
    public void setSuction(final Aspect aspect, final int amount) {}

    @Override
    public int fill(final ForgeDirection from, final FluidStack resource, final boolean doFill) {
        return this.tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(final ForgeDirection from, final FluidStack resource, final boolean doDrain) {
        return this.tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(final ForgeDirection from, final int maxDrain, final boolean doDrain) {
        return this.tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(final ForgeDirection from, final Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(final ForgeDirection from, final Fluid fluid) {
        final FluidStack stack = FluidRegistry.getFluidStack(fluid.getName(), 200);
        final int f = WorldHelper.insertFluidIntoAdjacentFluidHandler(this, from.ordinal(), stack, false);
        return f == 200;
    }

    @Override
    public FluidTankInfo[] getTankInfo(final ForgeDirection from) {
        return new FluidTankInfo[] { this.tank.getInfo() };
    }

}
