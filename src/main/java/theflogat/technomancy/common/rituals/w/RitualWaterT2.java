package theflogat.technomancy.common.rituals.w;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theflogat.technomancy.api.rituals.Ritual;
import theflogat.technomancy.util.RitualHelper;

public class RitualWaterT2 extends Ritual{

	@Override
	public boolean isCoreComplete(World w, int x, int y, int z) {
		return w.getBlockMetadata(x, y, z)==blue;
	}

	@Override
	public boolean isFrameComplete(World w, int x, int y, int z) {
		return RitualHelper.checkForT1(w, x, y, z, blue) && RitualHelper.checkForT2(w, x, y, z, blue);
	}

	@Override
	public boolean canApplyEffect(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {
		for(int i=-3; i<4; i++){
			for(int j=-3; j<4; i++){
				if(w.getBlock(x+i, y-1, z+j).isAir(w, x+i, y-1, z+j)||w.getBlock(x+i, y-1, z+j)
						.canReplace(w, x+i, y-1, z+j, w.getBlockMetadata(x+i, y-1, z+j), new ItemStack(Blocks.water))){
					w.setBlock(x+i, y-1, z+j, Blocks.water);
				}
			}
		}
	}
}