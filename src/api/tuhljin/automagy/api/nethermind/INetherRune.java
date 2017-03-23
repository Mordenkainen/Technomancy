package tuhljin.automagy.api.nethermind;

import net.minecraft.block.Block;


public interface INetherRune extends INetherRuneBase {

	/** Returns the block to be created. */
	public Block blockCreated(int runeType);

	/** Returns the metadata of the block to be created. */
	public int blockCreatedMetadata(int runeType);

}
