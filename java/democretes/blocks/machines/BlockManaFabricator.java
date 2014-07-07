package democretes.blocks.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import vazkii.botania.api.IWandHUD;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.blocks.BlockBase;
import democretes.blocks.machines.tiles.TileManaFabricator;
import democretes.lib.Names;
import democretes.lib.Ref;
import democretes.lib.RenderIds;

public class BlockManaFabricator extends BlockBase implements IWandHUD {

	public BlockManaFabricator(int id) {
		super(id);
		setUnlocalizedName(Ref.MOD_PREFIX + Names.manaFabricator);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileManaFabricator();
	}
	
	private int facing;	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
		TileEntity tile = world.getBlockTileEntity(x, y, z);
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
		TileEntity tile = world.getBlockTileEntity(x, y, z);
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
	public Icon iconMana;
	
	@SideOnly(Side.CLIENT)
	public void RegisterIcons(IconRegister icon) {
		this.iconMana = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.condenserBlock);
	}
	
	@Override
	public void renderHUD(Minecraft mc, ScaledResolution res, World world, int x, int y, int z) {
		((TileManaFabricator)world.getBlockTileEntity(x, y, z)).renderHUD(mc, res);
	}

}
