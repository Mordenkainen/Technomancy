package theflogat.technomancy.common.rituals.f;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import theflogat.technomancy.api.rituals.Ritual;
import theflogat.technomancy.util.RitualHelper;

public class RitualOfFireT2 extends Ritual{

	@Override
	public boolean isCoreComplete(World w, int x, int y, int z) {
		return w.getBlockMetadata(x, y, z)==red;
	}

	@Override
	public boolean isFrameComplete(World w, int x, int y, int z) {
		return RitualHelper.checkForT1(w, x, y, z, red) && RitualHelper.checkForT2(w, x, y, z, red);
	}

	@Override
	public boolean canApplyEffect(World w, int x, int y, int z) {
		RitualHelper.removeT1(w, x, y, z);
		RitualHelper.removeT2(w, x, y, z);
		w.setBlockToAir(x, y, z);
			
		for(int i=-9; i<=9; i++){
			for(int j=-9; j<=9; j++){
				for(int k=-19; k<0; k++){
					Block bl = w.getBlock(x+i, y+k, z+j);
					if(bl!=null&&bl.getMaterial()==Material.water){
						w.setBlock(x+i, y+k, z+j, Blocks.obsidian);
					}
				}
			}
		}
		
		return true;
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {}

}