package theflogat.technomancy.handlers.util;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHelper {
	
	
	public static ItemStack getFirstItemStack(CompareItemStack comp) {
		Iterator it = Item.itemRegistry.iterator();
		while(it.hasNext()) {
			Item item = (Item) it.next();
			if(item.getHasSubtypes()){
				ArrayList<ItemStack> l = new ArrayList<ItemStack>();
				if(item.getHasSubtypes()){
					for(int j=0;j<256;j++){
						l.add(new ItemStack(item, 1, j));
					}
				}else{
					l.add(new ItemStack(item));
				}
				for(ItemStack items : (ItemStack[])l.toArray()){
					if(comp.isCorrectItemStack(items))
						return items;
				}
			}else{
				ItemStack items = new ItemStack(item);
				if(comp.isCorrectItemStack(items))
					return items;
			}
		}
		
		Iterator bl = Block.blockRegistry.iterator();
		while(bl.hasNext()) {
			ItemStack items = new ItemStack((Block)bl.next());
			if(comp.isCorrectItemStack(items))
				return items;
		}
		
		return null;
	}
}
