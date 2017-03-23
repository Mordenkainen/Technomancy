package theflogat.technomancy.common.blocks.thaumcraft.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.BlockProcessor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileTCProcessor;

public class BlockTCProcessor extends BlockProcessor {

    public BlockTCProcessor() {
        name = "TC";
    }

    @Override
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ))
            return true;
        if (player != null) {
            TileEntity tile = w.getTileEntity(x, y, z);
            if (tile instanceof TileTCProcessor) {
                player.openGui(Technomancy.instance, 0, w, x, y, z);
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World w, int meta) {
        return new TileTCProcessor();
    }
}