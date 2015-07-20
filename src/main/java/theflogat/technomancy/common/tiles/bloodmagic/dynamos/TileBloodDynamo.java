package theflogat.technomancy.common.tiles.bloodmagic.dynamos;

import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.lib.compat.BloodMagic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileBloodDynamo extends TileDynamoBase implements IFluidHandler {

	public int liquid = 0;
	public static final int capacity = 8000;
	
	@Override
	public int extractFuel(int ener) {
		float ratio = (ener) / 80F;
		int val = (int)Math.ceil(100 * ratio);
		if(val > liquid) {
			return 0;
		}
		liquid -= val;
		return 400;
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
	public void writeSyncData(NBTTagCompound comp) {
		super.writeSyncData(comp);
		comp.setInteger("liquid", liquid);
	}
	
	@Override
	public void readSyncData(NBTTagCompound comp) {
		super.readSyncData(comp);
		liquid = comp.getInteger("liquid");
	}
}
