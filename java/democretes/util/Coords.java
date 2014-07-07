package democretes.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
	
	public TileEntity getTile() {
		return w.getBlockTileEntity(x, y, z);
	}
	
	public void print(){
		System.out.println("X:" + x + " Y:" + y + " Z:" + z);
	}
}