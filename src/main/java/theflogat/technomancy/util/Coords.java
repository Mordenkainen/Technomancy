package theflogat.technomancy.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import theflogat.technomancy.util.helpers.WorldHelper;

public class Coords {
	public int x;
	public int y;
	public int z;
	public World w;
	
	public Coords(int x, int y, int z, World world) {
		this.x = x;
		this.y = y;
		this.z = z;
		w = world;
	}
	
	public Coords(TileEntity te) {
		x = te.getPos().getX();
		y = te.getPos().getY();
		z = te.getPos().getZ();
		w = te.getWorld();
	}

	@Override
	public boolean equals(Object o) {
		Coords c = (Coords)o;
		return x==c.x && y==c.y && z==c.z;
	}
	
	public TileEntity getTile() {
		return w.getTileEntity(new BlockPos(x, y, z));
	}
	
	public void setAirAndDrop(){
		WorldHelper.destroyAndDrop(w, x, y, z);
	}
	
	public void print(){
		System.out.println("X:" + x + " Y:" + y + " Z:" + z);
	}
}