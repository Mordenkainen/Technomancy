package theflogat.technomancy.common.blocks.machines;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileTeslaCoil;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.lib.compat.Thaumcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTeslaCoil extends BlockBase {

	public BlockTeslaCoil() {
		setBlockName(Ref.MOD_PREFIX + Names.teslaCoil);
		setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileTeslaCoil();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		TileTeslaCoil tile = getTE(world, x, y, z);
		if(tile != null) {
			ItemStack stack = player.getHeldItem();
			if(stack == null && player.isSneaking()) {
				if (tile.aspectFilter != null ) {
					tile.aspectFilter = null;
					if (world.isRemote) {
						world.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
					}else{
						if (!player.inventory.addItemStackToInventory(new ItemStack(Thaumcraft.itemResource, 1, 13))) {
							ForgeDirection fd = ForgeDirection.getOrientation(ForgeDirection.OPPOSITES[tile.facing]);
							world.spawnEntityInWorld(new EntityItem(world, x + 0.5F + fd.offsetX / 3.0F, y + 0.5F, z + 0.5F + fd.offsetZ /
									3.0F, new ItemStack(Thaumcraft.itemResource, 1, 13)));
						}
					}
				}
			}
			if(stack != null) {
				if (stack.getItemDamage() == 13 && stack.getItem() == Thaumcraft.itemResource && stack.getItem() instanceof IEssentiaContainerItem) {
					if (((IEssentiaContainerItem)stack.getItem()).getAspects(stack) != null && tile.aspectFilter == null) {
						tile.aspectFilter = ((IEssentiaContainerItem)stack.getItem()).getAspects(stack).getAspects()[0];
						stack.stackSize -= 1;
						world.markBlockForUpdate(x, y, z);
						if(world.isRemote) {
							world.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
						}						
					}				
				}
			}
		}
		return false;
	}
	
	//FIXME: This may be unstable since this class is a singleton!
	private int facing;
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
		TileTeslaCoil tile = getTE(world, x, y, z);
		if(tile != null) {
			tile.facing = this.facing;
		}
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta){
		this.facing = ForgeDirection.OPPOSITES[side];
		return side + meta;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileTeslaCoil tile = getTE(world, x, y, z);
		if(tile != null) {
			switch(tile.facing) {
				case 0:
					setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
					break;
				case 1:
					setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
					break;
				case 2:
					setBlockBounds(0.1875F, 0.1875F, 0.0F, 0.8125F, 0.8125F, 1.0F);
					break;
				case 3:
					setBlockBounds(0.1875F, 0.1875F, 0.0F, 0.8125F, 0.8125F, 1.0F);
					break;
				case 4:
					setBlockBounds(0.0F, 0.1875F, 0.1875F, 1.0F, 0.8125F, 0.8125F);
					break;
				case 5:
					setBlockBounds(0.0F, 0.1875F, 0.1875F, 1.0F, 0.8125F, 0.8125F);
					break;
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
		return RenderIds.idTeslaCoil;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(Ref.getAsset(Names.teslaCoil));
	}

	private TileTeslaCoil getTE(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileTeslaCoil) {
			return (TileTeslaCoil)tile;
		}
		return null;
	}
}
