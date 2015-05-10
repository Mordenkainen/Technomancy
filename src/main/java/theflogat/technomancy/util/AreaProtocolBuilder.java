package theflogat.technomancy.util;

import net.minecraft.block.Block;

public class AreaProtocolBuilder {
	
	Coords start;
	Area area;
	
	public AreaProtocolBuilder(Coords start, Area area) {
		this.start = start;
		this.area = area;
	}
	
	public void buildNext(Block b) {
		boolean flag = true;
		while(area.hasNext() && flag){
			Coords c = area.next();
			flag = c==null;
			if(!flag){
				c.w.setBlock(start.x + c.x, start.y + c.y, start.z + c.z, b);
			}
		}
	}
}
