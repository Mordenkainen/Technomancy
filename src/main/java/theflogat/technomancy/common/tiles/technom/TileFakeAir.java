package theflogat.technomancy.common.tiles.technom;

import net.minecraft.nbt.NBTTagCompound;
import theflogat.technomancy.common.blocks.machines.BlockNodeGenerator;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;

public class TileFakeAir extends TileTechnomancy{
	
	private int x;
	private int y;
	private int z;
	
	@Override
	public void updateEntity() {
		if(!(worldObj.getBlock(x, y, z) instanceof BlockNodeGenerator)){
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
	}
	
	public void addMain(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		x = comp.getInteger("mainx");
		y = comp.getInteger("mainy");
		z = comp.getInteger("mainz");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		comp.setInteger("mainx", x);
		comp.setInteger("mainy", y);
		comp.setInteger("mainz", z);
	}

}
