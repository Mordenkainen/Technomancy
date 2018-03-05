package theflogat.technomancy.common.blocks.air;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theflogat.technomancy.common.tiles.air.TileFakeAirCore;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class BlockFakeAirLight extends BlockContainer {

	public BlockFakeAirLight() {
		super(Material.AIR);
		setUnlocalizedName(Ref.getId(Names.fakeAirLight));
		setLightLevel(1F);
	}

	@Override
	public boolean isAir(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileFakeAirCore();
	}

}