package theflogat.technomancy.common.rituals.f;

import net.minecraft.world.World;
import theflogat.technomancy.api.rituals.Ritual;

public abstract class RitualOfFireBase extends Ritual {

    public RitualOfFireBase(final Type[] frame, final Type core) {
        super(frame, core);
    }

    @Override
    public boolean canApplyEffect(final World w, final int x, final int y, final int z) {
        return true;
    }

    @Override
    public void applyEffect(final World w, final int x, final int y, final int z) {
        w.setBlockToAir(x, y, z);
        removeFrame(w, x, y, z);
    
        for (int i = -9; i <= 9; i++) {
            for (int j = -9; j <= 9; j++) {
                for (int k = -19; k < 0; k++) {
                    doAction(w, x, y, z, i, k, j);
                }
            }
        }
    }

    protected abstract void doAction(final World world, int x, int y, int z, int xOffset, int yOffset, int zOffset);
    
}