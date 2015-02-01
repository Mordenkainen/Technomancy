package theflogat.technomancy.common.blocks.technom;

import theflogat.technomancy.common.tiles.technom.TileFakeAir;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFakeAir extends BlockContainer{

	public BlockFakeAir() {
		super(Material.air);
		setBlockUnbreakable();
	}
	
	@Override
	public boolean isAir(IBlockAccess world, int x, int y, int z) {
		return true;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileFakeAir();
	}

}
