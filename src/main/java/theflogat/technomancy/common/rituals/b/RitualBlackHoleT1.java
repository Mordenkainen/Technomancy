package theflogat.technomancy.common.rituals.b;

import theflogat.technomancy.client.models.ModelBlackSphere;

public class RitualBlackHoleT1 extends RitualBlackHole {

	public RitualBlackHoleT1() {
		super(new Type[]{Type.DARK}, Type.DARK, 3, 3, 3);
		specialRender = new ModelBlackSphere(3F, -1F, 0, 1F);
	}
}
