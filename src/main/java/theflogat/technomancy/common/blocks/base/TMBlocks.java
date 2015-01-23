package theflogat.technomancy.common.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import theflogat.technomancy.common.blocks.dynamos.BlockBloodDynamo;
import theflogat.technomancy.common.blocks.dynamos.BlockEssentiaDynamo;
import theflogat.technomancy.common.blocks.dynamos.BlockFlowerDynamo;
import theflogat.technomancy.common.blocks.dynamos.BlockNodeDynamo;
import theflogat.technomancy.common.blocks.dynamos.ItemBloodDynamo;
import theflogat.technomancy.common.blocks.dynamos.ItemEssentiaDynamo;
import theflogat.technomancy.common.blocks.dynamos.ItemFlowerDynamo;
import theflogat.technomancy.common.blocks.dynamos.ItemNodeDynamo;
import theflogat.technomancy.common.blocks.machines.BlockBiomeMorpher;
import theflogat.technomancy.common.blocks.machines.BlockBloodFabricator;
import theflogat.technomancy.common.blocks.machines.BlockCondenser;
import theflogat.technomancy.common.blocks.machines.BlockEldritchConsumer;
import theflogat.technomancy.common.blocks.machines.BlockElectricBellows;
import theflogat.technomancy.common.blocks.machines.BlockFluxLamp;
import theflogat.technomancy.common.blocks.machines.BlockManaFabricator;
import theflogat.technomancy.common.blocks.machines.BlockNodeGenerator;
import theflogat.technomancy.common.blocks.machines.BlockProcessor;
import theflogat.technomancy.common.blocks.machines.BlockReconstructor;
import theflogat.technomancy.common.blocks.machines.BlockTeslaCoil;
import theflogat.technomancy.common.blocks.storage.BlockCreativeJar;
import theflogat.technomancy.common.blocks.storage.BlockEssentiaContainer;
import theflogat.technomancy.common.blocks.technom.BlockCrystal;
import theflogat.technomancy.common.tiles.TileBMProcessor;
import theflogat.technomancy.common.tiles.TileBOProcessor;
import theflogat.technomancy.common.tiles.dynamos.TileBloodDynamo;
import theflogat.technomancy.common.tiles.dynamos.TileEssentiaDynamo;
import theflogat.technomancy.common.tiles.dynamos.TileFlowerDynamo;
import theflogat.technomancy.common.tiles.dynamos.TileNodeDynamo;
import theflogat.technomancy.common.tiles.technom.TileCrystal;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileBiomeMorpher;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileBloodFabricator;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileCondenser;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEldritchConsumer;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileElectricBellows;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileFluxLamp;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileManaFabricator;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileReconstructor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileTCProcessor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileTeslaCoil;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileCreativeJar;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileEssentiaContainer;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.compat.CoFH;
import cpw.mods.fml.common.registry.GameRegistry;

public class TMBlocks {
	
	
	//Block Instances	
	public static Block nodeDynamo;
	public static Block essentiaContainer;
	public static Block cosmeticOpaque;
	public static Block cosmeticPane;
	public static Block essentiaDynamo;
	public static Block biomeMorpher;
	public static Block nodeGenerator;
	public static Block fluxLamp;
	public static Block teslaCoil;
	public static Block electricBellows;
	public static Block creativeJar;
	public static Block crystalBlock;	
	public static Block reconstructorBlock;
	public static Block bloodDynamo;
	public static Block condenserBlock;
	public static Block bloodFabricator;
	public static Block flowerDynamo;
	public static Block manaFabricator;
	public static Block processorTC;
	public static Block processorBM;
	public static Block processorBO;
	public static Block eldritchConsumer;
	
	public static void initTechnomancy(){
		crystalBlock = Ids.crystalBlock ? new BlockCrystal() : null;

		registerBlock(crystalBlock, Names.crystalBlock);
		
		GameRegistry.registerTileEntity(TileCrystal.class, Ref.MOD_PREFIX + "TileCrystal");
	}
	
	public static void initThaumcraft() {
		//Block Initializaton
		nodeDynamo = Ids.dynNode ? new BlockNodeDynamo() : null;
		essentiaContainer = Ids.contEssentia ? new BlockEssentiaContainer() : null;
		cosmeticOpaque = Ids.cosmeticOpaque ? new BlockCosmeticOpaque() : null;
		essentiaDynamo = Ids.dynEssentia ? new BlockEssentiaDynamo() : null;
		biomeMorpher = Ids.biomeMorpher ? new BlockBiomeMorpher() : null;
		nodeGenerator = Ids.nodeGen ? new BlockNodeGenerator() : null;
		fluxLamp = Ids.fluxLamp ? new BlockFluxLamp() : null;
		teslaCoil = Ids.wirelessCoil ? new BlockTeslaCoil() : null;
		electricBellows = Ids.electricBellows ? new BlockElectricBellows() : null;
		creativeJar = Ids.creativeJar ? new BlockCreativeJar() : null;
		reconstructorBlock = Ids.reconstructor ? new BlockReconstructor() : null;
		condenserBlock = Ids.condenser && CoFH.co ? new BlockCondenser() : null;
		processorTC = Ids.processorTC ? new BlockProcessor.BlockTCProcessor() : null;
		eldritchConsumer = Ids.eldrichConsumer ? new BlockEldritchConsumer() : null;
		
		//Registry
		registerBlock(nodeDynamo, Names.nodeDynamo, ItemNodeDynamo.class);
		registerBlock(essentiaContainer, Names.essentiaContainer);
		registerBlock(cosmeticOpaque, Names.cosmeticOpaque);
		registerBlock(essentiaDynamo, Names.essentiaDynamo, ItemEssentiaDynamo.class);
		registerBlock(biomeMorpher, Names.biomeMorpher);
		registerBlock(nodeGenerator, Names.nodeGenerator);
		registerBlock(fluxLamp, Names.fluxLamp);
		registerBlock(teslaCoil, Names.teslaCoil);
		registerBlock(electricBellows, Names.electricBellows);
		registerBlock(creativeJar, Names.creativeJar);
		registerBlock(reconstructorBlock, Names.reconstructor);
		registerBlock(condenserBlock, Names.condenserBlock);
		registerBlock(processorTC, Names.processor + "TC");
		registerBlock(eldritchConsumer, Names.eldritchConsumer);

		
		//Tiles registry
		GameRegistry.registerTileEntity(TileEssentiaContainer.class, "TileEssentiacontainer");
		GameRegistry.registerTileEntity(TileNodeDynamo.class, "TileNodeDynamo");
		GameRegistry.registerTileEntity(TileEssentiaDynamo.class, "TileEssentiaDynamo");
		GameRegistry.registerTileEntity(TileBiomeMorpher.class, "TileBiomeMorpher");
		GameRegistry.registerTileEntity(TileNodeGenerator.class, "TileNodeGenerator");
		GameRegistry.registerTileEntity(TileFluxLamp.class, "TileFluxLamp");
		GameRegistry.registerTileEntity(TileTeslaCoil.class, "TileTeslaCoil");
		GameRegistry.registerTileEntity(TileElectricBellows.class, "TileElectricBellows");
		GameRegistry.registerTileEntity(TileCreativeJar.class, "TileCreativeJar");
		GameRegistry.registerTileEntity(TileReconstructor.class, "TileReconstructor");
		GameRegistry.registerTileEntity(TileCondenser.class, "TileCondenser");
		GameRegistry.registerTileEntity(TileTCProcessor.class, "TileProcessorTC");
		GameRegistry.registerTileEntity(TileEldritchConsumer.class, "TileEldrichConsumer");
	}
	
	public static void initBloodMagic() {
		//Block Initialization
		bloodDynamo = Ids.bloodDynamo ? new BlockBloodDynamo() : null;	
		bloodFabricator = Ids.bloodFabricator ? new BlockBloodFabricator() : null;
		processorBM = Ids.processorBM ? new BlockProcessor.BlockBMProcessor() : null;

		//Registry
		registerBlock(bloodDynamo, Names.bloodDynamo, ItemBloodDynamo.class);
		registerBlock(bloodFabricator, Names.bloodFabricator);
		registerBlock(processorBM, Names.processor + "BM");

		//Tiles registry
		GameRegistry.registerTileEntity(TileBloodDynamo.class, "TileBloodDynamo");
		GameRegistry.registerTileEntity(TileBloodFabricator.class, "TileBloodFabricator");
		GameRegistry.registerTileEntity(TileBMProcessor.class, "TileProcessorBM");

	}
	
	public static void initBotania() {
		//Block Initialization
		flowerDynamo = Ids.flowerDyn ? new BlockFlowerDynamo() : null;
		manaFabricator = Ids.manaFab ? new BlockManaFabricator() : null;
		processorBO = Ids.processorBO ? new BlockProcessor.BlockBOProcessor() : null;
		
		//Block Registry
		registerBlock(flowerDynamo, Names.flowerDynamo, ItemFlowerDynamo.class);
		registerBlock(manaFabricator, Names.manaFabricator);
		registerBlock(processorBO, Names.processor + "BO");

		//Tile Registry
		GameRegistry.registerTileEntity(TileFlowerDynamo.class, "TileFlowerDynamo");
		GameRegistry.registerTileEntity(TileManaFabricator.class, "ManaFabricator");
		GameRegistry.registerTileEntity(TileBOProcessor.class, "TileProcessorBO");

	}
	
	private static void registerBlock(Block block, String name) {
		if(block!=null)
			GameRegistry.registerBlock(block, name);
	}
	
	private static void registerBlock(Block block, String name, Class<? extends ItemBlock> itemclass) {
		if(block!=null)
			GameRegistry.registerBlock(block, itemclass, name);
	}
}
