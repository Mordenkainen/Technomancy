package theflogat.technomancy.common.blocks.thaumcraft.machines;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileCondenser;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCondenser extends BlockContainerAdvanced {

	@SideOnly(Side.CLIENT)
	IIcon[] icons;
	
	public BlockCondenser() {
		setBlockName(Ref.MOD_PREFIX + Names.condenserBlock);
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		boolean flag = super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ);
		if(w.getTileEntity(x, y, z) instanceof TileCondenser && player.isSneaking()){
			return ((TileCondenser) w.getTileEntity(x, y, z)).toggleDir(side);
		}
		return flag;
	}

	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase entity, ItemStack items)    {
		super.onBlockPlacedBy(w, x, y, z, entity, items);
		int rotation = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
		w.setBlockMetadataWithNotify(x, y, z, rotation, 2);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		icons = new IIcon[6];
		icons[0] = icon.registerIcon(Ref.getAsset(Names.condenserBlock + "Front"));
		icons[1] = icon.registerIcon(Ref.getAsset(Names.condenserBlock + "Side"));
		icons[2] = icon.registerIcon(Ref.getAsset(Names.condenserBlock + "Top"));
		icons[3] = icon.registerIcon(Ref.getAsset(Names.condenserBlock + "Bottom"));
		icons[4] = icon.registerIcon(Ref.getAsset(Names.condenserBlock + "Out"));
		icons[5] = icon.registerIcon(Ref.getAsset(Names.condenserBlock + "Out2"));
	}
	
	@Override
	public IIcon getIcon(IBlockAccess w, int x,	int y, int z, int side) {
		if(w.getTileEntity(x, y, z) instanceof TileCondenser){
			if(((TileCondenser)w.getTileEntity(x, y, z)).sides.get(ForgeDirection.VALID_DIRECTIONS[side])){
				if(side==0 || side==1){
					return icons[5];
				}
				return icons[4];
			}
		}
		return getIcon(side, w.getBlockMetadata(x, y, z));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		if(side == 0) {
			return icons[2];
		}
		if(side == 1) {
			return icons[3];
		}
		if(side==getFacingFromMeta(meta)) {
			return icons[0];
		}
		return icons[1];
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileCondenser();
	}
	
	public static int getFacingFromMeta(int meta){
		switch(meta){
		case 0:
			return 3;
		case 1:
			return 4;
		case 2:
			return 2;
		case 3:
			return 5;
		}
		return 0;
	}
}