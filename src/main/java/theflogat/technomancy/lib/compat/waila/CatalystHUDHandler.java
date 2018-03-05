package theflogat.technomancy.lib.compat.waila;

import java.util.List;

import net.minecraft.util.math.BlockPos;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

public class CatalystHUDHandler implements IWailaDataProvider {

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
		return null;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		final TileCatalyst te = (TileCatalyst) accessor.getTileEntity();
		currenttip.add("Is active: " + (te.handler!=null));
		WailaHelper.drawDefault(currenttip, te);
		return currenttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack arg0, List<String> arg1, IWailaDataAccessor arg2, IWailaConfigHandler arg3) {
		return null;
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor arg0, IWailaConfigHandler arg1) {
		return ItemStack.EMPTY;
	}

	@Override
	public List<String> getWailaTail(ItemStack arg0, List<String> arg1, IWailaDataAccessor arg2, IWailaConfigHandler arg3) {
		return null;
	}

}
