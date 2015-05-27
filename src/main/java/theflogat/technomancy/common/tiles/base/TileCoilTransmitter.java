package theflogat.technomancy.common.tiles.base;

import java.util.ArrayList;

import theflogat.technomancy.common.blocks.base.BlockContainerRedstone;
import theflogat.technomancy.util.helpers.WorldHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;

public abstract class TileCoilTransmitter extends TileTechnomancyRedstone implements ICouplable, IWrenchable, IUpgradable{

	public boolean redstoneState = false;
	
	public TileCoilTransmitter() {
		super(RedstoneSet.LOW);
	}

	public ArrayList<ChunkCoordinates> sources = new ArrayList<ChunkCoordinates>();
	public int facing = 0;
	public boolean boost;

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		super.writeCustomNBT(comp);
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
		comp.setBoolean("redstone", redstoneState);
	}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		super.readCustomNBT(comp);
		facing = comp.getByte("facing");
		int size = comp.getInteger("size");
		for(int i = 0; i < size; i ++) {
			int xx = comp.getInteger("xcoord" + i);		
			int yy = comp.getInteger("ycoord" + i);		
			int zz = comp.getInteger("zcoord" + i);
			this.sources.add(new ChunkCoordinates(xx, yy, zz));
		}
		boost = comp.getBoolean("boost");
		redstoneState = comp.getBoolean("redstone");
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
	public boolean toggleBoost() {
		boost = !boost;
		fixRedstone();
		return boost;
	}

	@Override
	public boolean getBoost() {
		return boost;
	}

	@Override
	public void setBoost(boolean newBoost) {
		boost = newBoost;
		fixRedstone();
	}
	
	@Override
	public boolean canBeModified() {
		return !boost;
	}

	private void fixRedstone() {
		if(boost) {
			if(modified) {
				Item it = BlockContainerRedstone.settingToItem.get(set);
				if(!worldObj.isRemote) {
					WorldHelper.spawnEntItem(worldObj, xCoord, yCoord, zCoord, new ItemStack(it, 1));
				}
				modified = false;
			}
			set = RedstoneSet.NONE;
		} else {
			set = RedstoneSet.LOW;
		}
		
	}

	@Override
	public String getInfo() {
		return "Emits A Redstone Signal When Not Full";
	}
}