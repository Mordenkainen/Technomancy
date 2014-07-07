package democretes.blocks.machines.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cofh.api.energy.EnergyStorage;
import cofh.util.FluidHelper;
import democretes.compat.BloodMagic;

public class TileBloodFabricator extends TileMachineBase implements IFluidHandler {
	
	public FluidTank tank = new FluidTank(10000);
	int count = 0;
	
	public TileBloodFabricator() {
		this.capacity = 50000000;
		this.maxReceive = 50000;
		this.energyStorage = new EnergyStorage(capacity);
	}
	@Override
	public void updateEntity() {
		if(this.energyStorage.getEnergyStored() >= 1000000 && this.tank.getFluidAmount() + 100 <= this.tank.getCapacity()) {
			this.energyStorage.extractEnergy(1000000, false);
			this.tank.fill(new FluidStack(BloodMagic.lifeEssenceFluid, 200), true);
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}	
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if ((resource == null) || ((from != ForgeDirection.UNKNOWN))) {
			return 0;
		}
		if(resource.getFluid() == BloodMagic.lifeEssenceFluid) {
			return this.tank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return this.tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(this.tank.getFluid() != null) {
			return this.tank.drain(maxDrain, doDrain);
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
		int f = FluidHelper.insertFluidIntoAdjacentFluidHandler(this, from.ordinal(), stack, false);
		if(f == 200) {
			return true;
		}
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { this.tank.getInfo() };
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		this.tank = new FluidTank(10000);
		this.tank.readFromNBT(compound);
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		this.tank.writeToNBT(compound);
	}
	
	@Override
	public boolean canInterface(ForgeDirection from) {
		return from==ForgeDirection.DOWN;
	}

}
