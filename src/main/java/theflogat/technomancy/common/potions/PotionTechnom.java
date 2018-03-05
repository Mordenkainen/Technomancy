package theflogat.technomancy.common.potions;

import net.minecraft.potion.Potion;

public class PotionTechnom extends Potion{

	public PotionTechnom(int id, boolean isNeg, int color) {
		super(isNeg, color);
	}

	public Potion setIconIndex(int par1, int par2){
		super.setIconIndex(par1, par2);
		return this;
	}
}