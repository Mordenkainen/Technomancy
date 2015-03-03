package theflogat.technomancy.common.blocks.air;

import theflogat.technomancy.common.tiles.air.TileFakeAirCore;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFakeAirLight extends BlockContainer{

	public BlockFakeAirLight() {
		super(Material.air);
		setBlockName(Ref.getId(Names.fakeAirLight));
		setLightLevel(1F);
	}
	
	@Override
	public boolean isAir(IBlockAccess world, int x, int y, int z) {
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
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileFakeAirCore();
	}

}