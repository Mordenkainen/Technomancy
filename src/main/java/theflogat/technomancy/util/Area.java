package theflogat.technomancy.util;

import net.minecraft.world.World;

public abstract class Area{
	public int lengthX;
	public int lengthY;
	public int lengthZ;
	public World w;
	public int currX = 0;
	public int currY = 0;
	public int currZ = 0;
	public boolean end;
	
	public Area(World w, int lengthX, int lengthY, int lengthZ) {
		this.lengthX = lengthX;
		this.lengthY = lengthY;
		this.lengthZ = lengthZ;
		this.w = w;
	}
	
	public boolean hasNext(){
		return !end;
	}
	
	public Coords next(){
		Coords c = new Coords(currX, currY, currZ, w);
		if(currX==lengthX){
			if(currZ==lengthZ){
				if(currY==lengthY){
					currX = 0;
					currY = 0;
					currZ = 0;
					end = true;
				}else{
					currX = 0;
					currZ = 0;
					currY++;
				}
			}else{
				currX = 0;
				currZ++;
			}
		}else{
			currX++;
		}
		if(isPosValid(c))
			return c;
		return null;
	}
	
	public void reiterate(){
		currX = 0;
		currY = 0;
		currZ = 0;
		end = false;
	}
	
	public abstract boolean isPosValid(Coords c);
}