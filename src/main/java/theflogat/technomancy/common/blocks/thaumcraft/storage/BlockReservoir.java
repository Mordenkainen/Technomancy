package theflogat.technomancy.common.blocks.thaumcraft.storage;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileEssentiaReservoir;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;

public class BlockReservoir extends BlockContainerAdvanced {

    public BlockReservoir() {
        setBlockName(Reference.getId(Names.RESERVOIR));
        setBlockBounds(0.125F, 0.125F, 0.125F, 0.875F, 0.875F, 0.875F);
    }

    @Override
    public boolean isBlockNormalCube() {
        return false;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess w, int x, int y, int z, int side) {
        return false;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        blockIcon = reg.registerIcon(Reference.getAsset(Names.RESERVOIR));
    }

    @Override
    public TileEntity createNewTileEntity(World w, int meta) {
        return new TileEssentiaReservoir();
    }

}
