package theflogat.technomancy.common.blocks.air;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theflogat.technomancy.common.tiles.air.TileFakeAirCore;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;

public class BlockFakeAirLight extends BlockContainer {

    public BlockFakeAirLight() {
        super(Material.air);
        setBlockName(Reference.getId(Names.FAKEAIRLIGHT));
        setLightLevel(1F);
    }

    @Override
    public boolean isAir(final IBlockAccess world, final int x, final int y, final int z) {
        return true;
    }

    @Override
    public boolean isBlockNormalCube() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(final World w, final int meta) {
        return new TileFakeAirCore();
    }

}