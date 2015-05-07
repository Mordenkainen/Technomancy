package theflogat.technomancy.common.blocks.thaumcraft.machines;

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
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaTransmitter;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.lib.compat.Thaumcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEssentiaTransmitter extends BlockContainerAdvanced {

	public BlockEssentiaTransmitter() {
		setBlockName(Ref.MOD_PREFIX + Names.essentiaTransmitter);
		setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileEssentiaTransmitter();
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEssentiaTransmitter tile = getTE(w, x, y, z);
		if(tile != null) {
			ItemStack stack = player.getHeldItem();
			if(stack == null && player.isSneaking()) {
				if (tile.aspectFilter != null ) {
					tile.aspectFilter = null;
					if (w.isRemote) {
						w.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
					}else{
						if (!player.inventory.addItemStackToInventory(new ItemStack(Thaumcraft.itemResource, 1, 13))) {
							ForgeDirection fd = ForgeDirection.getOrientation(ForgeDirection.OPPOSITES[tile.facing]);
							w.spawnEntityInWorld(new EntityItem(w, x + 0.5F + fd.offsetX / 3.0F, y + 0.5F, z + 0.5F + fd.offsetZ /
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
						w.markBlockForUpdate(x, y, z);
						if(w.isRemote) {
							w.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
						}						
					}				
				}
			}
		}
		return super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ);
	}
	
	//FIXME: This may be unstable since this class is a singleton!
	private int facing;
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
		TileEssentiaTransmitter tile = getTE(world, x, y, z);
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
		TileEssentiaTransmitter tile = getTE(world, x, y, z);
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
		return RenderIds.idEssentiaTransmitter;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(Ref.getAsset(Names.essentiaTransmitter));
	}

	private TileEssentiaTransmitter getTE(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileEssentiaTransmitter) {
			return (TileEssentiaTransmitter)tile;
		}
		return null;
	}
}
