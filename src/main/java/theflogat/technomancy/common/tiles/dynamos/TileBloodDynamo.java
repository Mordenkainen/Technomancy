package theflogat.technomancy.common.tiles.dynamos;

import theflogat.technomancy.common.tiles.base.EnergyStorage;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.handlers.compat.BloodMagic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileBloodDynamo extends TileDynamoBase implements IFluidHandler {

	public int liquid = 0;
	public static final int capacity = 8000;
	
	@Override
	public int extractFuel(int ener) {
		if(liquid!=0){
			float ratio = ((float) ener) / 80F;
			float val = 100F * ratio;
			float fuelPerc = val / (float) Math.min(liquid, val);
			liquid -= Math.min(liquid, val);
			float fuel = 12F * fuelPerc;
			return (int) fuel;
		}
		return 0;
	}
	
	public boolean emptyBucket() {
		if(liquid+1000<=capacity) {
			liquid += 1000;
			return true;
		}
		return false;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (resource == null || from==ForgeDirection.UNKNOWN || from.ordinal()==facing) {
			return 0;
		}
		if(resource.getFluid() == BloodMagic.lifeEssenceFluid) {
			int liquid = this.liquid;
			this.liquid += doFill ? Math.min(resource.amount, capacity - liquid) : 0;
			return Math.min(resource.amount, capacity - liquid);
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return from.ordinal()!=facing;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { new FluidTankInfo(new FluidStack(BloodMagic.lifeEssenceFluid, liquid), capacity) };
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		super.readCustomNBT(comp);
		liquid = comp.getInteger("liquid");
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		super.writeCustomNBT(comp);
		comp.setInteger("liquid", liquid);
	}
}