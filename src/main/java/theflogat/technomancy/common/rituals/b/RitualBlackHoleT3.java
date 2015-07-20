package theflogat.technomancy.common.rituals.b;

import theflogat.technomancy.client.models.ModelBlackSphere;

public class RitualBlackHoleT3 extends RitualBlackHole {
	
	public RitualBlackHoleT3() {
		super(new Type[]{Type.DARK,Type.DARK,Type.DARK}, Type.DARK, 9, 9, 9);
		specialRender = new ModelBlackSphere(8.1F, -1F, 0, 1F);
	}
}
