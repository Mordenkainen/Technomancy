package theflogat.technomancy.common.potions;

import net.minecraft.potion.Potion;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class TMPotions {
	
	public static Potion drown;
	public static Potion slowFall;

	public static void initTechnomancy() {
		drown = new PotionTechnom(Ids.drown, true, 0).setIconIndex(1,1).setPotionName(Ref.getId(Names.drown));
		slowFall = new PotionTechnom(Ids.slowFall, false, 0).setIconIndex(1,1).setPotionName(Ref.getId(Names.slowFall));
	}
}