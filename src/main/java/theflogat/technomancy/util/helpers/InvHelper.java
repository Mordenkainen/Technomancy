package theflogat.technomancy.util.helpers;

import java.util.ArrayList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InvHelper {

	public static ItemStack insert(IInventory tile, ItemStack items, EnumFacing dir) {
		int[] slots;
		if(tile instanceof ISidedInventory){
			slots = ((ISidedInventory)tile).getSlotsForFace(dir);
		} else {
			slots = new int[tile.getSizeInventory()];
			for(int i = 0 ; i < slots.length; i++) {
				slots[i] = i;
			}
		}
		for(int i : slots){
			if(tile instanceof ISidedInventory ? ((ISidedInventory)tile).canInsertItem(i, items, dir) : tile.isItemValidForSlot(i, items)) {
				if(tile.getStackInSlot(i).isEmpty()){
					tile.setInventorySlotContents(i, items);
				}else if(tile.getStackInSlot(i).getItem()==items.getItem()){
					int maxSize = Math.min(tile.getInventoryStackLimit(), tile.getStackInSlot(i).getItem().getItemStackLimit(tile.getStackInSlot(i)));	
					if(maxSize>tile.getStackInSlot(i).getCount()){
						int toAdd =  Math.min(maxSize - tile.getStackInSlot(i).getCount(), items.getCount());
						int returnAm = items.getCount() - toAdd;
						items.setCount(tile.getStackInSlot(i).getCount()+ toAdd);
						tile.setInventorySlotContents(i, items);
						
						if(returnAm==0){
							return ItemStack.EMPTY;
						}else{
							items.setCount(returnAm);
							return items;
						}
					}
				}
			}
		}
		return items;
	}
	
	public static boolean insertInEmptySlot(IInventory tile, ItemStack items, EnumFacing dir){
		if(tile instanceof ISidedInventory){
			ISidedInventory inv = (ISidedInventory) tile;
			for(int i : inv.getSlotsForFace(dir)){
				if(inv.canInsertItem(i, items, dir)){
					if(tile.getStackInSlot(i).isEmpty()){
						tile.setInventorySlotContents(i, items);
						return true;
					}
				}
			}
		}else{
			for(int i = 0; i<tile.getSizeInventory(); i++){
				if(tile.isItemValidForSlot(i, items)){
					if(tile.getStackInSlot(i).isEmpty()){
						tile.setInventorySlotContents(i, items);
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void dropItemsFromTile(World w, int x, int y, int z) {
		TileEntity tileEntity = w.getTileEntity(new BlockPos(x, y, z));
		if (!(tileEntity instanceof IInventory)) {
			return;
		}
		IInventory inventory = (IInventory)tileEntity;

		for (int i = 0; i < inventory.getSizeInventory(); i++){
			ItemStack items = inventory.getStackInSlot(i);

			if ((!items.isEmpty()) && (items.getCount() > 0)) {
				float rx = w.rand.nextFloat() * 0.8F + 0.1F;
				float ry = w.rand.nextFloat() * 0.8F + 0.1F;
				float rz = w.rand.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(w, x + rx, y + ry, z + rz, new ItemStack(items.getItem(), items.getCount(), items.getItemDamage()));

				if (items.hasTagCompound()) {
					entityItem.getItem().setTagCompound((NBTTagCompound)items.getTagCompound().copy());
				}

				float factor = 0.05F;
				entityItem.motionX = (w.rand.nextGaussian() * factor);
				entityItem.motionY = (w.rand.nextGaussian() * factor + 0.2000000029802322D);
				entityItem.motionZ = (w.rand.nextGaussian() * factor);
				w.spawnEntity(entityItem);
				inventory.setInventorySlotContents(i, ItemStack.EMPTY);
			}
		}
	}

	public static void decrItemStack(ItemStack items){
		if(items.getCount()==1){
			items = ItemStack.EMPTY;
		}else{
			items.shrink(1);
		}
	}

	public static boolean isEmpty(IInventory te) {
		for(int i=0;i<te.getSizeInventory();i++){
			if(!te.getStackInSlot(i).isEmpty())
				return false;
		}
		return true;
	}

	public static boolean isFull(IInventory te) {
		for(int i=0;i<te.getSizeInventory();i++){
			if(te.getStackInSlot(i).isEmpty())
				return false;
		}
		return true;
	}

	public static int getFirstFilledSlot(IInventory te) {
		for(int i=0;i<te.getSizeInventory();i++){
			if(!te.getStackInSlot(i).isEmpty())
				return i;
		}
		return -1;
	}

	public static ArrayList<ItemStack> getAllStacks(IInventory te) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		for(int i=0;i<te.getSizeInventory();i++){
			items.add(te.getStackInSlot(i));
		}
		return items;
	}
}
