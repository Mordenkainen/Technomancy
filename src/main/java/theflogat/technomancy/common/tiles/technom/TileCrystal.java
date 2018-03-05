package theflogat.technomancy.common.tiles.technom;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import theflogat.technomancy.common.blocks.technom.BlockCrystal;

public class TileCrystal extends TileEntity {
	
	public int getStage() {
		int i = 0;
		if(world!=null && world.getBlockState(new BlockPos(pos.getX(), pos.getY()-1, pos.getZ())).getBlock() instanceof BlockCrystal){
			i++;
			if(world.getBlockState(new BlockPos(pos.getX(), pos.getY()-2, pos.getZ())).getBlock() instanceof BlockCrystal){
				i++;
			}
		}
		return i;
	}
}
