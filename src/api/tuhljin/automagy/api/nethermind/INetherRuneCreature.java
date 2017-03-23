package tuhljin.automagy.api.nethermind;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import thaumcraft.api.aspects.AspectList;


public interface INetherRuneCreature extends INetherRuneBase {

	/** Return the entity created. */
	public Entity entityCreated(int runeType, World world);

	/** Return the adjustment to stability associated with the runeType. Higher number means less stability.
	 *  This isn't a constant effect; it is applied upon creation of the associated entity. */
	public int getInstability(int runeType);

	/** Return the number of entities to spawn. */
	public int getSpawnCount(int runeType);

	/** Return the secondary essentia cost of creating the object. (Consumed by the Dimensional Lure.) */
	public AspectList secondaryEssentiaCost(int runeType);

	/** Return the number of conjurations the secondary essentia cost covers. */
	public int secondaryEssentiaCostUses(int runeType);

	/** Return a string used to identify the entity so the number of secondary essentia cost uses can be tracked.
	 *  Multiple rune types (per the runeType identifier) can use the same string. This is useful when, e.g., some
	 *  random element is involved so multiple rune types can relate to the same essentia costs but differ in other
	 *  ways. (For instance, when getRuneType is called, we randomly determines whether an additional mob will be
	 *  spawned, returning a different integer identifier depending on the choice made. This identifier allows
	 *  getSpawnCount and getInstability to likewise give a higher value but still use the same string identifier
	 *  here so the secondary essentia cost is shared.) */
	public String getEntityIdentifier(int runeType);

	/** Called after the entity is spawned into the world. Useful if you need to make adjustments after spawning
	 *  (e.g. turn that skeleton into a wither skeleton). */
	public void onEntitySpawned(int runeType, Entity entity);

	/** If any of the entities associated with this block require more space than is normally necessary between the
	 *  Nethermind and the Dimensional Lure, then this should return the largest number of blocks required.
	 *  Otherwise, return zero. */
	public int requiredLureDistance(World world, int x, int y, int z);

}
