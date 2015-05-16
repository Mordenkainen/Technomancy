package theflogat.technomancy.common.blocks.thaumcraft.storage;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileEssentiaReservoir;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class BlockReservoir extends BlockContainerAdvanced {
	
	public BlockReservoir() {
		setBlockName(Ref.getId(Names.reservoir));
		setBlockBounds(0.125F, 0.125F, 0.125F, 0.875F, 0.875F, 0.875F);
	}
	
	@Override
	public boolean isBlockNormalCube() {
		return false;
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
