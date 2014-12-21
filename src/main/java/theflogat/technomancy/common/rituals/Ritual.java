package theflogat.technomancy.common.rituals;

import net.minecraft.world.World;

public abstract class Ritual {
	private int id = 0;
	
	protected void setId(int id){
		this.id = id;
	}
	
	public abstract boolean isFrameComplete(World w, int x, int y, int z);

	public abstract boolean applyEffect(World w, int x, int y, int z);
}