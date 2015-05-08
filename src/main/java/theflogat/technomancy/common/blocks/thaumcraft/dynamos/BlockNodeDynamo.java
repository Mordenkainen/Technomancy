package theflogat.technomancy.common.blocks.thaumcraft.dynamos;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockDynamoBase;
import theflogat.technomancy.common.tiles.thaumcraft.dynamos.TileNodeDynamo;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNodeDynamo extends BlockDynamoBase {

	public BlockNodeDynamo() {
		setBlockName(Ref.MOD_PREFIX + Names.nodeDynamo);
	}
	
	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase entity, ItemStack items) {
		super.onBlockPlacedBy(w, x, y, z, entity, items);
		TileNodeDynamo tile = (TileNodeDynamo)w.getTileEntity(x, y, z);
		tile.facing = 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = Blocks.stone.getIcon(0, 0);
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileNodeDynamo();
	}

	@Override
	public int getRenderType() {
		return RenderIds.idNodeDynamo;
	}
}
