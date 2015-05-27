package theflogat.technomancy.common.blocks.base;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive.RedstoneSet;
import theflogat.technomancy.util.helpers.WorldHelper;

public abstract class BlockContainerRedstone extends BlockContainerBase {

	public static HashMap<Item, RedstoneSet> itemToSetting = new HashMap<Item, RedstoneSet>();
	public static HashMap<RedstoneSet, Item> settingToItem = new HashMap<RedstoneSet, Item>();

	static {
		itemToSetting.put(Items.gunpowder, RedstoneSet.NONE);
		itemToSetting.put(Items.redstone, RedstoneSet.HIGH);
		itemToSetting.put(Item.getItemFromBlock(Blocks.redstone_torch), RedstoneSet.LOW);
		settingToItem.put(RedstoneSet.NONE, Items.gunpowder);
		settingToItem.put(RedstoneSet.HIGH, Items.redstone);
		settingToItem.put(RedstoneSet.LOW, Item.getItemFromBlock(Blocks.redstone_torch));
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		IRedstoneSensitive te = getTE(w, x, y, z);
		if(te != null) {
			ItemStack items = player.inventory.mainInventory[player.inventory.currentItem];
			if(items!=null && itemToSetting.containsKey(items.getItem()) && te.canBeModified()) {
				if(itemToSetting.get(items.getItem())!=te.getCurrentSetting()){
					if(te.isModified()) {
						Item it = settingToItem.get(te.getCurrentSetting());
						if(!w.isRemote) {
							WorldHelper.spawnEntItem(w, x, y, z, new ItemStack(it, 1));
						}
					}
					te.setNewSetting(itemToSetting.get(items.getItem()));
					player.inventory.mainInventory[player.inventory.currentItem] = --player.inventory.mainInventory[player.inventory.currentItem].stackSize==0 ? null:
						player.inventory.mainInventory[player.inventory.currentItem];
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void breakBlock(World w, int x, int y, int z, Block b, int meta) {
		IRedstoneSensitive tile = getTE(w, x, y, z);
		if(tile.isModified()){
			Item it = settingToItem.get(tile.getCurrentSetting());
			if(!w.isRemote) {
				WorldHelper.spawnEntItem(w, x, y, z, new ItemStack(it, 1));
			}
		}
		super.breakBlock(w, x, y, z, b, meta);
	}
	
	private IRedstoneSensitive getTE(IBlockAccess access, int x, int y, int z) {
		TileEntity tile = access.getTileEntity(x, y, z);
		return tile instanceof IRedstoneSensitive ? (IRedstoneSensitive)tile : null;
	}
}