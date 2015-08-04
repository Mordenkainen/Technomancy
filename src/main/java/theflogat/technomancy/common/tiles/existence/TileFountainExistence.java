package theflogat.technomancy.common.tiles.existence;

import net.minecraft.nbt.NBTTagCompound;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;

public class TileFountainExistence extends TileTechnomancy implements IExistenceStorage{
	
	public int power;
	private static final int powerCap = 1000000;
	private static final int prodRate = 500;
	
	@Override
	public void updateEntity() {
		power += Math.min(prodRate, powerCap - power);
	}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		power = comp.getInteger("power");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		comp.setInteger("power", power);
	}

	@Override
	public void writeSyncData(NBTTagCompound compound) {
		writeToNBT(compound);
	}

	@Override
	public void readSyncData(NBTTagCompound compound) {
		readFromNBT(compound);
	}

	@Override
	public int getPower() {
		return power;
	}

	@Override
	public int getPowerCap() {
		return powerCap;
	}

	@Override
	public int getMaxRate() {
		return prodRate * 4;
	}

	@Override
	public boolean canInput() {
		return false;
	}

	@Override
	public boolean canOutput() {
		return true;
	}
}