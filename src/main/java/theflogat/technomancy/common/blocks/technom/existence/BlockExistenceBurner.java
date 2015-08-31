package theflogat.technomancy.common.blocks.technom.existence;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerMultiTiles;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceBurner;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceDynamicBurner;
import theflogat.technomancy.lib.RenderIds;

public class BlockExistenceBurner extends BlockContainerMultiTiles{
	
	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase ent, ItemStack items) {
		w.setBlockMetadataWithNotify(x, y, z, items.getItemDamage(), 3);
		setBlockBounds(0.25F, 0, 0.25F, 0.75F, 1, 0.75F);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = Blocks.anvil.getIcon(0, 0);
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return RenderIds.idExBurner;
	}

	@Override
	public TileEntity getTile(int meta) {
		return meta==0 ? new TileExistenceBurner() : new TileExistenceDynamicBurner();
	}
}