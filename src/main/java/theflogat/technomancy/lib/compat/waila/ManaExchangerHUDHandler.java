package theflogat.technomancy.lib.compat.waila;

import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;
import theflogat.technomancy.common.tiles.botania.machines.TileManaExchanger;

public class ManaExchangerHUDHandler extends WailaHUDNBT {

    @Override
    public List<String> getWailaBody(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        final TileManaExchanger tileEntity = (TileManaExchanger) accessor.getTileEntity();
        currenttip.add(tileEntity.mode ? "Exporting Mana" : "Importing Mana");
        final FluidTank tank = new FluidTank(1000);
        tank.readFromNBT(accessor.getNBTData());
        currenttip.add("Mana Condensate: " + tank.getFluidAmount() + "mB");
        WailaHelper.drawDefault(currenttip, tileEntity);
        return currenttip;
    }

}
