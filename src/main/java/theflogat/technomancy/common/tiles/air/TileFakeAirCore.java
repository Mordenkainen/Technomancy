package theflogat.technomancy.common.tiles.air;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;
import theflogat.technomancy.util.Coords;

public class TileFakeAirCore extends TileTechnomancy{

	protected int x = 0;
	protected int y = 0;
	protected int z = 0;
	protected Class<?> core;

	@Override
	public void update() {
		if(!(world.getBlockState(new BlockPos(x, y, z)).getBlock().getClass()==core)){
			world.setBlockToAir(pos);
		}
	}

	public Coords getMain(){
		return new Coords(x, y, z, world);
	}

	public void addMain(int x, int y, int z, Class<?> core) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.core = core;
	}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {}

	@Override
	public void writeSyncData(NBTTagCompound comp) {
		comp.setInteger("mainx", x);
		comp.setInteger("mainy", y);
		comp.setInteger("mainz", z);
		if(core!=null)
			comp.setString("core", core.getName());
	}

	@Override
	public void readSyncData(NBTTagCompound comp) {
		x = comp.getInteger("mainx");
		y = comp.getInteger("mainy");
		z = comp.getInteger("mainz");
		try {
			core = Class.forName(comp.getString("core"));
		} catch (ClassNotFoundException e) {
			core = null;
		}
		
	}
}
