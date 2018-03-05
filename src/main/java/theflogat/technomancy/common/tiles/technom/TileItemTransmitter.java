package theflogat.technomancy.common.tiles.technom;

import java.util.Collections;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import theflogat.technomancy.common.tiles.base.TileCoilTransmitter;
import theflogat.technomancy.util.helpers.InvHelper;
import theflogat.technomancy.util.helpers.WorldHelper;

public class TileItemTransmitter extends TileCoilTransmitter {

	public ItemStack filter = ItemStack.EMPTY;

	@Override
	public void update() {
		if(!world.isRemote) {
			if(set==null) {
				set = RedstoneSet.LOW;
			}
			TileEntity te = WorldHelper.getAdjacentTileEntity(this, (byte) facing);
			if(te==null || !(te instanceof IInventory)){
				return;
			}
			
			boolean flag = false;
			if(!boost){ 
				if(redstoneState) {
					redstoneState = false;
					flag = true;
				}
			} else {
				if(!InvHelper.isFull((IInventory)te)) {
					if(!redstoneState) {
						redstoneState = true;
						flag = true;
					}
				} else if(InvHelper.isFull((IInventory)te)) {
					if(redstoneState) {
						redstoneState = false;
						flag = true;
					}
				}
			}
			if(flag) {
				world.notifyNeighborsOfStateChange(pos, blockType, true);
				world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
			}
		
			if (set.canRun(this) && !sources.isEmpty()) {
				IInventory inv = (IInventory) te;
				if(!InvHelper.isFull(inv)) {
					Collections.shuffle(sources);
					Iterator<BlockPos> sourceIter = sources.iterator();
					while (sourceIter.hasNext()) {
						BlockPos coords = sourceIter.next();
						TileEntity tile = world.getTileEntity(coords);
						if(tile != null && !tile.isInvalid() && tile instanceof IInventory) {
							IInventory rem = null;
							Block chestBlock = world.getBlockState(coords).getBlock();
							if(chestBlock instanceof BlockChest) {
								rem = (IInventory)tile;
								if (world.getBlockState(new BlockPos(coords.getX() - 1, coords.getY(), coords.getZ())) == chestBlock) {
									rem = new InventoryLargeChest("container.chestDouble", (TileEntityChest)world.getTileEntity(new BlockPos(coords.getX() - 1, coords.getY(), coords.getZ())), (ILockableContainer) rem);
								}

								if (world.getBlockState(new BlockPos(coords.getX() + 1, coords.getY(), coords.getZ())) == chestBlock) {
									rem = new InventoryLargeChest("container.chestDouble", (ILockableContainer) rem, (TileEntityChest)world.getTileEntity(new BlockPos(coords.getX() + 1, coords.getY(), coords.getZ())));
								}

								if (world.getBlockState(new BlockPos(coords.getX(), coords.getY(), coords.getZ() - 1)) == chestBlock) {
									rem = new InventoryLargeChest("container.chestDouble", (TileEntityChest)world.getTileEntity(new BlockPos(coords.getX(), coords.getY(), coords.getZ()- 1)), (ILockableContainer) rem);
								}

								if (world.getBlockState(new BlockPos(coords.getX(), coords.getY(), coords.getZ() + 1)) == chestBlock) {
									rem = new InventoryLargeChest("container.chestDouble", (ILockableContainer) rem, (TileEntityChest)world.getTileEntity(new BlockPos(coords.getX(), coords.getY(), coords.getZ() + 1)));
								}
							} else {
								rem = (IInventory)tile;
							}
							for(int i = 0; i < rem.getSizeInventory() && !InvHelper.isFull(inv); i++) {
								ItemStack toMove = rem.getStackInSlot(i);
								if(!toMove.isEmpty() && (filter.isEmpty() || areItemStacksEqual(toMove, filter))){
									if(InvHelper.insertInEmptySlot(inv, toMove, EnumFacing.getFront(facing).getOpposite())) {
										rem.setInventorySlotContents(i, ItemStack.EMPTY);
										break;
									}
								}
							}
						}else{
							if(tile == null || (tile != null && !tile.isInvalid())) {
								sourceIter.remove();
							}
						}
					}
				}
			}
		}
	}
	
    private static boolean areItemStacksEqual(ItemStack toMove, ItemStack filter){
    	return toMove.isItemEqual(filter) && ItemStack.areItemStackTagsEqual(toMove, filter);
    }

	@Override
	public void writeSyncData(NBTTagCompound comp) {
		super.writeSyncData(comp);
		if(!filter.isEmpty()){
			NBTTagCompound item = new NBTTagCompound();
			filter.writeToNBT(item);
			comp.setTag("filter", item);
		}
	}
	
	@Override
	public void readSyncData(NBTTagCompound comp) {
		super.readSyncData(comp);
		if(comp.hasKey("filter")){
			NBTTagCompound item = comp.getCompoundTag("filter");
			filter = new ItemStack(item);
		} else {
			filter = ItemStack.EMPTY;
		}
	}

	@Override
	public Type getType() {
		return Type.ITEM;
	}

	@Override
	public boolean onWrenched(boolean sneaking) {
		for (int i = facing + 1; i < facing + 6; i++){
			TileEntity tile = WorldHelper.getAdjacentTileEntity(this, (byte) (i % 6));
			if (tile instanceof IInventory) {
				facing = (byte) (i % 6);
				world.notifyNeighborsOfStateChange(pos, world.getBlockState(pos).getBlock(), true);
				return true;
			}
		}
		return false;
	}

	public void addFilter(ItemStack newFilter) {
		filter = newFilter.copy();
		filter.setCount(1);
	}
}