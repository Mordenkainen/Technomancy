package theflogat.technomancy.util;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class ToolWrench {
	private static ArrayList<String> wrenchInterfaces = new ArrayList<String>();
	
	static {
		wrenchInterfaces.add("IToolWrench");
		wrenchInterfaces.add("IYetaWrench");
		wrenchInterfaces.add("IAEWrench");
		wrenchInterfaces.add("IToolHammer");
		wrenchInterfaces.add("IToolCrowbar");
	}
	
	public static boolean isWrench(ItemStack items) {
		Class<?>[] interfaces = items.getItem().getClass().getInterfaces();
		for (Class<?> curInterface: interfaces) {
			String intName = curInterface.getName();
			if(wrenchInterfaces.contains(intName.substring(intName.lastIndexOf('.') + 1, intName.length()))) {
				return true;
			}
		}
		
		return false;
	}
}
