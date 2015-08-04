package theflogat.technomancy.common.blocks.technom;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class BlockFountainExistence extends BlockContainerBase{
	
	public BlockFountainExistence() {
		setBlockName(Ref.getId(Names.fountainExistence));
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}
}
