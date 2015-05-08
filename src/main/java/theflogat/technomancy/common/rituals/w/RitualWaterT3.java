package theflogat.technomancy.common.rituals.w;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theflogat.technomancy.api.rituals.Ritual;

public class RitualWaterT3 extends Ritual{

	public RitualWaterT3() {
		super(new Type[]{Type.WATER,Type.WATER,Type.WATER},Type.WATER);
	}

	@Override
	public boolean canApplyEffect(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void applyEffect(World w, int x, int y, int z){
		w.setBlockToAir(x, y, z);
		removeFrame(w, x, y, z);
		
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
	}
}