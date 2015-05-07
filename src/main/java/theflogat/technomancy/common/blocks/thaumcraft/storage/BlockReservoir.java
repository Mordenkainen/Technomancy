package theflogat.technomancy.common.blocks.thaumcraft.storage;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerBase;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileEssentiaReservoir;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class BlockReservoir extends BlockContainerAdvanced {
	
	public BlockReservoir() {
		setBlockName(Ref.getId(Names.reservoir));
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World w, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x+.2, y+.2, z+.2, x-.2, y-.2, z-.2);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z) {
		return getSelectedBoundingBoxFromPool(w, x, y, z);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(Ref.getAsset(Names.reservoir));
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileEssentiaReservoir();
	}

}
