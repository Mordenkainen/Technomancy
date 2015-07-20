package theflogat.technomancy.common.rituals.w;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RitualWaterT3 extends RitualWater {

	public RitualWaterT3() {
		super(new Type[]{Type.WATER,Type.WATER,Type.WATER},Type.WATER, 9, 9);
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {
		w.setBlockToAir(x, y, z);
		removeFrame(w, x, y, z);
		
		for(int k = 0; k < y; k++) {
			for(int i = -radiusX; i <= radiusX; i++) {
				for(int j = -radiusZ; j <= radiusZ; j++) {
					if(w.getBlock(x + i, k, z + j).isAir(w, x + i, k, z + j)||w.getBlock(x + i, k, z + j)
							.canReplace(w, x + i, k, z + j, w.getBlockMetadata(x + i, k, z + j), new ItemStack(Blocks.water))){
						w.setBlock(x + i, k, z + j, Blocks.water);
					}
				}
			}
		}
	}
}