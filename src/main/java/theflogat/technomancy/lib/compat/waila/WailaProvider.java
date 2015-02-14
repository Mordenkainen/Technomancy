package theflogat.technomancy.lib.compat.waila;

import theflogat.technomancy.common.blocks.dynamos.BlockEssentiaDynamo;
import theflogat.technomancy.common.blocks.dynamos.BlockNodeDynamo;
import theflogat.technomancy.common.blocks.machines.BlockBiomeMorpher;
import theflogat.technomancy.common.tiles.dynamos.TileEssentiaDynamo;
import theflogat.technomancy.common.tiles.dynamos.TileNodeDynamo;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaProvider {
	public static void callbackRegister(IWailaRegistrar registrar) {
		IWailaDataProvider nodeDynamo = new NodeDynamoHUDHandler();
		registrar.registerBodyProvider(nodeDynamo, BlockNodeDynamo.class);
		registrar.registerNBTProvider(nodeDynamo, TileNodeDynamo.class);
		
		IWailaDataProvider essentiaDynamo = new EssentiaDynamoHUDHandler();
		registrar.registerBodyProvider(essentiaDynamo, BlockEssentiaDynamo.class);
		registrar.registerNBTProvider(essentiaDynamo, TileEssentiaDynamo.class);
		
		registrar.registerBodyProvider(new BiomeMorpherHUDHandler(), BlockBiomeMorpher.class);
	
    }
}
