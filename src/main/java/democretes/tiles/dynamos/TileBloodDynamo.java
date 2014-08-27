package democretes.tiles.dynamos;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import democretes.compat.BloodMagic;
import democretes.tiles.base.EnergyStorage;
import democretes.tiles.base.TileDynamoBase;

public class TileBloodDynamo extends TileDynamoBase implements IFluidHandler {

	public FluidTank tank = new FluidTank(8000);
	
	public TileBloodDynamo() {
		super(new EnergyStorage(maxEnergy_default, maxExtract_default, maxReceive_default));
	}
	
	@Override
	public int extractFuel(int ener) {
		float ratio = ((float) ener) / 80F;
		int val = (int) (100 * ratio);
		float fuel = (float) val / (float) tank.drain(val, true).amount;
		return (int) (((float)10*20)*fuel);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if ((resource == null) || ((from != ForgeDirection.UNKNOWN) && (from.ordinal() == facing))) {
			return 0;
		}
		if(resource.getFluid() == BloodMagic.lifeEssenceFluid) {
			return tank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return this.tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (from != ForgeDirection.UNKNOWN && from.ordinal() == facing) {
			return null;
		}
		if(tank.getFluid() != null) {
			return tank.drain(maxDrain, doDrain);
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return from.ordinal() != facing;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { tank.getInfo() };
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		super.readCustomNBT(comp);
		tank = new FluidTank(8000);
		tank.readFromNBT(comp);
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		super.writeCustomNBT(comp);
		tank.writeToNBT(comp);
	}
}