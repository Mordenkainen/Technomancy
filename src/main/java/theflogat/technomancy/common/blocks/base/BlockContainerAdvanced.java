package theflogat.technomancy.common.blocks.base;

import java.util.HashMap;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.items.technom.ItemCoilCoupler;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.base.IWrenchable;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive.RedstoneSet;
import theflogat.technomancy.common.tiles.technom.TileItemTransmitter;
import theflogat.technomancy.util.InvHelper;
import theflogat.technomancy.util.ToolWrench;
import theflogat.technomancy.util.WorldHelper;

public abstract class BlockContainerAdvanced extends BlockContainerBase{
	
	public static HashMap<Item, RedstoneSet> map = new HashMap<Item, RedstoneSet>();
	
	public BlockContainerAdvanced() {
		map.put(Items.gunpowder, RedstoneSet.NONE);
		map.put(Items.redstone, RedstoneSet.HIGH);
		map.put(Item.getItemFromBlock(Blocks.redstone_torch), RedstoneSet.LOW);
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity te = w.getTileEntity(x, y, z);
		if(te instanceof IWrenchable){
			if (ToolWrench.isWrench(player.getHeldItem()))
				((IWrenchable)te).onWrenched();
			return true;
		}
		if(te instanceof IRedstoneSensitive){
			if(player.getHeldItem()!=null && map.containsKey(player.getHeldItem().getItem()))
				if(map.get(player.getHeldItem().getItem())!=((IRedstoneSensitive)te).getCurrentSetting()){
					((IRedstoneSensitive)te).setNewSetting(map.get(player.getHeldItem().getItem()));
					InvHelper.decrItemStack(player.inventory.mainInventory[player.inventory.currentItem]);
					return true;
				}
		}
		return false;
	}
}
