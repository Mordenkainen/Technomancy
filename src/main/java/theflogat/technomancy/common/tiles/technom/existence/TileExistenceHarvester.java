package theflogat.technomancy.common.tiles.technom.existence;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import theflogat.technomancy.common.tiles.base.TileExistenceRedstoneBase;
import theflogat.technomancy.util.helpers.InvHelper;

public class TileExistenceHarvester extends TileExistenceRedstoneBase implements IInventory{

	public TileExistenceHarvester() {
		super(RedstoneSet.LOW, 10000);
	}

	ItemStack[] items;
	public boolean flag = false;

	@Override
	public void update() {
		if(items!=null && flag){
			checkInv();
			flag = false;
		}

		if(!world.isRemote && set.canRun(this) && items==null && power>=30){
			for(int xx=-5; xx<=5; xx++){
				for(int zz=-5; zz<=5; zz++){
					Block b = world.getBlockState(new BlockPos(pos.getX() + xx, pos.getY(), pos.getZ() + zz)).getBlock();
					if(b instanceof IGrowable){
						IGrowable grow = (IGrowable)b;
						if(!(grow.canGrow(world, new BlockPos(pos.getX() + xx, pos.getY(), pos.getZ() + zz), world.getBlockState(new BlockPos(pos.getX() + xx, pos.getY(), pos.getZ() + zz)), world.isRemote))){
							List<ItemStack> drops = b.getDrops(world, new BlockPos(pos.getX() + xx, pos.getY(), pos.getZ() + zz), world.getBlockState(new BlockPos(pos.getX() + xx, pos.getY(), pos.getZ() + zz)), 1);
							items = new ItemStack[drops.size()];
							world.notifyBlockUpdate(new BlockPos(pos.getX() + xx, pos.getY(), pos.getZ() + zz), world.getBlockState(new BlockPos(pos.getX() + xx, pos.getY(), pos.getZ() + zz)), world.getBlockState(new BlockPos(pos.getX() + xx, pos.getY(), pos.getZ() + zz)), 3);
							for(ItemStack drop: drops){
								if(drop.getItem()==Item.getItemFromBlock(b)){
									items[drops.indexOf(drop)] = drop;
									InvHelper.decrItemStack(items[drops.indexOf(drop)]);
								}else{
									items[drops.indexOf(drop)] = drop;
								}
							}
							power-=30;
						}
					}
					if(power<30){
						return;
					}
				}
			}
		}
	}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		super.readCustomNBT(comp);
		flag = true;
		if(comp.hasKey("items")){
			NBTTagList list = comp.getTagList("items",10);

			for(int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound item = list.getCompoundTagAt(i);
				int slot = item.getByte("slots");

				if(slot >= 0 && slot < getSizeInventory()) {
					setInventorySlotContents(slot, new ItemStack(item));
				}
			}
		}
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		super.writeCustomNBT(comp);
		if(items!=null){
			NBTTagList list = new NBTTagList();

			for(int i = 0; i < this.getSizeInventory(); i++) {
				ItemStack itemstack = this.getStackInSlot(i);

				if(!itemstack.isEmpty()) {
					NBTTagCompound item = new NBTTagCompound();

					item.setByte("slots", (byte) i);
					itemstack.writeToNBT(item);
					list.appendTag(item);
				}
			}

			comp.setTag("items", list);
		}
	}

	public void checkInv(){
		for(ItemStack item : items){
			if(item!=null){
				return;
			}
		}
		items = null;
	}

	@Override
	public int getSizeInventory() {
		return items!=null ? items.length : 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return items!=null ? items[i] : ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		flag = true;
		ItemStack itemstack = getStackInSlot(slot);

		if(!itemstack.isEmpty()) {
			if(itemstack.getCount() <= count) {
				setInventorySlotContents(slot, ItemStack.EMPTY);
			} else {
				itemstack = itemstack.splitStack(count);
			}
		}
		return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack newItems) {
		flag = true;
		if(items!=null){
			items[i] = newItems;
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack newItems) {
		if(items!=null){
			return items[i].isEmpty() ? true : (items[i].getItem() == newItems.getItem() && items[i].getCount()<64);
		}
		return false;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {

	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}
}
