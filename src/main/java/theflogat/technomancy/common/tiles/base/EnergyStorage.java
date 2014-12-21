package theflogat.technomancy.common.tiles.base;

import net.minecraft.nbt.NBTTagCompound;
import cofh.api.energy.IEnergyStorage;

public class EnergyStorage implements IEnergyStorage{
	
	public int energy = 0;
	public int capacity;
	public int maxExtract;
	public int maxReceive;
	
	public EnergyStorage(int capacity, int maxExtract, int maxReceive) {
		this.capacity = capacity;
		this.maxExtract = maxExtract;
		this.maxReceive = maxReceive;
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {

		int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {

		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored() {
		return energy;
	}

	@Override
	public int getMaxEnergyStored() {
		return capacity;
	}
	
	public void load(NBTTagCompound comp) {
		energy = comp.getInteger("energy");
	}
	
	public void save(NBTTagCompound comp) {
		comp.setInteger("energy", energy);
	}
	
	public boolean isFull() {
		return energy>=capacity;
	}
}
