package theflogat.technomancy.common.blocks.thaumcraft.dynamos;

import theflogat.technomancy.common.blocks.base.BlockDynamoBase;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.common.tiles.thaumcraft.dynamos.TileNodeDynamo;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.util.InvHelper;
import theflogat.technomancy.util.RedstoneSet;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNodeDynamo extends BlockDynamoBase {

	public BlockNodeDynamo() {
		setBlockName(Ref.MOD_PREFIX + Names.nodeDynamo);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		TileDynamoBase tile = (TileDynamoBase)world.getTileEntity(x, y, z);
		if (player.getHeldItem() != null) {
			if(player.getHeldItem().getItem()==Items.gunpowder && tile.set != RedstoneSet.NONE){
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
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileNodeDynamo tile = (TileNodeDynamo)world.getTileEntity(x, y, z);
		tile.facing = 0;
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = Blocks.stone.getIcon(0, 0);
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileNodeDynamo();
	}

	@Override
	public int getRenderType() {
		return RenderIds.idNodeDynamo;
	}
}
