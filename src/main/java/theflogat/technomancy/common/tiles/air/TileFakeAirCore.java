package theflogat.technomancy.common.tiles.air;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import theflogat.technomancy.util.Coords;

public class TileFakeAirCore extends TileEntity{

	protected int x = 0;
	protected int y = 0;
	protected int z = 0;
	protected Class<?> core;

	@Override
	public void updateEntity() {
		if(!(worldObj.getBlock(x, y, z).getClass()==core)){
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
	}

	public Coords getMain(){
		return new Coords(x, y, z, worldObj);
	}

	public void addMain(int x, int y, int z, Class<?> core) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.core = core;
	}

	@Override
	public void readFromNBT(NBTTagCompound comp) {
		super.readFromNBT(comp);
		x = comp.getInteger("mainx");
		y = comp.getInteger("mainy");
		z = comp.getInteger("mainz");
		try {
			core = Class.forName(comp.getString("core"));
		} catch (ClassNotFoundException e) {
			core = null;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound comp) {
		super.writeToNBT(comp);
		comp.setInteger("mainx", x);
		comp.setInteger("mainy", y);
		comp.setInteger("mainz", z);
		if(core!=null)
			comp.setString("core", core.getName());
	}
}
