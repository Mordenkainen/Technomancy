package theflogat.technomancy.common.rituals.f;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import theflogat.technomancy.api.rituals.Ritual;
import theflogat.technomancy.api.rituals.Ritual.Type;
import theflogat.technomancy.util.RitualHelper;

public class RitualOfFireT1 extends Ritual{

	public RitualOfFireT1() {
		super(new Type[]{Type.FIRE},Type.FIRE);
	}
	
	@Override
	public boolean canApplyEffect(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {
		w.setBlockToAir(x, y, z);
		removeFrame(w, x, y, z);
			
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
	}

}