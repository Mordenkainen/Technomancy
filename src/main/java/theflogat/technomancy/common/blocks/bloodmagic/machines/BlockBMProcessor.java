package theflogat.technomancy.common.blocks.bloodmagic.machines;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.BlockProcessor;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBMProcessor;

public class BlockBMProcessor extends BlockProcessor {
	
	public BlockBMProcessor() {
		this.name = "BM";
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(player != null) {
			TileEntity te = w.getTileEntity(x, y, z);
			if(te instanceof TileBMProcessor) {
				if(((TileBMProcessor)te).owner.equals("")){
					((TileBMProcessor)te).owner = player.getDisplayName();
				}
				player.openGui(Technomancy.instance, 1, w, x, y, z);
			}
		}
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileBMProcessor && entity instanceof EntityPlayer) {
			((TileBMProcessor)tile).owner = ((EntityPlayer)entity).getDisplayName();
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileBMProcessor();
	}
}