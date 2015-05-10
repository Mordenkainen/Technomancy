package theflogat.technomancy.lib.compat.waila;

import java.util.List;
import net.minecraft.tileentity.TileEntity;
import mcp.mobius.waila.api.SpecialChars;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.base.IUpgradable;

public class WailaHelper {
	
	public static void drawDefault(List<String> currenttip, TileEntity te){
		if(te instanceof IUpgradable){
			drawBoost(currenttip, (IUpgradable) te);
		}
		if(te instanceof IRedstoneSensitive){
			drawRedstoneSet(currenttip, te);
		}
	}
	
	private static void drawBoost(List<String> currenttip, IUpgradable te) {
		if (te.getBoost()) {
			currenttip.add(SpecialChars.GREEN + "Potency Gem Installed");
		}
	}
	
	private static void drawRedstoneSet(List<String> currenttip, TileEntity te){
		currenttip.add("Redstone Setting: " + formatSetting(((IRedstoneSensitive)te).getCurrentSetting().id));
		currenttip.add(((IRedstoneSensitive)te).getCurrentSetting().canRun(te) ? SpecialChars.GREEN + "Enabled" : SpecialChars.RED + "Disabled");
	}
	
	private static String formatSetting(String id) {
		if (id.equals("High")) {
			return SpecialChars.RED + "High";
		} else if (id.equals("Low")) {
			return SpecialChars.GREEN + "Low";
		} else {
			return SpecialChars.GRAY + "None";
		}
	}
}
