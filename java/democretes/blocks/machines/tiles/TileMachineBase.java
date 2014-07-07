package democretes.blocks.machines.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;
import democretes.blocks.TileTechnomancy;
import democretes.handlers.ConfigHandler;

public class TileMachineBase extends TileTechnomancy implements IEnergyHandler, IEnergyStorage {
	
	EnergyStorage energyStorage;
	
	public int energy;
	public int capacity;
	public int maxReceive;
	public int maxExtract = this.maxReceive;

	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		energyStorage.readFromNBT(compound);	
		if (energy < 0) {
			energy = 0;
		}
		compound.setInteger("Energy", energy);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		energyStorage.writeToNBT(compound);		
		this.energy = compound.getInteger("Energy");

		if (energy > capacity) {
			energy = capacity;
		}
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,boolean simulate) {
		if(!simulate)worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return energyStorage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if(!simulate)worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return energyStorage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energyStorage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return energyStorage.getMaxEnergyStored();
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

}
