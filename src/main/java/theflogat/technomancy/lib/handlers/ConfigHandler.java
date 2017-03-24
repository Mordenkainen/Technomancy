package theflogat.technomancy.lib.handlers;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;

import theflogat.technomancy.lib.TMConfig;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.util.Ore;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

    public static Configuration config;

    public static void init(File file) {

        config = new Configuration(file);

        config.load();
        String blocks = "Blocks";
        // Blocks
        TMConfig.dynNode = config.get(blocks, Names.NODEDYNAMO, true).getBoolean();
        TMConfig.contEssentia = config.get(blocks, Names.ESSENTIACONTAINER, true).getBoolean();
        TMConfig.cosmeticOpaque = config.get(blocks, Names.COSMETICOPAQUE, true).getBoolean();
        TMConfig.idcosmeticpane = config.get(blocks, Names.COSMETICPANE, true).getBoolean();
        TMConfig.dynEssentia = config.get(blocks, Names.ESSENTIADYNAMO, true).getBoolean();
        TMConfig.biomeMorpher = config.get(blocks, Names.BIOMEMORPHER, true).getBoolean();
        TMConfig.nodeGen = config.get(blocks, Names.NODEGENERATOR, true).getBoolean();
        TMConfig.fluxLamp = config.get(blocks, Names.FLUXLAMP, true).getBoolean();
        TMConfig.wirelessCoil = config.get(blocks, Names.ESSENTIATRANS, true).getBoolean();
        TMConfig.electricBellows = config.get(blocks, Names.ELECTRICBELLOWS, true).getBoolean();
        TMConfig.creativeJar = config.get(blocks, Names.CREATIVEJAR, true).getBoolean();
        TMConfig.crystalBlock = config.get(blocks, Names.CRYSTALBLOCK, true).getBoolean();
        // Ids.reconstructor = config.get(blocks, Names.reconstructor,
        // true).getBoolean();
        TMConfig.bloodDynamo = config.get(blocks, Names.BLOODDYNAMO, true).getBoolean();
        TMConfig.condenser = config.get(blocks, Names.CONDENSERBLOCK, true).getBoolean();
        TMConfig.bloodFabricator = config.get(blocks, Names.BLOODFABRICATOR, true).getBoolean();
        TMConfig.flowerDyn = config.get(blocks, Names.FLOWERDYNAMO, true).getBoolean();
        TMConfig.manaFab = config.get(blocks, Names.MANAFABRICATOR, true).getBoolean();
        TMConfig.processorTC = config.get(blocks, Names.PROCESSOR + "TC", true).getBoolean();
        TMConfig.processorBM = config.get(blocks, Names.PROCESSOR + "BM", true).getBoolean();
        TMConfig.processorBO = config.get(blocks, Names.PROCESSOR + "BO", true).getBoolean();
        TMConfig.eldrichConsumer = config.get(blocks, Names.ELDRITCHCONSUMER, true).getBoolean();
        TMConfig.catalyst = config.get(blocks, Names.CATALYST, true).getBoolean();
        TMConfig.reservoir = config.get(blocks, Names.RESERVOIR, true).getBoolean();
        TMConfig.advDeconTable = config.get(blocks, Names.ADVDECONTABLE, true).getBoolean();
        TMConfig.manaFluid = config.get(blocks, Names.MANAFLUID, true).getBoolean();
        TMConfig.manaExchanger = config.get(blocks, Names.MANAEXCHANGER, true).getBoolean();
        TMConfig.itemTransmitter = config.get(blocks, Names.ITEMTRANSMITTER, true).getBoolean();
        TMConfig.basalt = config.get(blocks, Names.BASALT, true).getBoolean();
        TMConfig.fusor = config.get(blocks, Names.FUSOR, true).getBoolean();
        TMConfig.existenceFountain = config.get(blocks, Names.EXISTENCEFOUNTAIN, true).getBoolean();
        TMConfig.existenceBurner = config.get(blocks, Names.EXISTENCEBURNER[0], true).getBoolean();
        TMConfig.existencePylon = config.get(blocks, Names.EXISTENCEPYLON, true).getBoolean();
        TMConfig.existenceUser = config.get(blocks, "existenceUser", true).getBoolean();

        String items = "Items";
        // Items
        TMConfig.essentiaCannon = config.get(items, Names.ESSENTIACANNON, true).getBoolean();
        TMConfig.itemMaterial = config.get(items, Names.ITEMMATERIAL, true).getBoolean();
        TMConfig.pen = config.get(items, Names.PEN, true).getBoolean();
        TMConfig.wandCores = config.get(items, Names.WANDCORES, true).getBoolean();
        TMConfig.focusFusion = config.get(items, Names.FUSIONFOCUS, true).getBoolean();
        TMConfig.matBM = config.get(items, Names.ITEMBM, true).getBoolean();
        TMConfig.matBO = config.get(items, Names.ITEMBO, true).getBoolean();
        TMConfig.itemBoost = config.get(items, Names.ITEMBOOST, true).getBoolean();
        TMConfig.ritualTome = config.get(items, Names.RITUALTOME, true).getBoolean();
        TMConfig.coilCoupler = config.get(items, Names.COILCOUPLER, true).getBoolean();
        TMConfig.scepter = config.get(items, Names.SCEPTER, true).getBoolean();
        TMConfig.exGem = config.get(items, Names.EXGEM, true).getBoolean();
        TMConfig.treasures = config.get(items, "treasures", true).getBoolean();
        TMConfig.treasures &= config.get(items, "treasureSafeguard", false, "Enhanced villagers will only be enabled if this option AND the treasures option are true. " + "Note, some of the villagers can cause large scale damage, you have been warned!").getBoolean();

        // Potions
        String potions = "Potions";
        TMConfig.drown = config.get(potions, Names.DROWN, 82).getInt(82);
        TMConfig.slowFall = config.get(potions, Names.SLOWFALL, 83).getInt(83);

        // HUD
        TMConfig.hudStartX = config.get("HUD", "StartX", 16).getInt(16);
        TMConfig.hudStartY = config.get("HUD", "StartY", 88).getInt(88);
        TMConfig.showHUD = config.get("HUD", "ShowHUD", TMConfig.showHUD).getBoolean(TMConfig.showHUD);

        // Recipe Whatnots
        TMConfig.bonus = config.get("Recipes", "Add/Increase Smelting bonus to dusts/ore", TMConfig.bonus).getBoolean(TMConfig.bonus);

        // Render effects
        TMConfig.fancy = config.get("Renderers", "CoilFX", TMConfig.fancy).getBoolean(TMConfig.fancy);

        // Reconstructor stuff
        TMConfig.blacklist = config.get("Machines", "Blacklisted materials for use in the Essentia Reconstructor", TMConfig.blacklist).getIntList();

        TMConfig.debug = config.get("Misc", "DebugFunction", TMConfig.debug).getBoolean(TMConfig.debug);
        TMConfig.mkfirst = config.get("Misc", "Mekanism Recipes Will Override TE Ones", TMConfig.mkfirst).getBoolean();

        // RF Costs
        Rate.modifyRate(config);

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void initOreConfigs() {
        for (Ore ore : Ore.ORES) {
            String name = "pure" + ore.oreName().substring(3);
            ore.setEnabled(config.get("PureOres", name, true).getBoolean(true));
            ore.setName(config.get("PureOres", name + "Name", ore.name()).getString());
            ore.setIngotsPerStage(Arrays.asList(config.get("PureOres", name + "Ingots", "2,3,4,5,6,7").getString().split("\\s*,\\s*")));
        }

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void initColorConfigs() {
        for (Ore ore : Ore.ORES) {
            String name = "pure" + ore.oreName().substring(3);
            ore.setColor(Color.decode(config.get("PureOres", name + "Color", "0x" + Integer.toHexString(ore.color())).getString()));
        }

        if (config.hasChanged()) {
            config.save();
        }
    }
}
