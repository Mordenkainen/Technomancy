package theflogat.technomancy.common.tiles.technom;

import java.util.Iterator;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import theflogat.technomancy.common.tiles.base.ICouplable;
import theflogat.technomancy.common.tiles.base.IUpgradable;
import theflogat.technomancy.common.tiles.base.IWrenchable;
import theflogat.technomancy.common.tiles.base.TileCoilTransmitter;
import theflogat.technomancy.util.helpers.InvHelper;
import theflogat.technomancy.util.helpers.WorldHelper;

public class TileItemTransmitter extends TileCoilTransmitter implements IUpgradable, ICouplable, IWrenchable{

	public ItemStack filter = null;

	@Override
	public void updateEntity() {
		if(set==null)
			set = RedstoneSet.LOW;
		TileEntity te = WorldHelper.getAdjacentTileEntity(this, (byte) facing);
		if(te==null || !(te instanceof IInventory)){
			return;
		}
		
		if(!worldObj.isRemote) {
			boolean flag = false;
			if(!boost){ 
				if(redstoneState) {
					redstoneState = false;
					flag = true;
				}
			} else {
				if(!InvHelper.isFull((IInventory)te) && !redstoneState) {
					redstoneState = true;
					flag = true;
				} else if(InvHelper.isFull((IInventory)te) && redstoneState) {
					redstoneState = false;
					flag = true;
				}
			}
			if(flag) {
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, blockType);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
		
		if (set.canRun(this) && !sources.isEmpty()) {
			IInventory inv = (IInventory) te;
			if(!InvHelper.isFull(inv)) {
				Iterator<ChunkCoordinates> sourceIter = sources.iterator();
				while (sourceIter.hasNext()) {
					ChunkCoordinates coords = sourceIter.next();
					TileEntity tile = worldObj.getTileEntity(coords.posX, coords.posY, coords.posZ);
					if(tile != null && tile instanceof IInventory) {
						IInventory rem = (IInventory)tile;
						for(int i=0; i < rem.getSizeInventory() && !InvHelper.isFull(inv); i++){
							ItemStack toMove = rem.getStackInSlot(i);
							if(toMove != null && (filter == null || areItemStacksEqual(toMove, filter))){
								if(InvHelper.insertInEmptySlot(inv, toMove, ForgeDirection.getOrientation(facing).getOpposite()))
									rem.setInventorySlotContents(i, null);
							}
						}
					}else{
						sourceIter.remove();
					}
				}
			}
		}
	}
	
    private static boolean areItemStacksEqual(ItemStack toMove, ItemStack filter){
    	return toMove.isItemEqual(filter) && ItemStack.areItemStackTagsEqual(toMove, filter);
    }

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		if(comp.hasKey("filter")){
			NBTTagCompound item = comp.getCompoundTag("filter");
			filter = ItemStack.loadItemStackFromNBT(item);
			filter.readFromNBT(item);
		} else {
			filter = null;
		}
		super.readCustomNBT(comp);
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		if(filter!=null){
			NBTTagCompound item = new NBTTagCompound();
			filter.writeToNBT(item);
			comp.setTag("filter", item);
		}
		super.writeCustomNBT(comp);
	}

	@Override
	public Type getType() {
		return Type.ITEM;
	}

	@Override
	public void addPos(ChunkCoordinates coords) {
		sources.add(coords);
	}

	@Override
	public void clear() {
		sources.clear();
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