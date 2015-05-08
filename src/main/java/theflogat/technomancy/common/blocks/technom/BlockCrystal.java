package theflogat.technomancy.common.blocks.technom;

import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerBase;
import theflogat.technomancy.common.tiles.technom.TileCrystal;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;

public class BlockCrystal extends BlockContainerBase{

	public BlockCrystal() {
		setBlockName(Ref.MOD_PREFIX + Names.crystalBlock);
		setLightLevel(1);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z) {
//				if(w != null && w.getBlock(x, y-1, z) instanceof BlockCrystal){
//					if(w.getBlock(x, y-2, z) instanceof BlockCrystal){
//						return AxisAlignedBB.getBoundingBox(0.375, 0, 0.375, 0.625, 1, 0.625);
//					}
//					return AxisAlignedBB.getBoundingBox(0.25, 0, 0.25, 0.75, 1, 0.75);
//				}
		return super.getCollisionBoundingBoxFromPool(w, x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase ent, ItemStack items) {
		w.setBlockMetadataWithNotify(x, y, z, items.getItemDamage(), 3);
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
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderIds.idCrystal;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubBlocks(Item id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, 0));
		list.add(new ItemStack(id, 1, 1));
		list.add(new ItemStack(id, 1, 2));
		list.add(new ItemStack(id, 1, 3));
		list.add(new ItemStack(id, 1, 4));
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileCrystal();
	}

	public IIcon[] icons = new IIcon[5];

	@Override
	public void registerBlockIcons(IIconRegister icon) {
		for(int i=0; i<icons.length; i++)
			icons[i] = icon.registerIcon(Ref.getAsset(Names.catalyst) + "_" + i);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return icons[meta];
	}
}