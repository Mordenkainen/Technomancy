package theflogat.technomancy.lib.compat.waila;

import java.util.List;
import net.minecraft.item.ItemStack;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.SpecialChars;

public class BiomeMorpherHUDHandler extends WailaHUDNBT {

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        currenttip.add("Biome: " + biomeForMeta(accessor.getMetadata()));
        WailaHelper.drawDefault(currenttip, accessor.getTileEntity());
        return currenttip;
    }

    private static String biomeForMeta(int meta) {
        if (meta == 0) {
            return SpecialChars.GREEN + "Magical Forest";
        } else if (meta == 1) {
            return SpecialChars.DGRAY + "Eerie";
        } else if (meta == 2) {
            return SpecialChars.DPURPLE + "Tainted Land";
        } else {
            return "Unknown";
        }
    }
}
