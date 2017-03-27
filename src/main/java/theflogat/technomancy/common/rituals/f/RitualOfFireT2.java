package theflogat.technomancy.common.rituals.f;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class RitualOfFireT2 extends RitualOfFireBase {

    public RitualOfFireT2() {
        super(new Type[] { Type.FIRE, Type.FIRE }, Type.FIRE);
    }

    @Override
    protected void doAction(final World world, final int x, final int y, final int z, final int xOffset, final int yOffset, final int zOffset) {
        if (world.getBlock(x + xOffset, y + yOffset, z + yOffset) == null || world.getBlock(x + xOffset, y + yOffset, z + yOffset).isAir(world, x + xOffset, y + yOffset, z + yOffset)) {
            world.setBlock(x + xOffset, y + yOffset, z + yOffset, Blocks.lava);
        }
    }

}
