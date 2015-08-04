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
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import theflogat.technomancy.common.tiles.base.TileCoilTransmitter;
import theflogat.technomancy.util.helpers.InvHelper;
import theflogat.technomancy.util.helpers.WorldHelper;

public class TileItemTransmitter extends TileCoilTransmitter {

	public ItemStack filter = null;

	@SuppressWarnings({ "null", "cast" })
	@Override
	public void updateEntity() {		
		if(!worldObj.isRemote) {
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
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, blockType);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		
			if (set.canRun(this) && !sources.isEmpty()) {
				IInventory inv = (IInventory) te;
				if(!InvHelper.isFull(inv)) {
					Collections.shuffle(sources);
					Iterator<ChunkCoordinates> sourceIter = sources.iterator();
					while (sourceIter.hasNext()) {
						ChunkCoordinates coords = sourceIter.next();
						TileEntity tile = worldObj.getTileEntity(coords.posX, coords.posY, coords.posZ);
						if(tile != null && !tile.isInvalid() && tile instanceof IInventory) {
							IInventory rem = null;
							Block chestBlock = worldObj.getBlock(coords.posX, coords.posY, coords.posZ);
							if(chestBlock instanceof BlockChest) {
								rem = (IInventory)tile;
								if (worldObj.getBlock(coords.posX - 1, coords.posY, coords.posZ) == chestBlock) {
									rem = new InventoryLargeChest("container.chestDouble", (TileEntityChest)worldObj.getTileEntity(coords.posX - 1, coords.posY, coords.posZ), (IInventory)rem);
								}

								if (worldObj.getBlock(coords.posX + 1, coords.posY, coords.posZ) == chestBlock) {
									rem = new InventoryLargeChest("container.chestDouble", (IInventory)rem, (TileEntityChest)worldObj.getTileEntity(coords.posX + 1, coords.posY, coords.posZ));
								}

								if (worldObj.getBlock(coords.posX, coords.posY, coords.posZ - 1) == chestBlock) {
									rem = new InventoryLargeChest("container.chestDouble", (TileEntityChest)worldObj.getTileEntity(coords.posX, coords.posY, coords.posZ - 1), (IInventory)rem);
								}

								if (worldObj.getBlock(coords.posX, coords.posY, coords.posZ + 1) == chestBlock) {
									rem = new InventoryLargeChest("container.chestDouble", (IInventory)rem, (TileEntityChest)worldObj.getTileEntity(coords.posX, coords.posY, coords.posZ + 1));
								}
							} else {
								rem = (IInventory)tile;
							}
							for(int i = 0; i < rem.getSizeInventory() && !InvHelper.isFull(inv); i++) {
								ItemStack toMove = rem.getStackInSlot(i);
								if(toMove != null && (filter == null || areItemStacksEqual(toMove, filter))){
									if(InvHelper.insertInEmptySlot(inv, toMove, ForgeDirection.getOrientation(facing).getOpposite())) {
										rem.setInventorySlotContents(i, null);
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
		if(filter!=null){
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
			filter = ItemStack.loadItemStackFromNBT(item);
		} else {
			filter = null;
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
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord));
				return true;
			}
		}
		return false;
	}

	public void addFilter(ItemStack newFilter) {
		filter = newFilter.copy();
		filter.stackSize = 1;
	}
}