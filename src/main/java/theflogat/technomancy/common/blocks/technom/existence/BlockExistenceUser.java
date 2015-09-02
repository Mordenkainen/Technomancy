package theflogat.technomancy.common.blocks.technom.existence;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerMultiTiles;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceCropAccelerator;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceHarvester;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockExistenceUser extends BlockContainerMultiTiles{

	public BlockExistenceUser() {
		setBlockName(Ref.getId("existenceUser"));
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
	public boolean isBlockNormalCube() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public IIcon[] icons;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[2];
		blockIcon = reg.registerIcon(Ref.getAsset(Names.existenceUser[0]));
		icons[0] = reg.registerIcon(Ref.getAsset(Names.existenceUser[0] + "_top"));
		icons[1] = reg.registerIcon(Ref.getAsset(Names.existenceUser[1]));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		
		if(meta==0){
			if(side==1){
				return icons[0];
			}
			return blockIcon;
		}
		if(meta==1){
			if(side!=0 && side!=1){
				return icons[0];
			}
			return blockIcon;
		}
		return icons[1];
	}

	@Override
	public TileEntity getTile(int meta) {
		switch(meta){
		case 0:
			return new TileExistenceCropAccelerator();
		case 1:
			return new TileExistenceHarvester();
		}
		return null;
	}
}
