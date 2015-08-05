package theflogat.technomancy.common.tiles.technom.existence;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.nbt.NBTTagCompound;
import theflogat.technomancy.common.tiles.base.TileTechnomancyRedstone;

public class TileExistenceCropAccelerator extends TileTechnomancyRedstone implements IExistenceConsumer{

	public TileExistenceCropAccelerator() {
		super(RedstoneSet.LOW);
	}
	
	public int power;
	
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote){
			if(power>=15){
				for(int xx =-5; xx<=5; xx++){
					for(int zz =-5; zz<=5; zz++){
						Block b = worldObj.getBlock(xCoord + xx, yCoord + 2, zCoord + zz);
						if(b instanceof IGrowable){
							b.updateTick(worldObj, xCoord + xx, yCoord + 2, zCoord + zz, worldObj.rand);
							power -= 15;
						}
					}
				}
			}
		}
	}

	@Override
	public int getPower() {
		return power;
	}

	@Override
	public int getPowerCap() {
		return 1000;
	}

	@Override
	public int getMaxRate() {
		return 20;
	}

	@Override
	public void addPower(int val) {
		power += val;
	}

	@Override
	public boolean canInput() {
		return power<getPowerCap();
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
