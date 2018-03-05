package theflogat.technomancy.common.tiles.bloodmagic.dynamos;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.lib.compat.BloodMagic;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

public class TileBloodDynamo extends TileDynamoBase implements IFluidHandler, IFluidTank {

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

	@Nullable
	@Override
	public FluidStack getFluid() {
		return null;
	}

	@Override
	public int getFluidAmount() {
		return liquid;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (resource == null) {
			return 0;
		}
		if(resource.getFluid() == BloodMagic.lifeEssenceFluid) {
			int liquid = this.liquid;
			this.liquid += doFill ? Math.min(resource.amount, capacity - liquid) : 0;
			return Math.min(resource.amount, capacity - liquid);
		}
		return 0;
	}

	@Nullable
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return null;
	}

	@Nullable
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[0];
	}

	@Override
	public FluidTankInfo getInfo() {
		if(FluidRegistry.isFluidRegistered(BloodMagic.lifeEssenceFluid)) {
			return new FluidTankInfo(new FluidStack(BloodMagic.lifeEssenceFluid, liquid), capacity);
		}else {
			return null;
		}
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
