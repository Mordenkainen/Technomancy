package theflogat.technomancy.lib.compat.waila;

import java.util.List;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileFluxLamp;
import net.minecraft.item.ItemStack;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.SpecialChars;

public class FluxLampHUDHandler extends WailaHUDBase {

    @Override
    public List<String> getWailaBody(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        final TileFluxLamp tileEntity = (TileFluxLamp) accessor.getTileEntity();
        currenttip.add(SpecialChars.DPURPLE + "Flux: " + tileEntity.tank.getFluidAmount() + "/" + tileEntity.tank.getCapacity());
        WailaHelper.drawDefault(currenttip, tileEntity);
        return currenttip;
    }

}
