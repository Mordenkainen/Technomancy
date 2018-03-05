package theflogat.technomancy.common.tiles.base;

import java.util.ArrayList;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import theflogat.technomancy.common.blocks.base.BlockContainerRedstone;
import theflogat.technomancy.util.helpers.WorldHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileCoilTransmitter extends TileTechnomancyRedstone implements ICouplable, IWrenchable, IUpgradable {

	public boolean redstoneState = false;
	
	public TileCoilTransmitter() {
		super(RedstoneSet.LOW);
	}

	public ArrayList<BlockPos> sources = new ArrayList<BlockPos>();
	public int facing = 0;
	public boolean boost;

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		int sourceCount = 0;
		for(int i = 0; i < sources.size(); i++) {
			if(sources.get(i) != null) {
				comp.setInteger("pos.getX()" + sourceCount, sources.get(i).getX());
				comp.setInteger("pos.getY()" + sourceCount, sources.get(i).getY());
				comp.setInteger("pos.getZ()" + sourceCount, sources.get(i).getZ());
				sourceCount++;
			}		
		}
		comp.setInteger("size", sourceCount);
	}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		int size = comp.getInteger("size");
		for(int i = 0; i < size; i ++) {
			int xx = comp.getInteger("pos.getX()" + i);
			int yy = comp.getInteger("pos.getY()" + i);
			int zz = comp.getInteger("pos.getZ()" + i);
			this.sources.add(new BlockPos(xx, yy, zz));
		}
	}
	
	@Override
	public void writeSyncData(NBTTagCompound comp) {
		super.writeSyncData(comp);
		comp.setByte("facing", (byte)facing);
		comp.setBoolean("boost", boost);
		comp.setBoolean("redstone", redstoneState);
	}
	
	@Override
	public void readSyncData(NBTTagCompound comp) {
		super.readSyncData(comp);
		facing = comp.getByte("facing");
		boost = comp.getBoolean("boost");
		redstoneState = comp.getBoolean("redstone");
	}

	@Override
	public void addPos(BlockPos coords) {
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
				if(!world.isRemote) {
					Item it = BlockContainerRedstone.settingToItem.get(set);
					WorldHelper.spawnEntItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(it, 1));
				}
				modified = false;
			}
			set = RedstoneSet.NONE;
		} else {
			set = RedstoneSet.LOW;
		}
	}

	@Override
	public String getInfos() {
		return "Emits A Redstone Signal When Not Full";
	}
}
