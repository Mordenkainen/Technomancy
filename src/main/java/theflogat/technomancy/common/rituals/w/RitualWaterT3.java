package theflogat.technomancy.common.rituals.w;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualWaterT3 extends RitualWater {

	public RitualWaterT3() {
		super(new Type[]{Type.WATER,Type.WATER,Type.WATER},Type.WATER, 9, 9);
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {
		w.setBlockToAir(new BlockPos(x, y, z));
		removeFrame(w, x, y, z);
		
		for(int k = 0; k < y; k++) {
			for(int i = -radiusX; i <= radiusX; i++) {
				for(int j = -radiusZ; j <= radiusZ; j++) {
					if(w.isAirBlock(new BlockPos(x + i, k, z + j))||w.getBlockState(new BlockPos(x + i, k, z + j)).getBlock()
							.canPlaceBlockAt(w, new BlockPos(x + i, k, z + j))){
						w.setBlockState(new BlockPos(x + i, k, z + j), Blocks.WATER.getDefaultState());
					}
				}
			}
		}
	}
}