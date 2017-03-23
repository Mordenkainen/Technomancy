package me.jezza.thaumicpipes.api;

/**
 * Used to flag what type of tile it is. Either given a direction or not.
 */
public enum TileType {
    INPUT,
    STORAGE,
    OUTPUT;

    public static final int[] OPPOSITES = {2, 1, 0};

    public TileType getOpposite() {
        return values()[OPPOSITES[ordinal()]];
    }
}
