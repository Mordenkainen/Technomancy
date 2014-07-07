package democretes.blocks.dynamos.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import democretes.compat.BloodMagic;

public class TileBloodDynamo extends TileDynamoBase implements IFluidHandler {

	public FluidTank tank = new FluidTank(5000);

	@Override
	protected boolean canGenerate() {
		if(tank.getFluid() != null) {
			if(tank.getFluid().amount >= 100) {
				return true;
			}		
		}
		return false;	
	}	
	
	@Override
	protected void generate() {
		if (fuelRF <= 0) {
			fuelRF += 10000;
			tank.drain(100, true);
		}
		int energy = calcEnergy();
		energyStorage.modifyEnergyStored(energy);
		fuelRF -= energy;
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
		if ( ((from != ForgeDirection.UNKNOWN) && (from.ordinal() == facing))) {
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
	public void readCustomNBT(NBTTagCompound compound) {
		fuelRF = compound.getInteger("RF");
		facing = compound.getByte("Facing");
		tank = new FluidTank(5000);
		tank.readFromNBT(compound);
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		compound.setInteger("RF", fuelRF);	
		compound.setByte("Facing", facing);
		tank.writeToNBT(compound);
	}
	

}