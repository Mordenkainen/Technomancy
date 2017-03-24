package theflogat.technomancy.lib.compat.waila;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.common.tiles.base.TileCoilTransmitter;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBloodFabricator;
import theflogat.technomancy.common.tiles.botania.machines.TileManaExchanger;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileBiomeMorpher;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileCondenser;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEldritchConsumer;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaFusor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileFluxLamp;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;

public class WailaProvider {

    public static void callbackRegister(final IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new DynamoHUDHandler(), TileDynamoBase.class);

        registrar.registerBodyProvider(new CatalystHUDHandler(), TileCatalyst.class);

        registrar.registerBodyProvider(new ProcessorHUDHandler(), TileProcessorBase.class);

        registrar.registerBodyProvider(new CoilTransmitterHUDHandler(), TileCoilTransmitter.class);

        // registrar.registerBodyProvider(new ExistenceConsumerHUDHandler(),
        // TileExistenceCropAccelerator.class);
        // registrar.registerBodyProvider(new ExistenceProducerHUDHandler(),
        // TileExistenceBurner.class);

        if (CompatibilityHandler.th) {
            final IWailaDataProvider biomeMorpher = new BiomeMorpherHUDHandler();
            registrar.registerBodyProvider(biomeMorpher, TileBiomeMorpher.class);
            registrar.registerNBTProvider(biomeMorpher, TileBiomeMorpher.class);

            final IWailaDataProvider nodeGenerator = new NodeGeneratorHUDHandler();
            registrar.registerBodyProvider(nodeGenerator, TileNodeGenerator.class);
            registrar.registerNBTProvider(nodeGenerator, TileNodeGenerator.class);

            final IWailaDataProvider nodeGeneratorAir = new FakeAirNGHUDHandler();
            registrar.registerBodyProvider(nodeGeneratorAir, TileFakeAirNG.class);
            registrar.registerNBTProvider(nodeGeneratorAir, TileFakeAirNG.class);
            registrar.registerStackProvider(nodeGeneratorAir, TileFakeAirNG.class);

            registrar.registerBodyProvider(new EldritchConsumerHUDHandler(), TileEldritchConsumer.class);

            registrar.registerBodyProvider(new FluxLampHUDHandler(), TileFluxLamp.class);

            registrar.registerBodyProvider(new EssentiaFusorHUDHandler(), TileEssentiaFusor.class);

            registrar.registerBodyProvider(new CondenserHUDHandler(), TileCondenser.class);
        }

        if (CompatibilityHandler.bo) {
            final IWailaDataProvider manaExchanger = new ManaExchangerHUDHandler();
            registrar.registerBodyProvider(manaExchanger, TileManaExchanger.class);
            registrar.registerNBTProvider(manaExchanger, TileManaExchanger.class);
        }

        if (CompatibilityHandler.bm) {
            registrar.registerBodyProvider(new BloodFabricatorHUDHandler(), TileBloodFabricator.class);
        }
    }
}
