package theflogat.technomancy.lib.compat.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import theflogat.technomancy.common.tiles.base.TileCoilTransmitter;
import theflogat.technomancy.common.tiles.technom.TileItemTransmitter;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaTransmitter;

public class CoilTransmitterHUDHandler extends WailaHUDBase {

    @Override
    public List<String> getWailaBody(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        final TileCoilTransmitter te = (TileCoilTransmitter) accessor.getTileEntity();
        if (te.redstoneState) {
            currenttip.add("Currently Emitting a Signal");
        }
        if (te instanceof TileEssentiaTransmitter && ((TileEssentiaTransmitter) te).aspectFilter != null) {
            currenttip.add("Filter: " + ((TileEssentiaTransmitter) te).aspectFilter.getName());
        }
        if (te instanceof TileItemTransmitter && ((TileItemTransmitter) te).filter != null) {
            currenttip.add("Filter: " + StatCollector.translateToLocal(((TileItemTransmitter) te).filter.getUnlocalizedName() + ".name"));
        }
        WailaHelper.drawDefault(currenttip, te);
        return currenttip;
    }

}
