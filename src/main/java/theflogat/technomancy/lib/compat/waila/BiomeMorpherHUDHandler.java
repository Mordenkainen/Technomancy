package theflogat.technomancy.lib.compat.waila;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;

public class BiomeMorpherHUDHandler implements IWailaDataProvider {

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		currenttip.add("Biome: " + biomeForMeta(accessor.getMetadata()));
		WailaHelper.drawDefault(currenttip, accessor.getTileEntity());
		return currenttip;
	}
	
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		return ItemStack.EMPTY;
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
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
		if (te != null) {
            te.writeToNBT(tag);
		}
        return tag;
	}
	
	private static String biomeForMeta(int meta) {
		if (meta == 0) {
			return SpecialChars.GREEN + "Magical Forest";
		}else if (meta == 1) {
			return SpecialChars.DGRAY + "Eerie";
		}else if (meta == 2) {
			return SpecialChars.DPURPLE + "Tainted Land";
		} else {
			return "Unknown";
		}
	}
}
