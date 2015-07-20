package theflogat.technomancy.common.rituals.b;

import theflogat.technomancy.client.models.ModelBlackSphere;

public class RitualBlackHoleT2 extends RitualBlackHole {
	
	public RitualBlackHoleT2() {
		super(new Type[]{Type.DARK,Type.DARK}, Type.DARK, 5, 5, 5);
		specialRender = new ModelBlackSphere(4.7F, -1F, 0, 1F);
	}
}
