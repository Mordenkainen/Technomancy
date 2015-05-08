package theflogat.technomancy.common.blocks.botania.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.botania.machines.TileManaFabricator;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import vazkii.botania.api.wand.IWandHUD;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockManaFabricator extends BlockContainerAdvanced implements IWandHUD {

	public BlockManaFabricator() {
		setBlockName(Ref.MOD_PREFIX + Names.manaFabricator);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileManaFabricator();
	}
	
	private int facing;	
	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase entity, ItemStack items){
		super.onBlockPlacedBy(w, x, y, z, entity, items);
		TileEntity tile = w.getTileEntity(x, y, z);
		if(tile instanceof TileManaFabricator) {
			((TileManaFabricator)tile).facing = this.facing;
		}
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta){
		this.facing = ForgeDirection.OPPOSITES[side];
		return side + meta;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileManaFabricator) {
			switch(((TileManaFabricator)tile).facing) {
			case 0:
				setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);break;
			case 1:
				setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);break;
			case 2:
				setBlockBounds(0.1875F, 0.1875F, 0.0F, 0.8125F, 0.8125F, 1.0F);break;
			case 3:
				setBlockBounds(0.1875F, 0.1875F, 0.0F, 0.8125F, 0.8125F, 1.0F);break;
			case 4:
				setBlockBounds(0.0F, 0.1875F, 0.1875F, 1.0F, 0.8125F, 0.8125F);break;
			case 5:
				setBlockBounds(0.0F, 0.1875F, 0.1875F, 1.0F, 0.8125F, 0.8125F);
			}
		}
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return RenderIds.idManaFabricator;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(Ref.getAsset(Names.manaFabricator));
	}
	
	@Override
	public void renderHUD(Minecraft mc, ScaledResolution res, World world, int x, int y, int z) {
		((TileManaFabricator)world.getTileEntity(x, y, z)).renderHUD(mc, res);
	}

}
