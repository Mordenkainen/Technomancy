package theflogat.technomancy.common.tiles.base;

import net.minecraft.nbt.NBTTagCompound;

public abstract class TileTechnomancyRedstone extends TileTechnomancy implements IRedstoneSensitive {

	public RedstoneSet set = RedstoneSet.HIGH;
	private boolean modified = false;

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
	public void readCustomNBT(NBTTagCompound comp) {
		set = RedstoneSet.load(comp);
		modified = comp.getBoolean("modified");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		set.save(comp);
		comp.setBoolean("modified", modified);
	}
}