package theflogat.technomancy.common.blocks.technom.existence;

import java.util.Random;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerBase;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceFountain;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockExistenceFountain extends BlockContainerBase {

    public BlockExistenceFountain() {
        setBlockName(Ref.getId(Names.existenceFountain));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World w, int x, int y, int z, Random rand) {
        super.randomDisplayTick(w, x, y, z, rand);
        if (((TileExistenceFountain) w.getTileEntity(x, y, z)).isRunning()) {
            for (int l = x - 2; l <= x + 2; ++l) {
                for (int i1 = z - 2; i1 <= z + 2; ++i1) {
                    if (l > x - 2 && l < x + 2 && i1 == z - 1) {
                        i1 = z + 2;
                    }
                    if (rand.nextInt(16) == 0) {
                        for (int j1 = y; j1 <= y + 1; ++j1) {
                            w.spawnParticle("enchantmenttable", x + 0.5D, y + 2.0D, z + 0.5D, l - x + rand.nextFloat() - 0.5D, j1 - y - rand.nextFloat() - 1.0F, i1 - z + rand.nextFloat() - 0.5D);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        blockIcon = Blocks.cobblestone.getIcon(0, 0);
    }

    @Override
    public int getRenderType() {
        return RenderIds.idExFountain;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World w, int meta) {
        return new TileExistenceFountain();
    }
}
