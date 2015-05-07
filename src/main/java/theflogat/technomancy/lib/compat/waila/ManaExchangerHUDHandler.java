package theflogat.technomancy.lib.compat.waila;

import java.util.List;

import theflogat.technomancy.common.tiles.base.IRedstoneSensitive.RedstoneSet;
import theflogat.technomancy.common.tiles.botania.machines.TileManaExchanger;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;

public class ManaExchangerHUDHandler implements IWailaDataProvider {

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		final TileManaExchanger tileEntity = (TileManaExchanger) accessor.getTileEntity();
		currenttip.add("Redstone Setting: " + formatSetting(tileEntity.set.id));
		currenttip.add(tileEntity.set.canRun(tileEntity) ? SpecialChars.GREEN + "Enabled" : SpecialChars.RED + "Disabled");
		currenttip.add(tileEntity.mode ? "Exporting Mana" : "Importing Mana");
		FluidTank tank = new FluidTank(1000);
		tank.readFromNBT(accessor.getNBTData());
		currenttip.add("Mana Condensate: " + tank.getFluidAmount() + "mB");
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
