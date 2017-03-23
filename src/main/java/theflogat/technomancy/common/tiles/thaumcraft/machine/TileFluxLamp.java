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

    int amount = 0;
    int maxAmount = 32;
    public FluidTank tank = new FluidTank(1000);
    Aspect aspectSuction;
    boolean stabilize = true;
    public boolean placed = false;

    @Override
    public void writeCustomNBT(NBTTagCompound compound) {
        compound.setBoolean("Stabilize", stabilize);
    }

    @Override
    public void readCustomNBT(NBTTagCompound compound) {
        stabilize = compound.getBoolean("Stabilize");
    }

    @Override
    public void writeSyncData(NBTTagCompound compound) {
        compound.setInteger("AspectAmount", amount);
        compound.setBoolean("Placed", placed);
        tank.writeToNBT(compound);
    }

    @Override
    public void readSyncData(NBTTagCompound compound) {
        amount = compound.getInteger("AspectAmount");
        placed = compound.getBoolean("Placed");
        tank = new FluidTank(1000);
        tank.readFromNBT(compound);
    }

    int count;

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            TileEntity tile = null;
            if (!worldObj.isRemote && ++count % 10 == 0) {
                if (amount < maxAmount) {
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

    TileEntity getMatrix() {
        for (int yy = -5; yy < 5; yy++) {
            for (int xx = -10; xx < 10; xx++) {
                for (int zz = -10; zz < 10; zz++) {
                    TileEntity te = this.worldObj.getTileEntity(this.xCoord + xx, this.yCoord + yy, this.zCoord + zz);
                    if (te instanceof TileInfusionMatrix) {
                        return te;
                    }
                }
            }
        }
        return null;
    }

    void fill() {
        TileEntity te = Thaumcraft.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.UP);
        if (te != null) {
            IEssentiaTransport ic = (IEssentiaTransport) te;
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
        AspectList al = new AspectList();
        if (this.amount > 0) {
            al.add(Aspect.ORDER, this.amount);
        }
        return al;
    }

    @Override
    public int addToContainer(Aspect tag, int amount) {
        if (amount == 0) {
            return amount;
        }
        if (this.amount < this.maxAmount && tag == Aspect.ORDER) {
            int added = Math.min(amount, this.maxAmount - this.amount);
            this.amount += added;
            amount -= added;
        }
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        return amount;
    }

    @Override
    public boolean takeFromContainer(Aspect tag, int amount) {
        if (this.amount >= amount && tag == Aspect.ORDER) {
            this.amount -= amount;
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            return true;
        }
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect tag, int amt) {
        if (amount >= amt && tag == Aspect.ORDER) {
            return true;
        }
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList ot) {
        if (ot.size() > 1) {
            return false;
        }
        if (ot.getAspects()[1] == Aspect.ORDER && this.amount >= ot.getAmount(Aspect.ORDER)) {
            return true;
        }
        return false;
    }

    @Override
    public int getMinimumSuction() {
        return 128;
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, ForgeDirection dir) {
        return takeFromContainer(aspect, amount) ? amount : 0;
    }

    @Override
    public void setAspects(AspectList aspects) {}

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public boolean takeFromContainer(AspectList ot) {
        return false;
    }

    @Override
    public boolean renderExtendedTube() {
        return true;
    }

    @Override
    public int containerContains(Aspect tag) {
        return tag == Aspect.ORDER ? amount : 0;
    }

    @Override
    public boolean doesContainerAccept(Aspect tag) {
        return tag == Aspect.ORDER && amount < maxAmount;
    }

    @Override
    public boolean isConnectable(ForgeDirection face) {
        return face == ForgeDirection.UP;
    }

    @Override
    public boolean canInputFrom(ForgeDirection face) {
        return face == ForgeDirection.UP;
    }

    @Override
    public boolean canOutputTo(ForgeDirection face) {
        return false;
    }

    @Override
    public Aspect getSuctionType(ForgeDirection face) {
        if (this.amount < this.maxAmount) {
            return Aspect.ORDER;
        }
        return null;
    }

    @Override
    public int getSuctionAmount(ForgeDirection loc) {
        if (this.amount < this.maxAmount) {
            return 128;
        }
        return 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, ForgeDirection dir) {
        return amount - addToContainer(aspect, amount);
    }

    @Override
    public Aspect getEssentiaType(ForgeDirection face) {
        return amount > 0 ? Aspect.ORDER : null;
    }

    @Override
    public int getEssentiaAmount(ForgeDirection face) {
        return this.amount;
    }

    @Override
    public void setSuction(Aspect aspect, int amount) {}

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return this.tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return this.tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return this.tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        FluidStack stack = FluidRegistry.getFluidStack(fluid.getName(), 200);
        int f = WorldHelper.insertFluidIntoAdjacentFluidHandler(this, from.ordinal(), stack, false);
        if (f == 200) {
            return true;
        }
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { this.tank.getInfo() };
    }

}
