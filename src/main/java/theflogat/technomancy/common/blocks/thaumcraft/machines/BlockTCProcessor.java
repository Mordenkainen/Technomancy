package theflogat.technomancy.common.blocks.thaumcraft.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.BlockProcessor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileTCProcessor;

public class BlockTCProcessor extends BlockProcessor {

	public BlockTCProcessor() {
		this.name = "TC";
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(player != null) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileTCProcessor) {		
				player.openGui(Technomancy.instance, 0, world, x, y, z);
			}
		}		
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileTCProcessor();
	}
}