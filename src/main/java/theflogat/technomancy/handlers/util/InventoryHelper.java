package theflogat.technomancy.handlers.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class InventoryHelper {

	public static ItemStack insert(IInventory tile, ItemStack items, ForgeDirection dir) {
		if(tile instanceof ISidedInventory){
			for(int i : ((ISidedInventory) tile).getAccessibleSlotsFromSide(dir.ordinal())){
				if(((ISidedInventory) tile).canInsertItem(i, items, dir.ordinal())){
					if(tile.getStackInSlot(i)==null){
						tile.setInventorySlotContents(i, items);
					}else if(tile.getStackInSlot(i).getItem()==items.getItem() &&
							tile.getStackInSlot(i).getItem().getItemStackLimit(tile.getStackInSlot(i))>tile.getStackInSlot(i).stackSize){
						int amountLeft = tile.getStackInSlot(i).getItem().getItemStackLimit(tile.getStackInSlot(i)) - tile.getStackInSlot(i).stackSize;
						int toAdd =  Math.min(amountLeft, items.stackSize);
						int returnAm = items.stackSize - toAdd;
						items.stackSize = tile.getStackInSlot(i).stackSize + toAdd;
						tile.setInventorySlotContents(i, items);
						
						if(returnAm==0){
							return null;
						}else{
							items.stackSize = returnAm;
							return items;
						}
					}
				}
			}
			return items;
		}
		
		for(int i = 0; i<tile.getSizeInventory(); i++){
			if(tile.isItemValidForSlot(i, items)){
				if(tile.getStackInSlot(i)==null){
					tile.setInventorySlotContents(i, items);
				}else if(tile.getStackInSlot(i).getItem()==items.getItem() &&
						tile.getStackInSlot(i).getItem().getItemStackLimit(tile.getStackInSlot(i))>tile.getStackInSlot(i).stackSize){
					int amountLeft = tile.getStackInSlot(i).getItem().getItemStackLimit(tile.getStackInSlot(i)) - tile.getStackInSlot(i).stackSize;
					int toAdd =  Math.min(amountLeft, items.stackSize);
					int returnAm = items.stackSize - toAdd;
					items.stackSize = tile.getStackInSlot(i).stackSize + toAdd;
					tile.setInventorySlotContents(i, items);
					
					if(returnAm==0){
						return null;
					}else{
						items.stackSize = returnAm;
						return items;
					}
				}
			}
		}
		
		return items;
	}

}
