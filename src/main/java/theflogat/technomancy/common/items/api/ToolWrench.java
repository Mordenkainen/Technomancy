package theflogat.technomancy.common.items.api;

import buildcraft.api.tools.IToolWrench;
import net.minecraft.item.ItemStack;

public class ToolWrench {
	
	public static boolean isWrench(ItemStack items) {
		if(items.getItem() instanceof IToolWrench){
			return true;
		}
		
		return false;
	}
}
