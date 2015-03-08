package theflogat.technomancy.common.blocks.air;

import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFakeAirNG extends BlockContainer{

	public BlockFakeAirNG() {
		super(Material.carpet);
		setBlockUnbreakable();
		setBlockName(Ref.getId(Names.fakeAirNG));
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(Ref.getAsset(Names.fakeAirNG));
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileFakeAirNG();
	}

}
