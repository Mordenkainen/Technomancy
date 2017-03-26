package theflogat.technomancy.lib.compat.waila;

import java.util.List;

import theflogat.technomancy.common.tiles.thaumcraft.machine.TileCondenser;
import net.minecraft.item.ItemStack;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public class CondenserHUDHandler extends WailaHUDBase {

    @Override
    public List<String> getWailaBody(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        final TileCondenser tileEntity = (TileCondenser) accessor.getTileEntity();
        WailaHelper.drawDefault(currenttip, tileEntity);
        return currenttip;
    }
    
}
