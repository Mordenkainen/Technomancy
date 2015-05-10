package theflogat.technomancy.common.blocks.technom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockCoilTransmitter;
import theflogat.technomancy.common.items.technom.ItemCoilCoupler;
import theflogat.technomancy.common.tiles.technom.TileItemTransmitter;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;

public class BlockItemTransmitter extends BlockCoilTransmitter{

	public BlockItemTransmitter() {
		setBlockName(Ref.getId(Names.itemTransmitter));
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileItemTransmitter();
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		ItemStack items = player.inventory.mainInventory[player.inventory.currentItem];
		if(!w.isRemote && w.getTileEntity(x, y, z) instanceof TileItemTransmitter){
			TileItemTransmitter tile = (TileItemTransmitter)w.getTileEntity(x, y, z);
			if(player.isSneaking()){
				if(tile.filter!=null){
					tile.filter = null;
					w.markBlockForUpdate(x, y, z);
					return true;
				}
			}else if(items!=null && tile.filter==null){
				if(!(items.getItem() instanceof ItemCoilCoupler)){
					tile.addFilter(player.inventory.mainInventory[player.inventory.currentItem]);
					w.markBlockForUpdate(x, y, z);
					return true;
				}
			}
		}
		if(items!=null && items.getItem() instanceof ItemCoilCoupler)
			return false;
		return super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ);
	}

	@Override
	public int getRenderType() {
		return RenderIds.idItemTransmitter;
	}

}
