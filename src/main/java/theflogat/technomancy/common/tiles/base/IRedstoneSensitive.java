package theflogat.technomancy.common.tiles.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

			return HIGH;
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
			if(tile!=null && tile.getWorld()!=null)
				switch(this){
				case HIGH:
					return tile.getWorld().isBlockPowered(tile.getPos());
				case LOW:
					return !tile.getWorld().isBlockPowered(tile.getPos());
				case NONE:
					return true;
				}
			return false;
		}
		
		public boolean canRun(World world, int x, int y, int z){
			switch(this){
			case HIGH:
				return world.isBlockPowered(new BlockPos(x, y, z));
			case LOW:
				return !world.isBlockPowered(new BlockPos(x, y, z));
			case NONE:
				return true;
			}
			return false;
		}
	}

	public RedstoneSet getCurrentSetting();
	public void setNewSetting(RedstoneSet newSet);
	public boolean isModified();
	public boolean canBeModified();
}
