package theflogat.technomancy.common.blocks.dynamos;

import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.common.tiles.dynamos.TileNodeDynamo;
import theflogat.technomancy.lib.RenderIds;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class BlockNodeDynamo extends BlockBase {
	
	public BlockNodeDynamo() {
		setBlockName("techno:nodeDynamo");
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileNodeDynamo tile = (TileNodeDynamo)world.getTileEntity(x, y, z);
		tile.facing = 0;
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileNodeDynamo();
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderIds.idNodeDynamo;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = Blocks.stone.getIcon(0, 0);
	}
}
