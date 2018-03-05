package theflogat.technomancy.common.tiles.base;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import theflogat.technomancy.common.blocks.base.IMultiTiles;

public class TileSelector extends TileEntity implements ITickable{
	
	@Override
	public void update() {
		world.setTileEntity(pos, ((IMultiTiles)world.getBlockState(pos).getBlock())
				.getTile(world.getBlockState(pos)));
	}
}