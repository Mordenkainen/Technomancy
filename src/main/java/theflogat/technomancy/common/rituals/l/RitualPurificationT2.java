package theflogat.technomancy.common.rituals.l;

import net.minecraft.world.World;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;

public class RitualPurificationT2 extends RitualPurification {

    public RitualPurificationT2() {
        super(new Type[] { Type.LIGHT, Type.LIGHT }, Type.LIGHT, 1, 1, -1, 11);
    }

    @Override
    public boolean canApplyEffect(final World w, final int x, final int y, final int z) {
        return true;
    }

    @Override
    public void applyEffect(final World w, final int x, final int y, final int z) {
        ((TileCatalyst) w.getTileEntity(x, y, z)).handler = this;
    }
}
