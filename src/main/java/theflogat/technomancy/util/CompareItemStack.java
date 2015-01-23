package theflogat.technomancy.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public abstract class CompareItemStack {
	
	public abstract boolean isCorrectItemStack(ItemStack items);


	



	//------------------------------------------------------------------//

	protected static boolean isOre(String name, ItemStack items){
		for(int i : OreDictionary.getOreIDs(items)){
			if(OreDictionary.getOreName(i) == name)
				return true;
		}
		return false;
	}
}