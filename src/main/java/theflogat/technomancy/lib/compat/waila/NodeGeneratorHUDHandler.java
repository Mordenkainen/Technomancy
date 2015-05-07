package theflogat.technomancy.lib.compat.waila;

import java.util.List;

import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;

public class NodeGeneratorHUDHandler implements IWailaDataProvider {

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		final TileNodeGenerator tileEntity = (TileNodeGenerator) accessor.getTileEntity();
		currenttip.add(tileEntity.canRun() ? SpecialChars.GREEN + "Enabled" : SpecialChars.RED + "Disabled");
		if (tileEntity.getBoost()) {
			currenttip.add(SpecialChars.GREEN + "Potency Gem Installed");
		}
		if (accessor.getNBTData().getBoolean("Active")) {
			currenttip.add(accessor.getNBTData().getBoolean("Spawn") ? "Mode: Create Node" : "Mode: Recharge Node");
		}
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
		if (te != null) {
            te.writeToNBT(tag);
		}
        return tag;
	}
}
