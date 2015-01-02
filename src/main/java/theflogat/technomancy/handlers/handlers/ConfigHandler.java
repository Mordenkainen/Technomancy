package theflogat.technomancy.handlers.handlers;

import java.io.File;

import theflogat.technomancy.lib.Conf;
import theflogat.technomancy.lib.Rate;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.Names;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

    public static void init(File file) {
        Configuration config = new Configuration(file);

        config.load();
        String blocks = "Blocks";
        //Blocks
        Ids.dynNode = config.get(blocks, Names.nodeDynamo, true).getBoolean();
        Ids.contEssentia = config.get(blocks, Names.essentiaContainer, true).getBoolean();
        Ids.cosmeticOpaque = config.get(blocks, Names.cosmeticOpaque, true).getBoolean();
        Ids.idCOSMETIC_PANE = config.get(blocks, Names.cosmeticPane, true).getBoolean();
        Ids.dynEssentia = config.get(blocks, Names.essentiaDynamo, true).getBoolean();
        Ids.biomeMorpher = config.get(blocks, Names.biomeMorpher, true).getBoolean();
        Ids.nodeGen = config.get(blocks, Names.nodeGenerator, true).getBoolean();
        Ids.fluxLamp = config.get(blocks, Names.fluxLamp, true).getBoolean();
        Ids.wirelessCoil = config.get(blocks, Names.teslaCoil, true).getBoolean();
        Ids.electricBellows = config.get(blocks, Names.electricBellows, true).getBoolean();
        Ids.creativeJar = config.get(blocks, Names.creativeJar, true).getBoolean();
        Ids.crystalBlock = config.get(blocks, Names.crystalBlock, true).getBoolean();
        Ids.reconstructor = config.get(blocks, Names.reconstructor, true).getBoolean();
        Ids.bloodDynamo = config.get(blocks, Names.bloodDynamo, true).getBoolean();
        Ids.condenser = config.get(blocks, Names.condenserBlock, true).getBoolean();
        Ids.bloodFabricator = config.get(blocks, Names.bloodFabricator, true).getBoolean();
        Ids.flowerDyn = config.get(blocks, Names.flowerDynamo, true).getBoolean();
        Ids.manaFab = config.get(blocks, Names.manaFabricator, true).getBoolean();
        Ids.processorTC = config.get(blocks, Names.processor + "TC", true).getBoolean();
        Ids.processorBM = config.get(blocks, Names.processor + "BM", true).getBoolean();
        Ids.processorBO = config.get(blocks, Names.processor + "BO", true).getBoolean();
        Ids.eldrichConsumer = config.get(blocks, Names.eldritchConsumer, true).getBoolean();
        
        String items = "Items";
        //Items        
        Ids.essentiaCannon = config.get(items, Names.essentiaCannon, true).getBoolean();
        Ids.itemMaterial = config.get(items, Names.itemMaterial, true).getBoolean();
        Ids.pen = config.get(items, Names.pen, true).getBoolean();
        Ids.wandCores = config.get(items, Names.wandCores, true).getBoolean();
        Ids.focusFusion = config.get(items, Names.fusionFocus, true).getBoolean();
        Ids.matBM = config.get(items, Names.itemBM, true).getBoolean();
        Ids.matBO = config.get(items, Names.itemBO, true).getBoolean();
        Ids.procIron = config.get(items, Names.pureIron, true).getBoolean();
        Ids.procGold = config.get(items, Names.pureGold, true).getBoolean();
        Ids.procCopp = config.get(items, Names.pureCopper, true).getBoolean();
        Ids.procTin = config.get(items, Names.pureTin, true).getBoolean();
        Ids.procSilver = config.get(items, Names.pureSilver, true).getBoolean();
        Ids.procLead = config.get(items, Names.pureLead, true).getBoolean();
        Ids.procNickel = config.get(items, Names.pureNickel, true).getBoolean();

        //Recipe Whatnots
        Conf.bonus = config.get("Recipes", "Add/Increase Smelting bonus to dusts/ore", Conf.bonus).getBoolean(Conf.bonus);
        
        //Render effects
        Conf.fancy = config.get("Renderers", "CoilFX", Conf.fancy).getBoolean(Conf.fancy);
        Conf.fancy = false;//Forced to be off
        
        //Reconstructor stuff
        Conf.blacklist = config.get("Machines", "Blacklisted materials for use in the Essentia Reconstructor", Conf.blacklist).getIntList();
        
       	Conf.debug = config.get("Misc", "DebugFunction", Conf.debug).getBoolean(Conf.debug);

       	//RF Costs
       	Rate.modifyRate(config);
       	
        config.save();

    }

}
