package theflogat.technomancy.common.blocks.technom.existence;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerBase;
import theflogat.technomancy.common.tiles.technom.existence.TileExistencePylon;
import theflogat.technomancy.common.tiles.technom.existence.TileExistencePylon.Type;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class BlockExistencePylon extends BlockContainerBase{
	
	public BlockExistencePylon() {
		setBlockName(Ref.getId(Names.existencePylon));
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileExistencePylon(Type.getTypeFromId(meta));
	}
}