package theflogat.technomancy.lib.compat.waila;

import java.util.List;

import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaFusor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public class EssentiaFusorHUDHandler extends WailaHUDNBT {

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        WailaHelper.drawDefault(currenttip, accessor.getTileEntity());
        if (accessor.getPlayer().isSneaking()) {
            TileEssentiaFusor tile = (TileEssentiaFusor) accessor.getTileEntity();
            for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                if (side != ForgeDirection.UP && side != ForgeDirection.DOWN) {
                    if (tile.getEssentiaType(side) != null) {
                        currenttip.add(side.name() + " : " + tile.getEssentiaType(side).getName());
                    } else {
                        currenttip.add(side.name() + " : None");
                    }
                }
            }
        }
        return currenttip;
    }
    
}
