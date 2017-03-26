package theflogat.technomancy.lib.compat.waila;

import java.util.List;
import net.minecraft.tileentity.TileEntity;
import mcp.mobius.waila.api.SpecialChars;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.base.IUpgradable;

public final class WailaHelper {

    private WailaHelper () {}
    
    public static void drawDefault(final List<String> currenttip, final TileEntity te) {
        if (te instanceof IUpgradable) {
            drawBoost(currenttip, (IUpgradable) te);
        }
        if (te instanceof IRedstoneSensitive) {
            drawRedstoneSet(currenttip, te);
        }
    }

    private static void drawBoost(final List<String> currenttip, final IUpgradable te) {
        if (te.isBoosted()) {
            currenttip.add(SpecialChars.GREEN + "Potency Gem Installed");
        }
    }

    private static void drawRedstoneSet(final List<String> currenttip, final TileEntity te) {
        currenttip.add("Redstone Setting: " + formatSetting(((IRedstoneSensitive) te).getCurrentSetting().id));
        currenttip.add(((IRedstoneSensitive) te).getCurrentSetting().canRun(te) ? SpecialChars.GREEN + "Enabled" : SpecialChars.RED + "Disabled");
    }

    public static String formatSetting(final String id) {
        if ("High".equals(id)) {
            return SpecialChars.RED + "High";
        } else if ("Low".equals(id)) {
            return SpecialChars.GREEN + "Low";
        } else {
            return SpecialChars.GRAY + "None";
        }
    }
}
