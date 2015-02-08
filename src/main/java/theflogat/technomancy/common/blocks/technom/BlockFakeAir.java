package theflogat.technomancy.common.blocks.technom;

import theflogat.technomancy.common.tiles.technom.TileFakeAir;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFakeAir extends BlockContainer{

	public BlockFakeAir() {
		super(Material.carpet);
		setBlockUnbreakable();
		setBlockName(Ref.getId(Names.fakeAir));
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(Ref.getAsset(Names.fakeAir));
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
