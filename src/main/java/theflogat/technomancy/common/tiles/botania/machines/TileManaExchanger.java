package theflogat.technomancy.common.tiles.botania.machines;

import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.tiles.base.IWrenchable;
import theflogat.technomancy.common.tiles.base.TileTechnomancyRedstone;
import theflogat.technomancy.lib.handlers.Rate;
import vazkii.botania.common.block.tile.mana.TilePool;

import javax.annotation.Nullable;

public class TileManaExchanger extends TileTechnomancyRedstone implements IFluidHandler, IFluidTank, IEnergyReceiver, IWrenchable {

	public TileManaExchanger() {
		super(RedstoneSet.LOW);
	}

	public FluidTank tank = new FluidTank(1000);
	public boolean mode;
	public boolean active;
	public EnergyStorage storage = new EnergyStorage(Rate.exchangerCost * 10);

	@Override
	public void readCustomNBT(NBTTagCompound comp) {}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {}
	
	@Override
	public void writeSyncData(NBTTagCompound comp) {
		super.writeSyncData(comp);
		tank.writeToNBT(comp);
		comp.setBoolean("Mode", mode);
		storage.writeToNBT(comp);
	}
	
	@Override
	public void readSyncData(NBTTagCompound comp) {
		super.readSyncData(comp);
		tank = new FluidTank(1000);
		tank.readFromNBT(comp);
		mode = comp.getBoolean("Mode");
		storage.readFromNBT(comp);
	}
	
	@Override
	public void update() {
		if (!set.canRun(this)) {
			active = false;
			return;
		} else {
			active = true;
		}
		TileEntity tile = world.getTileEntity(pos.up(1));
		if(tile instanceof TilePool){
			if (world.isRemote) {
				return;
			}
			TilePool pool = (TilePool)tile;
			if(storage.getEnergyStored() >= Rate.exchangerCost) {
				if(mode) {
					if(tank.getFluidAmount() > 0 && pool.getCurrentMana() <= pool.manaCap - 1000) {
						pool.recieveMana(1000);
						tank.drain(1, true);
						storage.extractEnergy(Rate.exchangerCost, false);
						world.notifyBlockUpdate(pos.up(1), world.getBlockState(pos.up(1)), world.getBlockState(pos.up(1)), 3);
					}
				} else {
					if(tank.getFluidAmount() < tank.getCapacity() && pool.getCurrentMana() >= 1000) {
						pool.recieveMana(-1000);
						tank.fill(new FluidStack(TMBlocks.manaFluid, 1), true);
						storage.extractEnergy(Rate.exchangerCost, false);
						world.notifyBlockUpdate(pos.up(1), world.getBlockState(pos.up(1)), world.getBlockState(pos.up(1)), 3);
					}
				}
			}
		} else {
			active = false;
		}
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
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[0];
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource.getFluid() != TMBlocks.manaFluid || !mode) {
			return 0;
		}
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(resource.getFluid() != TMBlocks.manaFluid) {
			return null;
		}
		return drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if(mode) {
			return null;
		}
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return from != EnumFacing.UP;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return storage.getMaxEnergyStored();
	}

	@Override
	public boolean onWrenched(boolean sneaking) {
		mode = !mode;
		return true;
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
			return (T) tank;
		}
		return super.getCapability(capability, facing);
	}
}
