package tuhljin.automagy.api.essentia;

import thaumcraft.api.aspects.IAspectSource;

/**
 * @author Tuhljin
 * 
 * Tile entities that implement this interface will be readable by the Essentia Locus and can have essentia drawn
 * out of them by the Essentia Aggregator.
 * Consider implementing IAspectContainerWithMax also (if applicable) as that will not only help vis readers
 * that are directly targeting the tile but it will help any that are targeting the Essentia Locus since the Locus
 * tracks and reports the maximum values it has encountered.
 */
public interface IEssentiaLocusReadable extends IAspectSource {

}
