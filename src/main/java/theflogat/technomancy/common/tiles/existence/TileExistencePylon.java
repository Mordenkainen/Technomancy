package theflogat.technomancy.common.tiles.existence;

import net.minecraft.nbt.NBTTagCompound;
import theflogat.technomancy.common.tiles.base.TileTechnomancyRedstone;

public class TileExistencePylon extends TileTechnomancyRedstone implements IExistenceTransmitter{
	
	public int transferRate;
	
	public TileExistencePylon(int transferRate) {
		super(RedstoneSet.LOW);
		this.transferRate = transferRate;
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		set.save(comp);
		comp.setBoolean("modified", modified);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		set = RedstoneSet.load(comp);
		modified = comp.getBoolean("modified");
	}

	@Override
	public int getMaxRate() {
		return transferRate;
	}
	
	public class TileExistencePylonBasic extends TileExistencePylon{
		
	}
}