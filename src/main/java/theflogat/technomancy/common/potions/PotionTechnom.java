package theflogat.technomancy.common.potions;

import net.minecraft.potion.Potion;

public class PotionTechnom extends Potion {

    public PotionTechnom(final int id, final boolean isNeg, final int color) {
        super(id, isNeg, color);
    }

    @Override
    public Potion setIconIndex(final int par1, final int par2) {
        super.setIconIndex(par1, par2);
        return this;
    }
}