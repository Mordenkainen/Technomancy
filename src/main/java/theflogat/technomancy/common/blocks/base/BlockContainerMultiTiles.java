package theflogat.technomancy.common.blocks.base;

import theflogat.technomancy.common.tiles.base.TileSelector;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockContainerMultiTiles extends BlockContainerBase implements IMultiTiles{

	@Override
	public TileEntity createNewTileEntity(World w, int wut) {
		return new TileSelector();
	}
}
