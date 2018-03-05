package theflogat.technomancy.common.tiles.bloodmagic.machines;

import WayofTime.bloodmagic.block.BlockLifeEssence;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import theflogat.technomancy.common.tiles.base.TileMachineBase;
import theflogat.technomancy.lib.compat.BloodMagic;
import theflogat.technomancy.lib.handlers.Rate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class TileBloodFabricator extends TileMachineBase implements IFluidHandler, IFluidTank {
	
	public FluidTank tank = new FluidTank(10000);
	public static int cost = Rate.bloodFabCost;
	int count = 0;
	
	public TileBloodFabricator() {
		super(1000000);
	}
	@Override
	public void update() {
		if(getEnergyStored()>=cost && tank.getCapacity() + tank.getFluidAmount() >= 200) {
			extractEnergy(cost, false);
			tank.fill(new FluidStack(BlockLifeEssence.getLifeEssence(), 200), true);
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
		if(tank.getFluidAmount() >= 200) {
			for(EnumFacing dir : EnumFacing.VALUES) {
				TileEntity te = world.getTileEntity(new BlockPos(pos.getX() + dir.getFrontOffsetX(), pos.getY() + dir.getFrontOffsetY(), pos.getZ()+ dir.getFrontOffsetZ()));
				if(te instanceof IFluidHandler) {
					IFluidHandler target = (IFluidHandler) te;
					FluidStack push = tank.getFluid().copy();
					push.amount = Math.min(push.amount, 200);
					int filled = target.fill(push, true);
					if (filled > 0) {
						tank.drain(filled, true);
						return;
					}
				}
			}
		}
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[0];
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if ((resource == null)) {
			return 0;
		}
		if(resource.getFluid() == BloodMagic.lifeEssenceFluid) {
			return tank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		if(tank.getFluid() != null) {
			return tank.drain(maxDrain, doDrain);
		}
		return null;
	}

	@Nullable
	@Override
	public FluidStack getFluid() {
		return tank.getFluid();
	}

	@Override
	public int getFluidAmount() {
		return tank.getFluidAmount();
	}

	@Override
	public int getCapacity() {
		return tank.getCapacity();
	}

	@Override
	public FluidTankInfo getInfo() {
		return this.tank.getInfo();
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
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T) this.tank;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return from==EnumFacing.DOWN;
	}

}