package theflogat.technomancy.common.tiles.air;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;
import theflogat.technomancy.common.blocks.machines.BlockNodeGenerator;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.util.Coords;

public class TileFakeAirCore extends TileTechnomancy{

	protected int x;
	protected int y;
	protected int z;
	protected Class core;

	@Override
	public void updateEntity() {
		if(!(worldObj.getBlock(x, y, z).getClass()==core)){
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
	}

	public Coords getMain(){
		return new Coords(x, y, z, worldObj);
	}

	public void addMain(int x, int y, int z, Class core) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.core = core;
	}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		x = comp.getInteger("mainx");
		y = comp.getInteger("mainy");
		z = comp.getInteger("mainz");
		try {
			core = Class.forName(comp.getString("core"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		comp.setInteger("mainx", x);
		comp.setInteger("mainy", y);
		comp.setInteger("mainz", z);
		comp.setString("core", core.getName());
	}
}
