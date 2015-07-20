package theflogat.technomancy.common.tiles.bloodmagic.machines;

import theflogat.technomancy.common.tiles.base.TileMachineBase;
import theflogat.technomancy.lib.compat.BloodMagic;
import theflogat.technomancy.lib.handlers.Rate;
import theflogat.technomancy.util.helpers.WorldHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileBloodFabricator extends TileMachineBase implements IFluidHandler {
	
	public FluidTank tank = new FluidTank(10000);
	public static int cost = Rate.bloodFabCost;
	int count = 0;
	
	public TileBloodFabricator() {
		super(Rate.bloodFabCost * 50);
	}
	@Override
	public void updateEntity() {
		if(getEnergyStored()>=cost && tank.getFluidAmount()+200<=tank.getCapacity()) {
			extractEnergy(cost, false);
			tank.fill(new FluidStack(BloodMagic.lifeEssenceFluid, 200), true);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}	
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if ((resource == null) || ((from != ForgeDirection.UNKNOWN))) {
			return 0;
		}
		if(resource.getFluid() == BloodMagic.lifeEssenceFluid) {
			return tank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		if(tank.getFluid() != null) {
			return tank.drain(maxDrain, doDrain);
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		FluidStack stack = FluidRegistry.getFluidStack(fluid.getName(), 200);
		int f = WorldHelper.insertFluidIntoAdjacentFluidHandler(this, from.ordinal(), stack, false);
		if(f == 200) {
			return true;
		}
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] {this.tank.getInfo()};
	}
	
	@Override
	public void writeSyncData(NBTTagCompound compound) {
		super.writeSyncData(compound);
		tank.writeToNBT(compound);
	}
	
	@Override
	public void readSyncData(NBTTagCompound compound) {
		super.readSyncData(compound);
		tank = new FluidTank(10000);
		tank.readFromNBT(compound);
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from==ForgeDirection.DOWN;
	}

}