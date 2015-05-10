package theflogat.technomancy.common.tiles.base;

import java.util.ArrayList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;

public abstract class TileCoilTransmitter extends TileTechnomancy implements ICouplable, IRedstoneSensitive, IWrenchable, IUpgradable{
	
	public ArrayList<ChunkCoordinates> sources = new ArrayList<ChunkCoordinates>();
	public int facing = 0;
	public RedstoneSet set = RedstoneSet.LOW;
	public boolean boost;

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
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
	}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		facing = comp.getByte("facing");
		int size = comp.getInteger("size");
		for(int i = 0; i < size; i ++) {
			int xx = comp.getInteger("xcoord" + i);		
			int yy = comp.getInteger("ycoord" + i);		
			int zz = comp.getInteger("zcoord" + i);
			this.sources.add(new ChunkCoordinates(xx, yy, zz));
		}
		boost = comp.getBoolean("boost");
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
	public String getInfo() {
		return "Emits A Redstone Signal When Not Full";
	}
}