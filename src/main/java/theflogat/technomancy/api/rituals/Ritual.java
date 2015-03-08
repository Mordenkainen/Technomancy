package theflogat.technomancy.api.rituals;

import net.minecraft.world.World;

public abstract class Ritual {
	
	protected int black = 0;
	protected int white = 1;
	protected int red = 2;
	protected int green = 3;
	protected int blue = 4;
	
	protected int id = 0;
	
	protected void setId(int id){
		this.id = id;
	}
	
	public abstract boolean isCoreComplete(World w, int x, int y, int z);
	
	public abstract boolean isFrameComplete(World w, int x, int y, int z);

	public abstract boolean canApplyEffect(World w, int x, int y, int z);

	public abstract void applyEffect(World w, int x, int y, int z);
}