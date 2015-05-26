package theflogat.technomancy.common.blocks.air;

import java.util.HashMap;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive.RedstoneSet;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.util.ToolWrench;
import theflogat.technomancy.util.helpers.WorldHelper;

public class BlockFakeAirNG extends BlockContainer{

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
	
	public BlockFakeAirNG() {
		super(Material.carpet);
		setBlockUnbreakable();
		setBlockName(Ref.getId(Names.fakeAirNG));
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(Ref.getAsset(Names.fakeAirNG));
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileFakeAirNG();
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		TileFakeAirNG fakeAir = (TileFakeAirNG)w.getTileEntity(x, y, z);
		ItemStack items = player.inventory.mainInventory[player.inventory.currentItem];
		if (ToolWrench.isWrench(items)){
			fakeAir.onWrenched(player.isSneaking());
			return true;
		}
		if (player.getHeldItem() == null && player.isSneaking()) {
			if(fakeAir.getBoost()) {
				if(!w.isRemote) {
					WorldHelper.dropBoost(w, x, y, z);
				}
				fakeAir.setBoost(false);
				return true;
			}
		}
		if(items!=null && itemToSetting.containsKey(items.getItem())) {
			if(itemToSetting.get(items.getItem())!=fakeAir.getCurrentSetting()){
				if(fakeAir.isModified()) {
					Item it = settingToItem.get(fakeAir.getCurrentSetting());
					if(!w.isRemote) {
						WorldHelper.spawnEntItem(w, x, y, z, new ItemStack(it, 1));
					}
				}
				fakeAir.setNewSetting(itemToSetting.get(items.getItem()));
				player.inventory.mainInventory[player.inventory.currentItem] = --player.inventory.mainInventory[player.inventory.currentItem].stackSize==0 ? null:
					player.inventory.mainInventory[player.inventory.currentItem];
				return true;
			}
		}
		return false;
	}
}
