package theflogat.technomancy.common.tiles.base;

import java.util.HashMap;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public interface IRedstoneSensitive {

	public enum RedstoneSet{
		NONE("None"),HIGH("High"),LOW("Low");

		public static RedstoneSet[] sets = {NONE,HIGH,LOW};

		public String id;

		RedstoneSet(String id){
			this.id = id;
		}

		@Override
		public String toString() {
			return id;
		}

		public void save(NBTTagCompound comp) {
			comp.setString("redstoneSettings", id);
		}

		public static RedstoneSet load(NBTTagCompound comp){
			String oldId = comp.getString("redstoneSettings");

			for(RedstoneSet set : sets)
				if(set.id.equalsIgnoreCase(oldId))
					return set;

			return null;
		}

		public RedstoneSet cycle(){
			int pos = -1;
			for(int i = 0; i<sets.length; i++){
				RedstoneSet set = sets[i];
				if(id.equalsIgnoreCase(set.id))
					pos = i;
			}

			pos++;

			return sets[pos%sets.length];
		}

		public boolean canRun(TileEntity tile){
			if(tile!=null && tile.getWorldObj()!=null)
				switch(this){
				case HIGH:
					return tile.getWorldObj().isBlockIndirectlyGettingPowered(tile.xCoord, tile.yCoord, tile.zCoord);
				case LOW:
					return !tile.getWorldObj().isBlockIndirectlyGettingPowered(tile.xCoord, tile.yCoord, tile.zCoord);
				case NONE:
					return true;
				}
			return false;
		}
	}

	public RedstoneSet getCurrentSetting();
	public void setNewSetting(RedstoneSet newSet);
}
