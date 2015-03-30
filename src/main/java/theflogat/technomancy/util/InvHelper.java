package theflogat.technomancy.util;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class InvHelper {

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
	
	public static void dropItemsFromTile(World w, int x, int y, int z) {
		TileEntity tileEntity = w.getTileEntity(x, y, z);
		if (!(tileEntity instanceof IInventory)) {
			return;
		}
		IInventory inventory = (IInventory)tileEntity;

		for (int i = 0; i < inventory.getSizeInventory(); i++){
			ItemStack items = inventory.getStackInSlot(i);

			if ((items != null) && (items.stackSize > 0)) {
				float rx = w.rand.nextFloat() * 0.8F + 0.1F;
				float ry = w.rand.nextFloat() * 0.8F + 0.1F;
				float rz = w.rand.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(w, x + rx, y + ry, z + rz, new ItemStack(items.getItem(), items.stackSize, items.getItemDamage()));

				if (items.hasTagCompound()) {
					entityItem.getEntityItem().setTagCompound((NBTTagCompound)items.getTagCompound().copy());
				}

				float factor = 0.05F;
				entityItem.motionX = (w.rand.nextGaussian() * factor);
				entityItem.motionY = (w.rand.nextGaussian() * factor + 0.2000000029802322D);
				entityItem.motionZ = (w.rand.nextGaussian() * factor);
				w.spawnEntityInWorld(entityItem);
				inventory.setInventorySlotContents(i, null);
			}
		}
	}
	
	public static void spawnEntItem(World w, int x, int y, int z, ItemStack items){
		if ((items != null) && (items.stackSize > 0)) {
			float rx = w.rand.nextFloat() * 0.8F + 0.1F;
			float ry = w.rand.nextFloat() * 0.8F + 0.1F;
			float rz = w.rand.nextFloat() * 0.8F + 0.1F;

			EntityItem entityItem = new EntityItem(w, x + rx, y + ry, z + rz, new ItemStack(items.getItem(), items.stackSize, items.getItemDamage()));

			if (items.hasTagCompound()) {
				entityItem.getEntityItem().setTagCompound((NBTTagCompound)items.getTagCompound().copy());
			}

			float factor = 0.05F;
			entityItem.motionX = (w.rand.nextGaussian() * factor);
			entityItem.motionY = (w.rand.nextGaussian() * factor + 0.2000000029802322D);
			entityItem.motionZ = (w.rand.nextGaussian() * factor);
			w.spawnEntityInWorld(entityItem);
		}
	}

	public static void spawnEntItem(World w, double x, double y, double z, ItemStack items) {
		if ((items != null) && (items.stackSize > 0)) {
			float rx = w.rand.nextFloat() * 0.8F + 0.1F;
			float ry = w.rand.nextFloat() * 0.8F + 0.1F;
			float rz = w.rand.nextFloat() * 0.8F + 0.1F;

			EntityItem entityItem = new EntityItem(w, x + rx, y + ry, z + rz, new ItemStack(items.getItem(), items.stackSize, items.getItemDamage()));

			if (items.hasTagCompound()) {
				entityItem.getEntityItem().setTagCompound((NBTTagCompound)items.getTagCompound().copy());
			}

			float factor = 0.05F;
			entityItem.motionX = (w.rand.nextGaussian() * factor);
			entityItem.motionY = (w.rand.nextGaussian() * factor + 0.2000000029802322D);
			entityItem.motionZ = (w.rand.nextGaussian() * factor);
			w.spawnEntityInWorld(entityItem);
		}
	}
}
