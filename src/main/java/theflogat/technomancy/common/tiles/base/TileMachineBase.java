package theflogat.technomancy.common.tiles.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;

public abstract class TileMachineBase extends TileTechnomancy implements IEnergyHandler, IEnergyStorage {
	
	public int energy;
	public int capacity;
	
	public TileMachineBase(int capacity) {
		this.capacity = capacity;
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound comp) {}
	
	@Override
	public void writeCustomNBT(NBTTagCompound comp) {};
	
	@Override
	public void writeSyncData(NBTTagCompound compound) {
		compound.setInteger("energy", energy);
	}
	
	@Override
	public void readSyncData(NBTTagCompound compound) {
		energy = compound.getInteger("energy");
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return extractEnergy(maxExtract, simulate);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return getMaxEnergyStored();
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if(!simulate)worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		int energy = this.energy;
		if(!simulate)this.energy += Math.min(maxReceive, capacity-energy);
		return Math.min(maxReceive, capacity-energy);
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if(!simulate)worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
