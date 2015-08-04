package theflogat.technomancy.common.tiles.existence;

import net.minecraft.nbt.NBTTagCompound;
import theflogat.technomancy.common.tiles.base.TileMachineBase;

public class TileExistenceProducer extends TileMachineBase implements IExistenceStorage{

	public TileExistenceProducer(int capacity) {
		super(100000);
	}
	
	public int power = 0;
	private static final int maxPower = 100;
	
	@Override
	public void updateEntity() {
		if(power<maxPower && energy>=10000){
			energy -= 10000;
			power++;
		}
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		super.readCustomNBT(comp);
		power = comp.getInteger("power");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		super.writeCustomNBT(comp);
		comp.setInteger("power", power);
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
		return 4;
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