package theflogat.technomancy.common.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.util.InvHelper;
import theflogat.technomancy.util.RedstoneSet;
import theflogat.technomancy.util.ToolWrench;

public abstract class BlockDynamoBase extends BlockBase {
		
	public BlockDynamoBase() {
		super();
	}

	@Override
	public void breakBlock(World w, int x, int y, int z, Block block, int meta) {
		TileDynamoBase tile = (TileDynamoBase) w.getTileEntity(x, y, z);
		if(tile.boost) {
			InvHelper.spawnEntItem(w, x, y, z, new ItemStack(TMItems.itemBoost, 1));
		}
		dropCurrentModifier(tile, w, x, y, z);
		super.breakBlock(w, x, y, z, block, meta);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		TileDynamoBase tile = (TileDynamoBase)world.getTileEntity(x, y, z);
		if (player.getHeldItem() != null) {
			if (ToolWrench.isWrench(player.getHeldItem())) {
				tile.rotateBlock();
				return true;
			}else if(player.getHeldItem().getItem()==Items.gunpowder && tile.set != RedstoneSet.NONE){
				if(--player.inventory.mainInventory[player.inventory.currentItem].stackSize == 0) {
					player.inventory.mainInventory[player.inventory.currentItem] = null;
				}
				if(!world.isRemote) {
					dropCurrentModifier(tile, world, x, y, z);
				}
				tile.set = RedstoneSet.NONE;
				tile.modified = true;
				return true;
			} else if(player.getHeldItem().getItem()==Items.redstone) {
				if(tile.set != RedstoneSet.HIGH) {
					if(--player.inventory.mainInventory[player.inventory.currentItem].stackSize == 0) {
						player.inventory.mainInventory[player.inventory.currentItem] = null;
					}
					if(!world.isRemote) {
						dropCurrentModifier(tile, world, x, y, z);
					}
					tile.set = RedstoneSet.HIGH;
					tile.modified = true;
				}
				return true;
			}else if(player.getHeldItem().getItem()==Item.getItemFromBlock(Blocks.redstone_torch)){
				if(tile.set != RedstoneSet.LOW) {
					if(--player.inventory.mainInventory[player.inventory.currentItem].stackSize == 0) {
						player.inventory.mainInventory[player.inventory.currentItem] = null;
					}
					if(!world.isRemote) {
						dropCurrentModifier(tile, world, x, y, z);
					}
					tile.set = RedstoneSet.LOW;
					tile.modified = true;
				}
				return true;
			}
		} else if (player.isSneaking()) {
			if(tile.modified) {
				if(!world.isRemote) {
					dropCurrentModifier(tile, world, x, y, z);
				}
				tile.modified = false;
				tile.set = RedstoneSet.HIGH;
				return true;
			}
			if(tile.boost) {
				if(!world.isRemote) {
					InvHelper.spawnEntItem(world, x, y, z, new ItemStack(TMItems.itemBoost, 1));
				}
				tile.setBoost(false);
				return true;
			}
		}
		return false;
	}

	protected void dropCurrentModifier(TileDynamoBase tile, World w, int x, int y, int z) {
		if(tile.modified) {
			ItemStack item = null;
			switch (tile.set) {
			case LOW:
				item = new ItemStack(Item.getItemFromBlock(Blocks.redstone_torch), 1);
				break;
			case HIGH:
				item = new ItemStack(Items.redstone, 1);
				break;
			case NONE:
				item = new ItemStack(Items.gunpowder, 1);
				break;
			}
			InvHelper.spawnEntItem(w, x, y, z, item);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

}