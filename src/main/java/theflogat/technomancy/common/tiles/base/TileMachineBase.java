package theflogat.technomancy.common.tiles.base;

import cofh.redstoneflux.api.IEnergyHandler;
import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.api.IEnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public abstract class TileMachineBase extends TileTechnomancy implements IEnergyHandler, IEnergyStorage, IEnergyReceiver, IEnergyProvider {
	
	public int energy;
	public int capacity;
	
	public TileMachineBase(int capacity) {
		this.capacity = capacity;
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound comp) {}
	
	@Override
	public void writeCustomNBT(NBTTagCompound comp) {}
	
	@Override
	public void writeSyncData(NBTTagCompound compound) {
		compound.setInteger("energy", energy);
	}
	
	@Override
	public void readSyncData(NBTTagCompound compound) {
		energy = compound.getInteger("energy");
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		return receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		return extractEnergy(maxExtract, simulate);
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		return getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return getMaxEnergyStored();
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if(!simulate)world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		int energy = this.energy;
		if(!simulate)this.energy += Math.min(maxReceive, capacity-energy);
		return Math.min(maxReceive, capacity-energy);
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if(!simulate)world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		int energy = this.energy;
		if(!simulate)this.energy -= Math.min(maxExtract, energy);
		return Math.min(maxExtract, energy);
	}

	@Override
	public int getEnergyStored() {
		return energy;
	}

	@Override
	public int getMaxEnergyStored() {
		return capacity;
	}
}
