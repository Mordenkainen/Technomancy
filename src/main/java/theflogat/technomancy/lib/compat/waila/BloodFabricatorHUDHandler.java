package theflogat.technomancy.lib.compat.waila;

import java.util.List;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBloodFabricator;
import net.minecraft.item.ItemStack;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.SpecialChars;

public class BloodFabricatorHUDHandler extends WailaHudBase {

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        final TileBloodFabricator tileEntity = (TileBloodFabricator) accessor.getTileEntity();
        currenttip.add(SpecialChars.DRED + "Blood: " + tileEntity.tank.getFluidAmount() + "/" + tileEntity.tank.getCapacity());
        WailaHelper.drawDefault(currenttip, tileEntity);
        return currenttip;
    }

}
