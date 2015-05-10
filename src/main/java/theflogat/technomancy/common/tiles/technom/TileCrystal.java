package theflogat.technomancy.common.tiles.technom;

import net.minecraft.nbt.NBTTagCompound;
import theflogat.technomancy.common.blocks.technom.BlockCrystal;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;

public class TileCrystal extends TileTechnomancy{
	
	public int getStage() {
		int i = 0;
		if(worldObj!=null && worldObj.getBlock(xCoord, yCoord-1, zCoord) instanceof BlockCrystal){
			i++;
			if(worldObj.getBlock(xCoord, yCoord-2, zCoord) instanceof BlockCrystal){
				i++;
			}
		}
		return i;
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		
	}

}
