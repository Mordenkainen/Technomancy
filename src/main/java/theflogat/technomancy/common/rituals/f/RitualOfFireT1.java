package theflogat.technomancy.common.rituals.f;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class RitualOfFireT1 extends RitualOfFireBase {

    public RitualOfFireT1() {
        super(new Type[] { Type.FIRE }, Type.FIRE);
    }

    @Override
    protected void doAction(final World world, final int x, final int y, final int z, final int xOffset, final int yOffset, final int zOffset) {
        final Block bl = world.getBlock(x + xOffset, y + yOffset, z + zOffset);
        if (bl != null && bl.getMaterial() == Material.water) {
            world.setBlock(x + xOffset, y + yOffset, z + zOffset, Blocks.obsidian);
        }
    }

}
