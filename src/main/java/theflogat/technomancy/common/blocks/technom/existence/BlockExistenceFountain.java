package theflogat.technomancy.common.blocks.technom.existence;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerBase;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceFountain;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;

public class BlockExistenceFountain extends BlockContainerBase{
	
	public BlockExistenceFountain() {
		setBlockName(Ref.getId(Names.existenceFountain));
	}
	
	@Override
	public int getRenderType() {
		return RenderIds.idExFountain;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileExistenceFountain();
	}
}
