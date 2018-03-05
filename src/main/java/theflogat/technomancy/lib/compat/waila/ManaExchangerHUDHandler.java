package theflogat.technomancy.lib.compat.waila;

import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import theflogat.technomancy.common.tiles.botania.machines.TileManaExchanger;

import javax.annotation.Nonnull;

public class ManaExchangerHUDHandler implements IWailaDataProvider {

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		final TileManaExchanger tileEntity = (TileManaExchanger) accessor.getTileEntity();
		currenttip.add(tileEntity.mode ? "Exporting Mana" : "Importing Mana");
		FluidTank tank = new FluidTank(1000);
		tank.readFromNBT(accessor.getNBTData());
		currenttip.add("Mana Condensate: " + tank.getFluidAmount() + "mB");
		WailaHelper.drawDefault(currenttip, tileEntity);
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

	@Nonnull
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
		if (te != null) {
			te.writeToNBT(tag);
		}
		return tag;
	}
}
