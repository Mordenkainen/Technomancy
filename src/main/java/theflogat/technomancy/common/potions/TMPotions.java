package theflogat.technomancy.common.potions;

import net.minecraft.potion.Potion;
import theflogat.technomancy.lib.TMConfig;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;

public class TMPotions {

    public static Potion drown;
    public static Potion slowFall;

    public static void initTechnomancy() {
        drown = new PotionTechnom(TMConfig.drown, true, 0).setIconIndex(1, 1).setPotionName(Reference.getId(Names.DROWN));
        slowFall = new PotionTechnom(TMConfig.slowFall, false, 0).setIconIndex(1, 1).setPotionName(Reference.getId(Names.SLOWFALL));
    }
}