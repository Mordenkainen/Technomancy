package theflogat.technomancy.lib.compat;

import thaumicenergistics.api.IThETransportPermissions;
import thaumicenergistics.api.ThEApi;
import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.common.tiles.thaumcraft.dynamos.TileEssentiaDynamo;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileCondenser;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEldritchConsumer;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaFusor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileFluxLamp;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileTCProcessor;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileCreativeJar;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileEssentiaContainer;
import theflogat.technomancy.lib.TMConfig;

public class ThaumicEnergistics implements IModModule {

    private static ThaumicEnergistics thaumicEnergistics;

    public static ThaumicEnergistics getInstance() {
        if (thaumicEnergistics == null) {
            thaumicEnergistics = new ThaumicEnergistics();
        }
        return thaumicEnergistics;
    }

    @Override
    public void init() {}

    @Override
    public void postInit() {
        final IThETransportPermissions perms = ThEApi.instance().transportPermissions();

        if (TMConfig.fluxLamp) {
            perms.addAspectContainerTileToInjectPermissions(TileFluxLamp.class, 32);
        }

        if (TMConfig.contEssentia) {
            perms.addAspectContainerTileToBothPermissions(TileEssentiaContainer.class, 640);
            perms.addAspectContainerTileToExtractPermissions(TileCreativeJar.class, 320);
        }

        if (TMConfig.dynEssentia) {
            perms.addAspectContainerTileToInjectPermissions(TileEssentiaDynamo.class, 64);
        }

        if (TMConfig.fusor) {
            perms.addAspectContainerTileToBothPermissions(TileEssentiaFusor.class, 64);
        }

        if (TMConfig.nodeGen) {
            perms.addAspectContainerTileToBothPermissions(TileNodeGenerator.class, 256);
            perms.addAspectContainerTileToBothPermissions(TileFakeAirNG.class, 256);
        }

        if (TMConfig.condenser) {
            perms.addAspectContainerTileToExtractPermissions(TileCondenser.class, 64);
        }

        if (TMConfig.eldrichConsumer) {
            perms.addAspectContainerTileToExtractPermissions(TileEldritchConsumer.class, 0);
        }

        if (TMConfig.processorTC) {
            perms.addAspectContainerTileToInjectPermissions(TileTCProcessor.class, 64);
        }
    }

    @Override
    public void registerItems() {

    }

    @Override
    public void registerBlocks() {

    }

    @Override
    public void registerRecipes() {

    }
}