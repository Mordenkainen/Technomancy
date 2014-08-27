package democretes.tiles.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;
import democretes.handlers.ConfigHandler;

public abstract class TileMachineBase extends TileTechnomancy implements IEnergyHandler, IEnergyStorage {
	
	protected EnergyStorage ener;
	
	public int energy;
	public int capacity;
	public int maxReceive;
	public int maxExtract = this.maxReceive;

	//public abstract void test();
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		ener.readFromNBT(compound);	
		if (energy < 0) {
			energy = 0;
		}
		compound.setInteger("Energy", energy);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		ener.writeToNBT(compound);		
		this.energy = compound.getInteger("Energy");

		if (energy > capacity) {
			energy = capacity;
		}
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,boolean simulate) {
		if(!simulate)worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return ener.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if(!simulate)worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return ener.extractEnergy(maxExtract, simulate);
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return ener.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return ener.getMaxEnergyStored();
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
