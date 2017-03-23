package tuhljin.automagy.api.essentia;

import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;

/**
 * @author Tuhljin
 * 
 * This interface should be implemented by tile entities that have a set maximum
 * amount of an aspect they can hold. This lets vis readers know what their
 * capacities are when it is in "Auto" mode.
 */
public interface IAspectContainerWithMax extends IAspectContainer {

	public AspectList getAspectsBase();

}

/* Example implementation:

public class MyEssentiaJar implements IAspectContainerWithMax {

	Aspect aspectContained;
	Aspect jarLabel;
	int maxAmount;
	int currentAmount;

	@Override
	public AspectList getAspectsBase() {
		Aspect a = aspectContained != null ? aspectContained : jarLabel;
		if (a != null) {
			return new AspectList().add(a, maxAmount);
		}
		return null;
	}

}

 */
