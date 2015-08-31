package theflogat.technomancy.common.tiles.base;

import net.minecraft.nbt.NBTTagCompound;
import theflogat.technomancy.common.tiles.technom.existence.IExistenceConsumer;

public abstract class TileExistenceRedstoneBase extends TileTechnomancyRedstone implements IExistenceConsumer{

	public int power;
	public int maxPower;

	public TileExistenceRedstoneBase(RedstoneSet setDefault, int maxPower) {
		super(setDefault);
		this.maxPower = maxPower;
	}

	@Override
	public int getPower() {
		return power;
	}

	@Override
	public int getPowerCap() {
		return maxPower;
	}

	@Override
	public int getMaxRate() {
		return maxPower/50;
	}

	@Override
	public void addPower(int val) {
		power += val;
	}

	@Override
	public boolean canInput() {
		return true;
	}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		power = comp.getInteger("power");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		comp.setInteger("power", power);
	}
}
