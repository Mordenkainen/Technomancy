package theflogat.technomancy.lib.compat.waila;

import java.util.List;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import net.minecraft.item.ItemStack;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public class CatalystHUDHandler extends WailaHUDBase {

    @Override
    public List<String> getWailaBody(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        final TileCatalyst te = (TileCatalyst) accessor.getTileEntity();
        currenttip.add("Is active: " + (te.handler != null));
        WailaHelper.drawDefault(currenttip, te);
        return currenttip;
    }

}
