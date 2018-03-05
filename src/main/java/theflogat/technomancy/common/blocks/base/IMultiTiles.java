package theflogat.technomancy.common.blocks.base;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;

public interface IMultiTiles {
	public TileEntity getTile(IBlockState state);
}
