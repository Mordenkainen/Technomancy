package democretes.blocks;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import democretes.blocks.dynamos.BlockBloodDynamo;
import democretes.blocks.dynamos.BlockEssentiaDynamo;
import democretes.blocks.dynamos.BlockFlowerDynamo;
import democretes.blocks.dynamos.BlockNodeDynamo;
import democretes.blocks.dynamos.tiles.TileBloodDynamo;
import democretes.blocks.dynamos.tiles.TileEssentiaDynamo;
import democretes.blocks.dynamos.tiles.TileFlowerDynamo;
import democretes.blocks.dynamos.tiles.TileNodeDynamo;
import democretes.blocks.machines.BlockBiomeMorpher;
import democretes.blocks.machines.BlockBloodFabricator;
import democretes.blocks.machines.BlockCondenser;
import democretes.blocks.machines.BlockEldritchConsumer;
import democretes.blocks.machines.BlockElectricBellows;
import democretes.blocks.machines.BlockFluxLamp;
import democretes.blocks.machines.BlockManaFabricator;
import democretes.blocks.machines.BlockNodeGenerator;
import democretes.blocks.machines.BlockProcessor;
import democretes.blocks.machines.BlockReconstructor;
import democretes.blocks.machines.BlockTeslaCoil;
import democretes.blocks.machines.tiles.TileBMProcessor;
import democretes.blocks.machines.tiles.TileBOProcessor;
import democretes.blocks.machines.tiles.TileBiomeMorpher;
import democretes.blocks.machines.tiles.TileBloodFabricator;
import democretes.blocks.machines.tiles.TileCondenser;
import democretes.blocks.machines.tiles.TileEldritchConsumer;
import democretes.blocks.machines.tiles.TileElectricBellows;
import democretes.blocks.machines.tiles.TileFluxLamp;
import democretes.blocks.machines.tiles.TileManaFabricator;
import democretes.blocks.machines.tiles.TileNodeGenerator;
import democretes.blocks.machines.tiles.TileReconstructor;
import democretes.blocks.machines.tiles.TileTCProcessor;
import democretes.blocks.machines.tiles.TileTeslaCoil;
import democretes.blocks.storage.BlockCreativeJar;
import democretes.blocks.storage.BlockEssentiaContainer;
import democretes.blocks.storage.TileCreativeJar;
import democretes.blocks.storage.TileEssentiaContainer;
import democretes.lib.Ids;
import democretes.lib.Names;

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
	
	public static void initThaumcraft() {
		//Block Initializaton
		nodeDynamo = new BlockNodeDynamo(Ids.dynNode);
		essentiaContainer = new BlockEssentiaContainer(Ids.contEssentia);
		cosmeticOpaque = new BlockCosmeticOpaque(Ids.cosmeticOpaque);
		essentiaDynamo = new BlockEssentiaDynamo(Ids.dynEssentia);
		biomeMorpher = new BlockBiomeMorpher(Ids.biomeMorpher);
		nodeGenerator = new BlockNodeGenerator(Ids.nodeGen);
		fluxLamp = new BlockFluxLamp(Ids.fluxLamp);
		teslaCoil = new BlockTeslaCoil(Ids.wirelessCoil);
		electricBellows = new BlockElectricBellows(Ids.electricBellows);
		creativeJar = new BlockCreativeJar(Ids.creativeJar);
		crystalBlock = new BlockCrystal(Ids.crystalBlock);
		reconstructorBlock = new BlockReconstructor(Ids.reconstructor);
		condenserBlock = new BlockCondenser(Ids.condenser);
		processorTC = new BlockProcessor.BlockTCProcessor(Ids.processorTC);
		eldritchConsumer = new BlockEldritchConsumer(Ids.eldrichConsumer);
		
		//Registry
		GameRegistry.registerBlock(nodeDynamo, Names.nodeDynamo);
		GameRegistry.registerBlock(essentiaContainer, Names.essentiaContainer);
		GameRegistry.registerBlock(cosmeticOpaque, Names.cosmeticOpaque);
		GameRegistry.registerBlock(essentiaDynamo, Names.essentiaDynamo);
		GameRegistry.registerBlock(biomeMorpher, Names.biomeMorpher);
		GameRegistry.registerBlock(nodeGenerator, Names.nodeGenerator);
		GameRegistry.registerBlock(fluxLamp, Names.fluxLamp);
		GameRegistry.registerBlock(teslaCoil, Names.teslaCoil);
		GameRegistry.registerBlock(electricBellows, Names.electricBellows);
		GameRegistry.registerBlock(creativeJar, Names.creativeJar);
		GameRegistry.registerBlock(crystalBlock, Names.crystalBlock);
		GameRegistry.registerBlock(reconstructorBlock, Names.reconstructor);
		GameRegistry.registerBlock(condenserBlock, Names.condenserBlock);
		GameRegistry.registerBlock(processorTC, Names.processor + "TC");
		GameRegistry.registerBlock(eldritchConsumer, Names.eldritchConsumer);

		
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
		bloodDynamo = new BlockBloodDynamo(Ids.bloodDynamo);	
		bloodFabricator = new BlockBloodFabricator(Ids.bloodFabricator);
		processorBM = new BlockProcessor.BlockBMProcessor(Ids.processorBM);

		//Registry
		GameRegistry.registerBlock(bloodDynamo, Names.bloodDynamo);
		GameRegistry.registerBlock(bloodFabricator, Names.bloodFabricator);
		GameRegistry.registerBlock(processorBM, Names.processor + "BM");

		//Tiles registry
		GameRegistry.registerTileEntity(TileBloodDynamo.class, "TileBloodDynamo");
		GameRegistry.registerTileEntity(TileBloodFabricator.class, "TileBloodFabricator");
		GameRegistry.registerTileEntity(TileBMProcessor.class, "TileProcessorBM");

	}
	
	public static void initBotania() {
		//Block Initialization
		flowerDynamo = new BlockFlowerDynamo(Ids.flowerDyn);
		manaFabricator = new BlockManaFabricator(Ids.manaFab);
		processorBO = new BlockProcessor.BlockBOProcessor(Ids.processorBO);
		
		//Block Registry
		GameRegistry.registerBlock(flowerDynamo, Names.flowerDynamo);
		GameRegistry.registerBlock(manaFabricator, Names.manaFabricator);
		GameRegistry.registerBlock(processorBO, Names.processor + "BO");

		//Tile Registry
		GameRegistry.registerTileEntity(TileFlowerDynamo.class, "TileFlowerDynamo");
		GameRegistry.registerTileEntity(TileManaFabricator.class, "ManaFabricator");
		GameRegistry.registerTileEntity(TileBOProcessor.class, "TileProcessorBO");

	}
	


}
