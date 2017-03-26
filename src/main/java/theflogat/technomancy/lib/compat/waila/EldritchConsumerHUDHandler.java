package theflogat.technomancy.lib.compat.waila;

import java.util.List;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEldritchConsumer;
import net.minecraft.item.ItemStack;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public class EldritchConsumerHUDHandler extends WailaHUDBase {

    @Override
    public List<String> getWailaBody(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        final TileEldritchConsumer tileEntity = (TileEldritchConsumer) accessor.getTileEntity();
        currenttip.add("Size Setting: " + tileEntity.current.toString());
        currenttip.add("Range: " + Integer.toString(tileEntity.current.r * 2 + 1) + "x" + Integer.toString(tileEntity.current.r * 2 + 1));
        currenttip.add("Depth: " + (tileEntity.current.h == -1 ? "To BedRock" : tileEntity.current.h));
        WailaHelper.drawDefault(currenttip, tileEntity);
        return currenttip;
    }

}
