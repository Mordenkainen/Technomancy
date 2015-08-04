package theflogat.technomancy.common.blocks.thaumcraft.machines;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.common.config.ConfigItems;
import theflogat.technomancy.common.blocks.base.BlockContainerRedstone;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaFusor;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.util.helpers.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockEssentiaFusor extends BlockContainerRedstone{

	public BlockEssentiaFusor() {
		setBlockName(Ref.MOD_PREFIX + Names.fusor);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 0.75F, 1F);
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEssentiaFusor();
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
		return RenderIds.idEssentiaFusor;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		if(ForgeDirection.getOrientation(side) == ForgeDirection.UP) {
			ForgeDirection slot = getSideFromPos(vecX, vecZ);
			TileEssentiaFusor tile = getTE(world, x, y, z);
			if(slot != null && tile != null) {
				if(player.getHeldItem() == null && tile.isSideOccupied(slot)) {
					ItemStack toDrop = world.isRemote? tile.getItemForSlot(slot) : tile.clearSide(slot);
					if(toDrop != null) {
						if(!world.isRemote) {
							WorldHelper.spawnEntItem(world, player.posX, player.posY, player.posZ, toDrop);
						}
						world.markBlockForUpdate(x, y, z);
						tile.markDirty();
						return true;
					}
				} else if(player.getHeldItem() != null && player.getHeldItem().getItem() == ConfigItems.itemEssence && !tile.isSideOccupied(slot)) {
					if(!world.isRemote) {
						if(tile.markSide(slot, player.getHeldItem())) {
							if(--player.inventory.mainInventory[player.inventory.currentItem].stackSize == 0) {
								player.inventory.mainInventory[player.inventory.currentItem] = null;
							}
						}
					}
					world.markBlockForUpdate(x, y, z);
					tile.markDirty();
					return true;
				}
			}
		}
		return super.onBlockActivated(world, x, y, z, player, side, vecX, vecY, vecZ);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(Ref.getAsset(Names.fusor));
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block id, int meta) {
		if(!world.isRemote) {
			TileEssentiaFusor tile = getTE(world, x, y, z);
			if(tile != null) {
			    for(ForgeDirection side: ForgeDirection.VALID_DIRECTIONS) {
			    	if(side != ForgeDirection.UP && side != ForgeDirection.DOWN) {
			    		if(tile.isSideOccupied(side)) {
			    			ItemStack toDrop = tile.getItemForSlot(side);
							if(toDrop != null) {
								WorldHelper.spawnEntItem(world, x, y, z, toDrop);
							}
			    		}
			    	}
			    }
			}
		}
	    super.breakBlock(world, x, y, z, id, meta);
	}

	private static ForgeDirection getSideFromPos(float vecX, float vecZ) {
		if(vecZ < .33 && vecX > .33 && vecX < .66) return ForgeDirection.NORTH;
		if(vecZ > .66 && vecX > .33 && vecX < .66) return ForgeDirection.SOUTH;
		if(vecX < .33 && vecZ > .33 && vecZ < .66) return ForgeDirection.WEST;
		if(vecX > .66 && vecZ > .33 && vecZ < .66) return ForgeDirection.EAST;
		return null;
	}
	
	private static TileEssentiaFusor getTE(IBlockAccess access, int x, int y, int z) {
		TileEntity tile = access.getTileEntity(x, y, z);
		return tile instanceof TileEssentiaFusor ? (TileEssentiaFusor)tile : null;
	}
}
