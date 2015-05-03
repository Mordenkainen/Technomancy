package theflogat.technomancy.common.tiles.technom;

import java.util.ArrayList;
import java.util.Iterator;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import theflogat.technomancy.common.tiles.base.ICouplable;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.base.IWrenchable;
import theflogat.technomancy.common.tiles.base.IUpgradable;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;
import theflogat.technomancy.util.InvHelper;
import theflogat.technomancy.util.WorldHelper;

public class TileItemTransmitter extends TileTechnomancy implements IUpgradable, ICouplable, IRedstoneSensitive, IWrenchable{

	public ItemStack filter = null;
	public ArrayList<ChunkCoordinates> sources = new ArrayList<ChunkCoordinates>();
	public byte facing = 0;
	public RedstoneSet set = RedstoneSet.LOW;
	public boolean boost = false;

	@Override
	public void updateEntity() {
		if(set==null)
			set = RedstoneSet.LOW;
		TileEntity te = WorldHelper.getAdjacentTileEntity(this, facing);
		if (set.canRun(this) && te != null && te instanceof IInventory && !sources.isEmpty()) {
			IInventory inv = (IInventory) te;
			if(!InvHelper.isFull(inv)){
				Iterator<ChunkCoordinates> sourceIter = sources.iterator();
				while (sourceIter.hasNext()) {
					ChunkCoordinates coords = sourceIter.next();
					TileEntity tile = worldObj.getTileEntity(coords.posX, coords.posY, coords.posZ);
					if(tile!=null && tile instanceof IInventory){
						IInventory rem = (IInventory)tile;
						for(int i=0;i<rem.getSizeInventory() && !InvHelper.isFull(inv);i++){
							ItemStack toMove = rem.getStackInSlot(i);
							if(toMove!=null && (filter==null || areItemStacksEqual(toMove, filter))){
								if(InvHelper.insertInEmptySlot(inv, toMove, ForgeDirection.VALID_DIRECTIONS[facing].getOpposite()))
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
	
    private boolean areItemStacksEqual(ItemStack toMove, ItemStack filter){
        return toMove.getItem() != filter.getItem() ? false : (toMove.getItemDamage() != filter.getItemDamage()
        		? false : (toMove.stackTagCompound == null && filter.stackTagCompound != null ? false : toMove.stackTagCompound == null ||
        		toMove.stackTagCompound.equals(filter.stackTagCompound)));
    }

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		if(comp.hasKey("filter")){
			NBTTagCompound item = comp.getCompoundTag("filter");
			filter = ItemStack.loadItemStackFromNBT(item);
			filter.readFromNBT(item);
		}
		facing = comp.getByte("facing");
		int size = comp.getInteger("size");
		for(int i = 0; i < size; i ++) {
			int xx = comp.getInteger("xcoord" + i);		
			int yy = comp.getInteger("ycoord" + i);		
			int zz = comp.getInteger("zcoord" + i);
			this.sources.add(new ChunkCoordinates(xx, yy, zz));
		}
		boost = comp.getBoolean("boost");
		set = RedstoneSet.load(comp);
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		if(filter!=null){
			NBTTagCompound item = new NBTTagCompound();
			filter.writeToNBT(item);
			comp.setTag("filter", item);
		}
		comp.setByte("facing", (byte)facing);
		int sourceCount = 0;
		for(int i = 0; i < sources.size(); i++) {
			if(sources.get(i) != null) {
				comp.setInteger("xcoord" + sourceCount, sources.get(i).posX);
				comp.setInteger("ycoord" + sourceCount, sources.get(i).posY);
				comp.setInteger("zcoord" + sourceCount, sources.get(i).posZ);
				sourceCount++;
			}		
		}
		comp.setInteger("size", sourceCount);
		comp.setBoolean("boost", boost);
		set.save(comp);
	}

	@Override
	public boolean toggleBoost() {
		boost = !boost;
		return boost;
	}

	@Override
	public boolean getBoost() {
		return boost;
	}

	@Override
	public void setBoost(boolean newBoost) {
		boost = newBoost;
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
	public RedstoneSet getCurrentSetting() {
		return set;
	}

	@Override
	public void setNewSetting(RedstoneSet newSet) {
		set = newSet;
	}
	
	@Override
	public boolean onWrenched() {
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
		filter = newFilter;
	}
}