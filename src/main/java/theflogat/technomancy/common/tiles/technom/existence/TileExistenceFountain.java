package theflogat.technomancy.common.tiles.technom.existence;

import net.minecraft.nbt.NBTTagCompound;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;

public class TileExistenceFountain extends TileTechnomancy implements IExistenceProducer{
	
	public int power;
	private static final int powerCap = 1000000;
	private static final int prodRate = 500;
	
	@Override
	public void update() {
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
		writeCustomNBT(compound);
	}

	@Override
	public void readSyncData(NBTTagCompound compound) {
		readCustomNBT(compound);
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

	@Override
	public void addPower(int val) {
		power += val;
	}

	public boolean isRunning() {
		return power<powerCap;
	}
}