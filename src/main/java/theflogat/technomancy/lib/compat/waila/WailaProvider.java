package theflogat.technomancy.lib.compat.waila;

import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.common.tiles.base.TileCoilTransmitter;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBloodFabricator;
import theflogat.technomancy.common.tiles.botania.machines.TileManaExchanger;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileBiomeMorpher;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEldritchConsumer;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileFluxLamp;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaProvider {
	public static void callbackRegister(IWailaRegistrar registrar) {
		IWailaDataProvider dynamoHUD = new DynamoHUDHandler();
		registrar.registerBodyProvider(dynamoHUD, TileDynamoBase.class);
		
		IWailaDataProvider biomeMorpher = new BiomeMorpherHUDHandler();
		registrar.registerBodyProvider(biomeMorpher, TileBiomeMorpher.class);
		registrar.registerNBTProvider(biomeMorpher, TileBiomeMorpher.class);
	
		IWailaDataProvider nodeGenerator = new NodeGeneratorHUDHandler();
		registrar.registerBodyProvider(nodeGenerator, TileNodeGenerator.class);
		registrar.registerNBTProvider(nodeGenerator, TileNodeGenerator.class);
		
		IWailaDataProvider nodeGeneratorAir = new FakeAirNGHUDHandler();
		registrar.registerBodyProvider(nodeGeneratorAir, TileFakeAirNG.class);
		registrar.registerNBTProvider(nodeGeneratorAir, TileFakeAirNG.class);
		registrar.registerStackProvider(nodeGeneratorAir, TileFakeAirNG.class);
		
		registrar.registerBodyProvider(new EldritchConsumerHUDHandler(), TileEldritchConsumer.class);
		
		registrar.registerBodyProvider(new BloodFabricatorHUDHandler(), TileBloodFabricator.class);
		
		registrar.registerBodyProvider(new FluxLampHUDHandler(), TileFluxLamp.class);
		
		registrar.registerBodyProvider(new CatalystHUDHandler(), TileCatalyst.class);
		
		IWailaDataProvider processor = new ProcessorHUDHandler();
		registrar.registerBodyProvider(processor, TileProcessorBase.class);
		
		IWailaDataProvider manaExchanger = new ManaExchangerHUDHandler();
		registrar.registerBodyProvider(manaExchanger, TileManaExchanger.class);
		registrar.registerNBTProvider(manaExchanger, TileManaExchanger.class);
		
		registrar.registerBodyProvider(new CoilTransmitterHUDHandler(), TileCoilTransmitter.class);
    }
}
