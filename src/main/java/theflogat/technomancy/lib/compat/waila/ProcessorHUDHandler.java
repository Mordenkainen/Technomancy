package theflogat.technomancy.lib.compat.waila;

import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.ItemStack;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;

public class ProcessorHUDHandler extends WailaHUDBase {

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        final TileProcessorBase tileEntity = (TileProcessorBase) accessor.getTileEntity();
        if (tileEntity.isActive) {
            currenttip.add("Progress: " + (int) (((TileProcessorBase.maxTime - tileEntity.progress) * 100F) / TileProcessorBase.maxTime) + "%");
        }
        WailaHelper.drawDefault(currenttip, tileEntity);
        return currenttip;
    }

}
