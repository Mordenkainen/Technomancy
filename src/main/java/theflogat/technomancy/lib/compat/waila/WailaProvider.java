package theflogat.technomancy.lib.compat.waila;

import theflogat.technomancy.common.blocks.dynamos.BlockBloodDynamo;
import theflogat.technomancy.common.blocks.dynamos.BlockEssentiaDynamo;
import theflogat.technomancy.common.blocks.dynamos.BlockFlowerDynamo;
import theflogat.technomancy.common.blocks.dynamos.BlockNodeDynamo;
import theflogat.technomancy.common.blocks.machines.BlockBiomeMorpher;
import theflogat.technomancy.common.blocks.machines.BlockBloodFabricator;
import theflogat.technomancy.common.blocks.machines.BlockEldritchConsumer;
import theflogat.technomancy.common.blocks.machines.BlockFluxLamp;
import theflogat.technomancy.common.blocks.machines.BlockNodeGenerator;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileBiomeMorpher;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaProvider {
	public static void callbackRegister(IWailaRegistrar registrar) {
		IWailaDataProvider dynamoHUD = new DynamoHUDHandler();
		registrar.registerBodyProvider(dynamoHUD, BlockNodeDynamo.class);
		registrar.registerBodyProvider(dynamoHUD, BlockEssentiaDynamo.class);
		registrar.registerBodyProvider(dynamoHUD, BlockBloodDynamo.class);
		registrar.registerBodyProvider(dynamoHUD, BlockFlowerDynamo.class);
		
		IWailaDataProvider biomeMorpher = new BiomeMorpherHUDHandler();
		registrar.registerBodyProvider(biomeMorpher, BlockBiomeMorpher.class);
		registrar.registerNBTProvider(biomeMorpher, TileBiomeMorpher.class);
	
		IWailaDataProvider nodeGenerator = new NodeGeneratorHUDHandler();
		registrar.registerBodyProvider(nodeGenerator, BlockNodeGenerator.class);
		registrar.registerNBTProvider(nodeGenerator, TileNodeGenerator.class);
		
		registrar.registerBodyProvider(new EldritchConsumerHUDHandler(), BlockEldritchConsumer.class);
		
		registrar.registerBodyProvider(new BloodFabricatorHUDHandler(), BlockBloodFabricator.class);
		
		registrar.registerBodyProvider(new FluxLampHUDHandler(), BlockFluxLamp.class);
    }
}
