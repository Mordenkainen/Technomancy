package theflogat.technomancy.common.rituals.w;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
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
		w.setBlockToAir(x, y, z);
		removeFrame(w, x, y, z);
		
		for(int i = -radiusX; i <= radiusX; i++) {
			for(int j = -radiusZ; j <= radiusZ; j++) {
				if(w.getBlock(x + i, y - 1, z + j).isAir(w, x + i, y - 1, z + j)||w.getBlock(x + i, y - 1, z + j)
						.canReplace(w, x + i, y - 1, z + j, w.getBlockMetadata(x + i, y - 1, z + j), new ItemStack(Blocks.water))) {
					w.setBlock(x + i, y - 1, z + j, Blocks.water);
				}
			}
		}
	}

}
