package theflogat.technomancy.common.rituals.l;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;

public class RitualPurificationT2 extends RitualPurification {
	
	public RitualPurificationT2() {
		super(new Type[]{Type.LIGHT,Type.LIGHT},Type.LIGHT, 1, 1, -1, 11);
	}

	@Override
	public boolean canApplyEffect(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {
		((TileCatalyst)w.getTileEntity(new BlockPos(x, y, z))).handler = this;
	}
}
