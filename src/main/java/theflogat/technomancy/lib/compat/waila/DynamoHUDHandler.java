package theflogat.technomancy.lib.compat.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive.RedstoneSet;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.common.tiles.bloodmagic.dynamos.TileBloodDynamo;

public class DynamoHUDHandler implements IWailaDataProvider {

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		final TileDynamoBase tileEntity = (TileDynamoBase) accessor.getTileEntity();
		currenttip.add("Redstone Setting: " + formatSetting(tileEntity.set.id));
		currenttip.add(tileEntity.set.canRun(tileEntity) ? SpecialChars.GREEN + "Enabled" : SpecialChars.RED + "Disabled");
		if (tileEntity.getBoost()) {
			currenttip.add(SpecialChars.GREEN + "Potency Gem Installed");
		}
		if (tileEntity instanceof TileBloodDynamo) {
			currenttip.add(SpecialChars.DRED + "Blood: " + ((TileBloodDynamo)tileEntity).liquid + " / " + TileBloodDynamo.capacity);
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
		return null;
	}
	
	private String formatSetting(String id) {
		if (id.equals("High")) {
			return SpecialChars.RED + "High";
		} else if (id.equals("Low")) {
			return SpecialChars.GREEN + "Low";
		} else {
			return SpecialChars.GRAY + "None";
		}
	}

}
