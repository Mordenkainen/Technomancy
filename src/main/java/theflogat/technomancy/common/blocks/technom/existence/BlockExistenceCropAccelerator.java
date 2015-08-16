package theflogat.technomancy.common.blocks.technom.existence;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerBase;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceCropAccelerator;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class BlockExistenceCropAccelerator extends BlockContainerBase{
	
	public BlockExistenceCropAccelerator() {
		setBlockName(Ref.getId(Names.existenceCropAcc));
	}
	
	IIcon top;
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(Ref.getAsset(Names.existenceCropAcc));
		top = reg.registerIcon(Ref.getAsset(Names.existenceCropAcc + "_top"));
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if(side==1){
			return top;
		}
		return blockIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileExistenceCropAccelerator();
	}
}
