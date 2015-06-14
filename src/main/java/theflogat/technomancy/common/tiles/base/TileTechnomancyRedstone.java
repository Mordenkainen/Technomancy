package theflogat.technomancy.common.tiles.base;

import net.minecraft.nbt.NBTTagCompound;

public abstract class TileTechnomancyRedstone extends TileTechnomancy implements IRedstoneSensitive {

	public RedstoneSet set = RedstoneSet.HIGH;
	protected boolean modified = false;

	public TileTechnomancyRedstone(RedstoneSet setDefault) {
		super();
		set = setDefault;
	}

	@Override
	public RedstoneSet getCurrentSetting() {
		return set;
	}

	@Override
	public void setNewSetting(RedstoneSet newSet) {
		set = newSet;
		modified = true;
	}

	@Override
	public boolean isModified() {
		return modified;
	}
	
	@Override
	public boolean canBeModified() {
		return true;
	}
	
	@Override
	public void writeSyncData(NBTTagCompound comp) {
		set.save(comp);
		comp.setBoolean("modified", modified);
	}
	
	@Override
	public void readSyncData(NBTTagCompound comp) {
		set = RedstoneSet.load(comp);
		modified = comp.getBoolean("modified");
	}
}
