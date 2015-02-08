package theflogat.technomancy.common.rituals;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theflogat.technomancy.api.rituals.Ritual;
import theflogat.technomancy.util.RitualHelper;

public class RitualWaterT3 extends Ritual{

	@Override
	public boolean isCoreComplete(World w, int x, int y, int z) {
		return w.getBlockMetadata(x, y, z)==blue;
	}

	@Override
	public boolean isFrameComplete(World w, int x, int y, int z) {
		return RitualHelper.checkForT1(w, x, y, z, blue) && RitualHelper.checkForT2(w, x, y, z, blue) && RitualHelper.checkForT3(w, x, y, z, blue);
	}

	@Override
	public boolean applyEffect(World w, int x, int y, int z) {

		w.setBlockToAir(x, y, z);
		RitualHelper.removeT1(w, x, y, z);
		RitualHelper.removeT2(w, x, y, z);
		RitualHelper.removeT3(w, x, y, z);
		
		for(int k=0; k<y; k++){
			for(int i=-9; i<=9; i++){
				for(int j=-9; j<=9; i++){
					if(w.getBlock(x+i, k, z+j).isAir(w, x+i, k, z+j)||w.getBlock(x+i, k, z+j)
							.canReplace(w, x+i, k, z+j, w.getBlockMetadata(x+i, k, z+j), new ItemStack(Blocks.water))){
						w.setBlock(x+i, k, z+j, Blocks.water);
					}
				}
			}
		}
		return true;
	}

	@Override
	public void afterEffect(World w, int x, int y, int z) {

	}

}