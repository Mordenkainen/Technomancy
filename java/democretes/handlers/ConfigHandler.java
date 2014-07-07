package democretes.handlers;

import java.io.File;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import democretes.lib.Ids;
import democretes.lib.Names;

public class ConfigHandler {
	
	public static boolean fancy = true;
	public static int[] blacklist = {};
	public static boolean bonus = true;

    public static void init(File file) {
        Configuration config = new Configuration(file);

        config.load();
        
        //Blocks
        Ids.dynNode = config.getBlock(Names.nodeDynamo, Ids.dynNodeDef).getInt();
        Ids.contEssentia = config.getBlock(Names.essentiaContainer, Ids.contEssetiaDef).getInt();
        Ids.cosmeticOpaque = config.getBlock(Names.cosmeticOpaque, Ids.cosmeticOpaqueDef).getInt();
        Ids.idCOSMETIC_PANE = config.getBlock(Names.cosmeticPane, Ids.cosmeticPaneDef).getInt();
        Ids.dynEssentia = config.getBlock(Names.essentiaDynamo, Ids.dynEssentiaDef).getInt();
        Ids.biomeMorpher = config.getBlock(Names.biomeMorpher, Ids.biomeMorpherDef).getInt();
        Ids.nodeGen = config.getBlock(Names.nodeGenerator, Ids.nodeGenDef).getInt();
        Ids.fluxLamp = config.getBlock(Names.fluxLamp, Ids.lampFluxDef).getInt();
        Ids.wirelessCoil = config.getBlock(Names.teslaCoil, Ids.wirelessCoilDef).getInt();
        Ids.electricBellows = config.getBlock(Names.electricBellows, Ids.electricBellowsDef).getInt();
        Ids.creativeJar = config.getBlock(Names.creativeJar, Ids.creativeJarDef).getInt();
        Ids.crystalBlock = config.getBlock(Names.crystalBlock, Ids.blockCrystalDef).getInt();
        Ids.reconstructor = config.getBlock(Names.reconstructor, Ids.reconstructorDef).getInt();
        Ids.bloodDynamo = config.getBlock(Names.bloodDynamo, Ids.bloodDynDef).getInt();
        Ids.condenser = config.getBlock(Names.condenserBlock, Ids.condenserDef).getInt();
        Ids.bloodFabricator = config.getBlock(Names.bloodFabricator, Ids.bloodFabicatorDef).getInt();
        Ids.flowerDyn = config.getBlock(Names.flowerDynamo, Ids.flowerDynDef).getInt();
        Ids.manaFab = config.getBlock(Names.manaFabricator, Ids.manaFabDef).getInt();
        Ids.processorTC = config.getBlock(Names.processor + "TC", Ids.processorTCDef).getInt();
        Ids.processorBM = config.getBlock(Names.processor + "BM", Ids.processorBMDef).getInt();
        Ids.processorBO = config.getBlock(Names.processor + "BO", Ids.processorBODef).getInt();
        Ids.eldrichConsumer = config.getBlock("Eldrich Consumer", Ids.eldrichConsumerDef).getInt();

        //Items        
        Ids.essentiaCannon = config.getItem(Names.essentiaCannon, Ids.essentiaCannonDef).getInt();
        Ids.itemMaterial = config.getItem(Names.itemMaterial, Ids.itemMaterialDef).getInt();
        Ids.pen = config.getItem(Names.pen, Ids.penDef).getInt();
        Ids.wandCores = config.getItem(Names.wandCores, Ids.wandCoresDef).getInt();
        Ids.focusFusion = config.getItem(Names.fusionFocus, Ids.focusFusionDef).getInt();
        Ids.matBM = config.getItem(Names.itemBM, Ids.matBMDef).getInt();
        Ids.matBO = config.getItem(Names.itemBO, Ids.matBODef).getInt();
        Ids.procIron = config.getItem(Names.pureIron, Ids.procIronDef).getInt();
        Ids.procGold = config.getItem(Names.pureGold, Ids.procGoldDef).getInt();
        Ids.procCopp = config.getItem(Names.pureCopper, Ids.procCoppDef).getInt();
        Ids.procTin = config.getItem(Names.pureTin, Ids.procTinDef).getInt();
        Ids.procSilver = config.getItem(Names.pureSilver, Ids.procSilverDef).getInt();
        Ids.procLead = config.getItem(Names.pureLead, Ids.procLeadDef).getInt();
        Ids.procNickel = config.getItem(Names.pureNickel, Ids.procNickelDef).getInt();

        //Recipe Whatnots
        Property smelting = config.get("Recipes", "Add/Increase Smelting bonus to dusts/ore", bonus);
        bonus = smelting.getBoolean(true);
        
        //Render effects
        Property coilfx = config.get("Renderers", "CoilFX", fancy);
        fancy = coilfx.getBoolean(true);
        
        //Reconstructor stuff
        Property reconstructor = config.get("Machines", "Blacklisted materials for use in the Essentia Reconstructor", blacklist);
        blacklist = reconstructor.getIntList();

        config.save();

    }

}
