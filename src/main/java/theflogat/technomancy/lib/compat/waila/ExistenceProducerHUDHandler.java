package theflogat.technomancy.lib.compat.waila;

import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.ItemStack;
import theflogat.technomancy.common.tiles.technom.existence.IExistenceProducer;

public class ExistenceProducerHUDHandler extends WailaHUDNBT {

    @Override
    public List<String> getWailaBody(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        WailaHelper.drawDefault(currenttip, accessor.getTileEntity());
        if (accessor.getPlayer().isSneaking()) {
            final IExistenceProducer tile = (IExistenceProducer) accessor.getTileEntity();
            currenttip.add("Power:" + tile.getPower() + "/" + tile.getPowerCap());
        }
        return currenttip;
    }

}
