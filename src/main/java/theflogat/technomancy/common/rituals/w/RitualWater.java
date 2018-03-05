package theflogat.technomancy.common.rituals.w;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import theflogat.technomancy.api.rituals.Ritual;

public class RitualWater extends Ritual {
	protected int radiusX, radiusZ;
	
	public RitualWater(Type[] frame, Type core, int radX, int radZ) {
		super(frame, core);
		radiusX = radX;
		radiusZ = radZ;
	}

	@Override
	public boolean canApplyEffect(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {
		w.setBlockToAir(new BlockPos(x, y, z));
		removeFrame(w, x, y, z);
		
		for(int i = -radiusX; i <= radiusX; i++) {
			for(int j = -radiusZ; j <= radiusZ; j++) {
				if(w.isAirBlock(new BlockPos(x + i, y - 1, z + j))||w.getBlockState(new BlockPos(x + i, y - 1, z + j)).getBlock()
						.canPlaceBlockAt(w, new BlockPos(x + i, y - 1, z + j))) {
					w.setBlockState(new BlockPos(x + i, y - 1, z + j), Blocks.WATER.getDefaultState());
				}
			}
		}
	}

}
