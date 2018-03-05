package theflogat.technomancy.common.blocks.base;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive.RedstoneSet;
import theflogat.technomancy.util.helpers.WorldHelper;

public abstract class BlockContainerRedstone extends BlockContainerBase {

	public static HashMap<Item, RedstoneSet> itemToSetting = new HashMap<Item, RedstoneSet>();
	public static HashMap<RedstoneSet, Item> settingToItem = new HashMap<RedstoneSet, Item>();

	static {
		itemToSetting.put(Items.GUNPOWDER, RedstoneSet.NONE);
		itemToSetting.put(Items.REDSTONE, RedstoneSet.HIGH);
		itemToSetting.put(Item.getItemFromBlock(Blocks.REDSTONE_TORCH), RedstoneSet.LOW);
		settingToItem.put(RedstoneSet.NONE, Items.GUNPOWDER);
		settingToItem.put(RedstoneSet.HIGH, Items.REDSTONE);
		settingToItem.put(RedstoneSet.LOW, Item.getItemFromBlock(Blocks.REDSTONE_TORCH));
	}

	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IRedstoneSensitive te = getTE(w, pos);
		if(te != null) {
			ItemStack items = player.inventory.mainInventory.get(player.inventory.currentItem);
			if(items!=null && itemToSetting.containsKey(items.getItem()) && te.canBeModified()) {
				if(itemToSetting.get(items.getItem())!=te.getCurrentSetting()){
					if(!w.isRemote) {
						if(te.isModified()) {
							Item it = settingToItem.get(te.getCurrentSetting());
							WorldHelper.spawnEntItem(w, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(it, 1));
						}
						te.setNewSetting(itemToSetting.get(items.getItem()));
						w.notifyBlockUpdate(pos, state, state, 3);
					}
					player.inventory.mainInventory.set(player.inventory.currentItem, player.inventory.mainInventory.get(player.inventory.currentItem).getCount()-1 == 0 ? null :
							player.inventory.mainInventory.get(player.inventory.currentItem));
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void breakBlock(World w, BlockPos pos, IBlockState state) {
		IRedstoneSensitive tile = getTE(w, pos);
		if(tile != null && tile.isModified()){
			Item it = settingToItem.get(tile.getCurrentSetting());
			if(!w.isRemote) {
				WorldHelper.spawnEntItem(w, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(it, 1));
			}
		}
		super.breakBlock(w, pos, state);
	}
	
	private static IRedstoneSensitive getTE(IBlockAccess access, BlockPos pos) {
		TileEntity tile = access.getTileEntity(pos);
		return tile instanceof IRedstoneSensitive ? (IRedstoneSensitive)tile : null;
	}
}