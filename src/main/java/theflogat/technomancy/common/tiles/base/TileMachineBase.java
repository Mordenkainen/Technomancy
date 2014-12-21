package theflogat.technomancy.common.tiles.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;

public abstract class TileMachineBase extends TileTechnomancy implements IEnergyHandler, IEnergyStorage {
	
	public int energy;
	public static int capacity;
	
	public TileMachineBase(int capacity) {
		this.capacity = capacity;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		compound.setInteger("energy", energy);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);		
		energy = compound.getInteger("energy");
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if(!simulate)worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		int energy = this.energy;
		if(!simulate)this.energy += Math.min(maxReceive, capacity-energy);
		return Math.min(maxReceive, capacity-energy);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if(!simulate)worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		int energy = this.energy;
		if(!simulate)this.energy -= Math.min(maxExtract, energy);
		return Math.min(maxExtract, energy);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energy;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return capacity;
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
