package theflogat.technomancy.common.rituals;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theflogat.technomancy.api.rituals.Ritual;
import theflogat.technomancy.util.RitualHelper;

public class RitualWaterT1 extends Ritual{

	@Override
	public boolean isCoreComplete(World w, int x, int y, int z) {
		return w.getBlockMetadata(x, y, z)==blue;
	}

	@Override
	public boolean isFrameComplete(World w, int x, int y, int z) {
		return RitualHelper.checkForT1(w, x, y, z, blue);
	}

	@Override
	public boolean applyEffect(World w, int x, int y, int z) {
		for(int i=-1; i<2; i++){
			for(int j=-1; j<2; i++){
				if(w.getBlock(x+i, y-1, z+j).isAir(w, x+i, y-1, z+j)||w.getBlock(x+i, y-1, z+j)
						.canReplace(w, x+i, y-1, z+j, w.getBlockMetadata(x+i, y-1, z+j), new ItemStack(Blocks.water))){
					w.setBlock(x+i, y-1, z+j, Blocks.water);
				}
			}
		}
		return true;
	}

	@Override
	public void afterEffect(World w, int x, int y, int z) {
		
	}

}
