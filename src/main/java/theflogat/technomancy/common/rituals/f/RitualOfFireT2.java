package theflogat.technomancy.common.rituals.f;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import theflogat.technomancy.api.rituals.Ritual;

public class RitualOfFireT2 extends Ritual {
	
	public RitualOfFireT2() {
		super(new Type[]{Type.FIRE,Type.FIRE},Type.FIRE);
	}

	@Override
	public boolean canApplyEffect(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {
		w.setBlockToAir(new BlockPos(x, y, z));
		removeFrame(w, x, y, z);
			
		for(int i =- 9; i <= 9; i++) {
			for(int j =- 9; j <= 9; j++) {
				for(int k =- 19; k < 0; k++) {
					if(w.getBlockState(new BlockPos(x + i, y + k, z+j)) == null || w.isAirBlock(new BlockPos(x + i, y + k, z + j))){
						w.setBlockState(new BlockPos(x + i, y + k, z + j), Blocks.LAVA.getDefaultState());
					}
				}
			}
		}
	}

}