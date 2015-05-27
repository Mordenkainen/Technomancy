package theflogat.technomancy.common.blocks.technom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockCoilTransmitter;
import theflogat.technomancy.common.items.technom.ItemBoost;
import theflogat.technomancy.common.items.technom.ItemCoilCoupler;
import theflogat.technomancy.common.tiles.technom.TileItemTransmitter;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;

public class BlockItemTransmitter extends BlockCoilTransmitter{

	public BlockItemTransmitter() {
		super();
		setBlockName(Ref.getId(Names.itemTransmitter));
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileItemTransmitter();
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		ItemStack items = player.inventory.mainInventory[player.inventory.currentItem];
		if(items!=null && (items.getItem() instanceof ItemCoilCoupler || items.getItem() instanceof ItemBoost)) {
			return false;
		}
		if(super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ)) {
			return true;
		}
		TileItemTransmitter tile = getTE(w, x, y, z);
		if(tile!= null){
			if(player.isSneaking()) {
				if(tile.filter!=null) {
					if(!w.isRemote) {
						tile.filter = null;
						w.markBlockForUpdate(x, y, z);
					}
					return true;
				}
			} else if(items!=null && tile.filter==null) {
				if(!w.isRemote) {
					tile.addFilter(player.inventory.mainInventory[player.inventory.currentItem]);
					w.markBlockForUpdate(x, y, z);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderIds.idItemTransmitter;
	}
	
	private static TileItemTransmitter getTE(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		return tile instanceof TileItemTransmitter ? (TileItemTransmitter)tile : null;
	}
}
