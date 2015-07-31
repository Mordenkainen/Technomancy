package me.jezza.thaumicpipes.api.interfaces;

/**
 * This interface is both an input and an output.
 * If you have a directional block, you can probably just extend the TileProperties objects.
 *
 * Two examples:
 * Firstly, an actual storage device.
 * The jar. It's a storage device. It holds it for the network. Nothing more.
 *
 * Second example, in which case, you need to use the directional functions of TileProperties:
 * The centrifuge is both an input and an output.
 * It inputs into the bottom, and pushes it out the top.
 *
 * So, in my code, I register it as such:
 * ThaumicRegistry.registerTile(TileCentrifuge.class, TileProperties.OUTPUT.addDirectionalFlag(ForgeDirection.DOWN, TileType.INPUT));
 *
 * Simple as that.
 * This tells the network that it's going to be putting into the top of the network, and pulling out from the top.
 *
 * Makes the whole process faster.
 * If I just registered the centrifuge as a storage device, the network treats it as a device that SOLELY HOLDS the essentia.
 */
public interface IThaumicStorage {
}
