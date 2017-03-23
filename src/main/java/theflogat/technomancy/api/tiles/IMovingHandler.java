package theflogat.technomancy.api.tiles;

import net.minecraft.world.World;

public interface IMovingHandler {

    /**
     * Can the block be moved?
     */

    boolean canMove(World w, int x, int y, int z);

    /**
     * Save the info. If want to pass data, you are free to return an Object[]
     * with it It can also be null
     */

    Object[] save(World w, int x, int y, int z);

    /**
     * Load the data. The Object[] is the same you passed on with the save
     * function
     */

    void load(World w, int x, int y, int z, Object[] d); // NOPMD

}
