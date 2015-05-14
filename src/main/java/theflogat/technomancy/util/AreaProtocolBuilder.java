package theflogat.technomancy.util;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public abstract class AreaProtocolBuilder {
	
	public static final Random rand = new Random();
	Coords start;
	Area area;
	
	public AreaProtocolBuilder(Coords start, Area area) {
		this.start = start;
		this.area = area;
	}
	
	public boolean buildNext(World w, Block b, Coords core) {
		while(area.hasNext()){
			Coords c = area.next(core);
			if(c!=null && isPosValid(c)){
				w.setBlock(start.x + c.x, start.y + c.y, start.z + c.z, b);
				return true;
			}
		}
		if(!area.hasNext()){
			return false;
		}
		return false;
	}

	public abstract boolean isPosValid(Coords c);
	
	public int remainingIterations(){
		return area.getIterationsLeft();
	}
}
