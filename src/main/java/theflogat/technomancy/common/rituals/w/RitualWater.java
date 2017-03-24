package theflogat.technomancy.common.rituals.w;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theflogat.technomancy.api.rituals.Ritual;

public class RitualWater extends Ritual {

    protected int radiusX, radiusZ;

    public RitualWater(final Type[] frame, final Type core, final int radX, final int radZ) {
        super(frame, core);
        radiusX = radX;
        radiusZ = radZ;
    }

    @Override
    public boolean canApplyEffect(final World w, final int x, final int y, final int z) {
        return true;
    }

    @Override
    public void applyEffect(final World w, final int x, final int y, final int z) {
        w.setBlockToAir(x, y, z);
        removeFrame(w, x, y, z);

        for (int i = -radiusX; i <= radiusX; i++) {
            for (int j = -radiusZ; j <= radiusZ; j++) {
                if (w.getBlock(x + i, y - 1, z + j).isAir(w, x + i, y - 1, z + j) || w.getBlock(x + i, y - 1, z + j).canReplace(w, x + i, y - 1, z + j, w.getBlockMetadata(x + i, y - 1, z + j), new ItemStack(Blocks.water))) {
                    w.setBlock(x + i, y - 1, z + j, Blocks.water);
                }
            }
        }
    }

}
