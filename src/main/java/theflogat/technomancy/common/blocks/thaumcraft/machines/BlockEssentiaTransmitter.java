package theflogat.technomancy.common.blocks.thaumcraft.machines;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import theflogat.technomancy.common.blocks.base.BlockCoilTransmitter;
import theflogat.technomancy.common.items.technom.ItemCoilCoupler;
import theflogat.technomancy.common.tiles.base.TileCoilTransmitter;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaTransmitter;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.lib.compat.Thaumcraft;

public class BlockEssentiaTransmitter extends BlockCoilTransmitter {

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileEssentiaTransmitter();
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileCoilTransmitter te = getTE(w, x, y, z);
		ItemStack stack = player.getHeldItem();
		if(te!=null) {
			TileEssentiaTransmitter tile = (TileEssentiaTransmitter) te;
			if(stack==null && player.isSneaking()) {
				if (tile.aspectFilter != null ) {
					tile.aspectFilter = null;
					if (w.isRemote) {
						w.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
					}else{
						if (!player.inventory.addItemStackToInventory(new ItemStack(Thaumcraft.itemResource, 1, 13))) {
							ForgeDirection fd = ForgeDirection.getOrientation(ForgeDirection.OPPOSITES[tile.facing]);
							w.spawnEntityInWorld(new EntityItem(w, x + 0.5F + fd.offsetX / 3.0F, y + 0.5F, z + 0.5F + fd.offsetZ /
									3.0F, new ItemStack(Thaumcraft.itemResource, 1, 13)));
						}
					}
					return true;
				}
			}
			if(stack != null) {
				if (stack.getItemDamage() == 13 && stack.getItem() == Thaumcraft.itemResource && stack.getItem() instanceof IEssentiaContainerItem) {
					if (((IEssentiaContainerItem)stack.getItem()).getAspects(stack) != null && tile.aspectFilter == null) {
						tile.aspectFilter = ((IEssentiaContainerItem)stack.getItem()).getAspects(stack).getAspects()[0];
						stack.stackSize -= 1;
						w.markBlockForUpdate(x, y, z);
						if(w.isRemote) {
							w.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
						}
						return true;
					}				
				}
			}
		}
		if(stack!=null && stack.getItem() instanceof ItemCoilCoupler)
			return false;
		return super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ);
	}
	
	@Override
	public int getRenderType() {
		return RenderIds.idEssentiaTransmitter;
	}
}
