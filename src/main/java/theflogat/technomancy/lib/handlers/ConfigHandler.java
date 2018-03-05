package theflogat.technomancy.lib.handlers;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;

import theflogat.technomancy.lib.Conf;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.util.Ore;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	public static Configuration config;
	
    public static void init(File file) {
    	
        config = new Configuration(file);

        config.load();
        String blocks = "Blocks";
        //Blocks
        //Ids.dynNode = config.get(blocks, Names.nodeDynamo, true).getBoolean();
        //Ids.contEssentia = config.get(blocks, Names.essentiaContainer, true).getBoolean();
        //Ids.cosmeticOpaque = config.get(blocks, Names.cosmeticOpaque, true).getBoolean();
        //Ids.idCOSMETIC_PANE = config.get(blocks, Names.cosmeticPane, true).getBoolean();
        //Ids.dynEssentia = config.get(blocks, Names.essentiaDynamo, true).getBoolean();
        //Ids.biomeMorpher = config.get(blocks, Names.biomeMorpher, true).getBoolean();
        //Ids.nodeGen = config.get(blocks, Names.nodeGenerator, true).getBoolean();
        //Ids.fluxLamp = config.get(blocks, Names.fluxLamp, true).getBoolean();
        Ids.wirelessCoil = config.get(blocks, Names.essentiaTransmitter, true).getBoolean();
        //Ids.electricBellows = config.get(blocks, Names.electricBellows, true).getBoolean();
        //Ids.creativeJar = config.get(blocks, Names.creativeJar, true).getBoolean();
        Ids.crystalBlock = config.get(blocks, Names.crystalBlock, true).getBoolean();
//      Ids.reconstructor = config.get(blocks, Names.reconstructor, true).getBoolean();
        Ids.bloodDynamo = config.get(blocks, Names.bloodDynamo, true).getBoolean();
        Ids.condenser = config.get(blocks, Names.condenserBlock, true).getBoolean();
        Ids.bloodFabricator = config.get(blocks, Names.bloodFabricator, true).getBoolean();
        Ids.flowerDyn = config.get(blocks, Names.flowerDynamo, true).getBoolean();
        Ids.manaFab = config.get(blocks, Names.manaFabricator, true).getBoolean();
        Ids.processorTC = config.get(blocks, Names.processor + "TC", true).getBoolean();
        Ids.processorBM = config.get(blocks, Names.processor + "BM", true).getBoolean();
        Ids.processorBO = config.get(blocks, Names.processor + "BO", true).getBoolean();
        Ids.eldrichConsumer = config.get(blocks, Names.eldritchConsumer, true).getBoolean();
        Ids.catalyst = config.get(blocks, Names.catalyst, true).getBoolean();
        Ids.reservoir = config.get(blocks, Names.reservoir, true).getBoolean();
        //Ids.advDeconTable = config.get(blocks, Names.advDeconTable, true).getBoolean();
        Ids.manaFluid = config.get(blocks, Names.manaFluid, true).getBoolean();
        Ids.manaExchanger = config.get(blocks, Names.manaExchanger, true).getBoolean();
        Ids.itemTransmitter = config.get(blocks, Names.itemTransmitter, true).getBoolean();
        Ids.basalt = config.get(blocks, Names.basalt, true).getBoolean();
        Ids.fusor = config.get(blocks, Names.fusor, true).getBoolean();
        Ids.existenceFountain = config.get(blocks, Names.existenceFountain, true).getBoolean();
        Ids.existenceBurner = config.get(blocks, Names.existenceBurner[0], true).getBoolean();
        Ids.existencePylon = config.get(blocks, Names.existencePylon, true).getBoolean();
        Ids.existenceUser = config.get(blocks, "existenceUser", true).getBoolean();
        
        String items = "Items";
        //Items        
        //Ids.essentiaCannon = config.get(items, Names.essentiaCannon, true).getBoolean();
        Ids.itemMaterial = config.get(items, Names.itemMaterial, true).getBoolean();
        //Ids.pen = config.get(items, Names.pen, true).getBoolean();
        Ids.wandCores = config.get(items, Names.wandCores, true).getBoolean();
        Ids.focusFusion = config.get(items, Names.fusionFocus, true).getBoolean();
        Ids.matBM = config.get(items, Names.itemBM, true).getBoolean();
        Ids.matBO = config.get(items, Names.itemBO, true).getBoolean();
        Ids.itemBoost = config.get(items, Names.itemBoost, true).getBoolean();
        Ids.ritualTome = config.get(items, Names.ritualTome, true).getBoolean();
        Ids.coilCoupler = config.get(items, Names.coilCoupler, true).getBoolean();
        Ids.scepter = config.get(items, Names.scepter, true).getBoolean();
        Ids.exGem = config.get(items, Names.exGem, true).getBoolean();
        Ids.treasures = config.get(items, "treasures", true).getBoolean();
        Ids.treasures &= config.get(items, "treasureSafeguard", false, 
        		"Enhanced villagers will only be enabled if this option AND the treasures option are true. "
        		+ "Note, some of the villagers can cause large scale damage, you have been warned!").getBoolean();
        
        //Potions
        String potions = "Potions";
        Ids.drown = config.get(potions, Names.drown, 82).getInt(82);
        Ids.slowFall = config.get(potions, Names.slowFall, 83).getInt(83);
        
        //HUD
        Ids.hudStartX = config.get("HUD", "StartX", 16).getInt(16);
        Ids.hudStartY = config.get("HUD", "StartY", 88).getInt(88);
        Conf.showHUD = config.get("HUD", "ShowHUD", Conf.showHUD).getBoolean(Conf.showHUD);
        
        //Recipe Whatnots
        Conf.bonus = config.get("Recipes", "Add/Increase Smelting bonus to dusts/ore", Conf.bonus).getBoolean(Conf.bonus);
        
        //Render effects
        Conf.fancy = config.get("Renderers", "CoilFX", Conf.fancy).getBoolean(Conf.fancy);
        
        //Reconstructor stuff
        Conf.blacklist = config.get("Machines", "Blacklisted materials for use in the Essentia Reconstructor", Conf.blacklist).getIntList();
        
       	Conf.debug = config.get("Misc", "DebugFunction", Conf.debug).getBoolean(Conf.debug);
       	Conf.mkfirst = config.get("Misc", "Mekanism Recipes Will Override TE Ones", Conf.mkfirst).getBoolean();
       	
       	//RF Costs
       	Rate.modifyRate(config);
       	
       	if (config.hasChanged()) {
       		config.save();
       	}
    }

    public static void initOreConfigs() {
		for (Ore ore : Ore.ores) {
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
		for (Ore ore : Ore.ores) {
			String name = "pure" + ore.oreName().substring(3);
			ore.setColor(Color.decode(config.get("PureOres", name + "Color", "0x" + Integer.toHexString(ore.color())).getString()));
		}
		
		if (config.hasChanged()) {
			config.save();
		}
	}
}
