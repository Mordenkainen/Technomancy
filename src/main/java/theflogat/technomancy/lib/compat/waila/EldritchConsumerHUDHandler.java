package theflogat.technomancy.lib.compat.waila;

import java.util.List;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEldritchConsumer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

public class EldritchConsumerHUDHandler implements IWailaDataProvider {

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		final TileEldritchConsumer tileEntity = (TileEldritchConsumer) accessor.getTileEntity();
		currenttip.add("Size Setting: " + tileEntity.current.toString());
		currenttip.add("Range: " + Integer.toString(tileEntity.current.r*2 + 1)+"x"+Integer.toString(tileEntity.current.r*2 + 1));
		currenttip.add("Depth: " + (tileEntity.current.h==-1 ? "To BedRock" : tileEntity.current.h));
		WailaHelper.drawDefault(currenttip, tileEntity);
		return currenttip;
	}
	
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		return null;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
		return null;
	}
}
