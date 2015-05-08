package theflogat.technomancy.common.blocks.technom;

import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;

public class BlockCatalyst extends BlockContainerAdvanced{

	public BlockCatalyst() {
		setBlockName(Ref.getId(Names.catalyst));
		setCreativeTab(Technomancy.tabsTM);
	}
	
	@Override
	public float getBlockHardness(World w, int x, int y, int z) {
		return ((TileCatalyst)w.getTileEntity(x, y, z)).remCount!=-1 ? -1 : blockHardness;
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ);
		if(player.isSneaking()){
			((TileCatalyst)w.getTileEntity(x, y, z)).activateRitual(player);
			return true;
		}
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase ent, ItemStack items) {
		w.setBlockMetadataWithNotify(x, y, z, items.getItemDamage(), 3);
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item id, CreativeTabs tab, List l) {
		l.add(new ItemStack(id, 1, 0));
		l.add(new ItemStack(id, 1, 1));
		l.add(new ItemStack(id, 1, 2));
		l.add(new ItemStack(id, 1, 3));
		l.add(new ItemStack(id, 1, 4));
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

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileCatalyst();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return RenderIds.idCatalyst;
	}
}
