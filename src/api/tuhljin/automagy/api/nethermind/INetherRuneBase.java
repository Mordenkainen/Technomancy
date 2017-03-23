package tuhljin.automagy.api.nethermind;

import net.minecraft.world.World;
import thaumcraft.api.aspects.AspectList;


public abstract interface INetherRuneBase {

	/** Returns an integer that will be used to identify the type of rune. These types are used in subsequent calls to
	 *  the class to get the object created, essentia cost, etc., and so should identify to that block class what the rune
	 *  is, but they don't have to be unique in relation to other blocks since they'll use their own set of integers.
	 *  Should generally return zero or greater; those with less than zero are considered "basic" runes and are treated
	 *  differently. The return could be the same each time or based on metadata or even random. */
	public int getRuneType(World world, int x, int y, int z);

	/** Return how many ticks until the object is created (after essentia is consumed). Must be one or higher.*/
	public int ticksUntilCreated(int runeType);

	/** Return the essentia cost of creating the object. */
	public AspectList essentiaCostPerCreation(int runeType);

	/** This is called when this rune is going to be used. Use it to change its appearance (e.g. adjust its metadata).
	 *  If getRuneType returned a negative number, this won't be called. */
	public void activateRune(World world, int x, int y, int z, int runeType);

	/** This is called when this rune is no longer being used. Use it to change its appearance (e.g. adjust its metadata).
	 *  If getRuneType returned a negative number, this won't be called. */
	public void deactivateRune(World world, int x, int y, int z, int runeType);

	/** Return a RuneCategory to indicate special behavior or null for standard behavior. */
	public RuneCategory getRuneCategory(int runeType);

}
