package theflogat.technomancy.common.potions;

import net.minecraft.potion.Potion;
import theflogat.technomancy.lib.TMConfig;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;

public final class TMPotions {

    public static Potion drown;
    public static Potion slowFall;

    private TMPotions() {}
    
    public static void initTechnomancy() {
        drown = new PotionTechnom(TMConfig.drown, true, 0).setIconIndex(1, 1).setPotionName(Reference.getId(Names.DROWN));
        slowFall = new PotionTechnom(TMConfig.slowFall, false, 0).setIconIndex(1, 1).setPotionName(Reference.getId(Names.SLOWFALL));
    }
}