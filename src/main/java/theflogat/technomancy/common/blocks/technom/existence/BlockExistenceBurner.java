package theflogat.technomancy.common.blocks.technom.existence;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerBase;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceBurner;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class BlockExistenceBurner extends BlockContainerBase{
	
	public BlockExistenceBurner() {
		setBlockName(Ref.getId(Names.existenceBurner));
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileExistenceBurner();
	}
}