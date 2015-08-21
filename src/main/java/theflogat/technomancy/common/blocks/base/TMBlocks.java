package theflogat.technomancy.common.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;
import theflogat.technomancy.api.rituals.RitualRegistry;
import theflogat.technomancy.api.tiles.MovableTileRegistry;
import theflogat.technomancy.common.blocks.air.BlockFakeAirLight;
import theflogat.technomancy.common.blocks.technom.BlockBasalt;
import theflogat.technomancy.common.blocks.technom.BlockCatalyst;
import theflogat.technomancy.common.blocks.technom.BlockCrystal;
import theflogat.technomancy.common.blocks.technom.BlockItemTransmitter;
import theflogat.technomancy.common.blocks.technom.existence.BlockExistenceBurner;
import theflogat.technomancy.common.blocks.technom.existence.BlockExistenceCropAccelerator;
import theflogat.technomancy.common.blocks.technom.existence.BlockExistenceFountain;
import theflogat.technomancy.common.blocks.technom.existence.BlockExistencePylon;
import theflogat.technomancy.common.items.base.ItemAdvancedBase;
import theflogat.technomancy.common.items.technom.ItemCatalyst;
import theflogat.technomancy.common.items.technom.ItemCrystal;
import theflogat.technomancy.common.items.technom.existence.ItemBlockExistenceBurner;
import theflogat.technomancy.common.items.technom.existence.ItemBlockExistencePylon;
import theflogat.technomancy.common.rituals.b.RitualBlackHoleT1;
import theflogat.technomancy.common.rituals.b.RitualBlackHoleT2;
import theflogat.technomancy.common.rituals.b.RitualBlackHoleT3;
import theflogat.technomancy.common.rituals.b.RitualFountainExistence;
import theflogat.technomancy.common.rituals.e.RitualCaveInT1;
import theflogat.technomancy.common.rituals.e.RitualCaveInT2;
import theflogat.technomancy.common.rituals.e.RitualCaveInT3;
import theflogat.technomancy.common.rituals.f.RitualOfFireT1;
import theflogat.technomancy.common.rituals.f.RitualOfFireT2;
import theflogat.technomancy.common.rituals.l.RitualPurificationT1;
import theflogat.technomancy.common.rituals.l.RitualPurificationT2;
import theflogat.technomancy.common.rituals.l.RitualPurificationT3;
import theflogat.technomancy.common.rituals.w.RitualWaterT1;
import theflogat.technomancy.common.rituals.w.RitualWaterT2;
import theflogat.technomancy.common.rituals.w.RitualWaterT3;
import theflogat.technomancy.common.tiles.air.TileFakeAirCore;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.common.tiles.technom.TileCrystal;
import theflogat.technomancy.common.tiles.technom.TileItemTransmitter;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceBurner;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceCropAccelerator;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceDynamicBurner;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceFountain;
import theflogat.technomancy.common.tiles.technom.existence.TileExistencePylon;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
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
//	public static Block reconstructorBlock;
	public static Block bloodDynamo;
	public static Block condenserBlock;
	public static Block bloodFabricator;
	public static Block flowerDynamo;
	public static Block manaFabricator;
	public static Block processorTC;
	public static Block processorBM;
	public static Block processorBO;
	public static Block eldritchConsumer;
	public static Block catalyst;
	public static Block fakeAirNG;
	public static Block reservoir;
	public static Block fakeAirLight;
	public static Block advDeconTable;
	public static Block manaFluidBlock;
	public static Block manaExchanger;
	public static Block itemTransmitter;
	public static Block basalt;
	public static Block essentiaFusor;
	public static Block	fountainExistence;
	public static Block	existenceBurner;
	public static Block existencePylon;
	public static Block existenceCropAcc;
	
	public static Fluid manaFluid;
	
	public static void initTechnomancy(){
		crystalBlock = Ids.crystalBlock ? new BlockCrystal() : null;
		catalyst = Ids.catalyst ? new BlockCatalyst() : null;
		fakeAirLight = Ids.catalyst ? new BlockFakeAirLight() : null;
		itemTransmitter = Ids.itemTransmitter ? new BlockItemTransmitter() : null;
		basalt = Ids.basalt ? new BlockBasalt() : null;
		fountainExistence = Ids.existenceFountain ? new BlockExistenceFountain() : null;
		existenceBurner = Ids.existenceBurner ? new BlockExistenceBurner() : null;
		existencePylon = Ids.existencePylon ? new BlockExistencePylon() : null;
		existenceCropAcc = Ids.existenceCropAcc ? new BlockExistenceCropAccelerator() : null;
		
		
		registerBlock(crystalBlock, Names.crystalBlock, ItemCrystal.class);
		registerBlock(catalyst, Names.catalyst, ItemCatalyst.class);
		registerBlock(fakeAirLight, Names.fakeAirLight);
		registerBlock(itemTransmitter, Names.itemTransmitter);
		registerBlock(basalt, Names.basalt);
		registerBlock(fountainExistence, Names.existenceFountain);
		registerBlock(existenceBurner, Names.existenceBurner[0], ItemBlockExistenceBurner.class);
		registerBlock(existencePylon, Names.existencePylon, ItemBlockExistencePylon.class);
		registerBlock(existenceCropAcc, Names.existenceCropAcc);
		
		
		OreDictionary.registerOre("basalt", basalt);
		
		
		GameRegistry.registerTileEntity(TileCrystal.class, Ref.MOD_PREFIX + "TileCrystal");
		GameRegistry.registerTileEntity(TileCatalyst.class, Ref.MOD_PREFIX + "TileCatalyst");
		GameRegistry.registerTileEntity(TileFakeAirCore.class, Ref.MOD_PREFIX + "TileFakeAirCore");
		GameRegistry.registerTileEntity(TileItemTransmitter.class, Ref.MOD_PREFIX + "TileItemTransmitter");
		GameRegistry.registerTileEntity(TileExistenceFountain.class, Ref.MOD_PREFIX + "TileExistenceFountain");
		GameRegistry.registerTileEntity(TileExistenceBurner.class, Ref.MOD_PREFIX + "TileExistenceBurner");
		GameRegistry.registerTileEntity(TileExistencePylon.class, Ref.MOD_PREFIX + "TileExistencePylon");
		GameRegistry.registerTileEntity(TileExistenceCropAccelerator.class, Ref.MOD_PREFIX + "TileExistenceCropAccelerator");
		GameRegistry.registerTileEntity(TileExistenceDynamicBurner.class, Ref.MOD_PREFIX + "TileExistenceDynamicBurner");
		
		
		MovableTileRegistry.addAllowed(TileCrystal.class);
		MovableTileRegistry.addAllowed(TileCatalyst.class);
		
		
		RitualRegistry.add(new RitualFountainExistence());
		RitualRegistry.add(new RitualCaveInT3());
		RitualRegistry.add(new RitualCaveInT2());
		RitualRegistry.add(new RitualCaveInT1());
		RitualRegistry.add(new RitualBlackHoleT3());
		RitualRegistry.add(new RitualBlackHoleT2());
		RitualRegistry.add(new RitualBlackHoleT1());
		RitualRegistry.add(new RitualWaterT3());
		RitualRegistry.add(new RitualWaterT2());
		RitualRegistry.add(new RitualWaterT1());
		RitualRegistry.add(new RitualPurificationT3());
		RitualRegistry.add(new RitualPurificationT2());
		RitualRegistry.add(new RitualPurificationT1());
//		RitualRegistry.add(new RitualOfFireT3());
		RitualRegistry.add(new RitualOfFireT2());
		RitualRegistry.add(new RitualOfFireT1());
	}
	
	public static void registerBlock(Block block, String name) {
		if(block instanceof BlockContainerAdvanced){
			GameRegistry.registerBlock(block, ItemAdvancedBase.class, name);
			return;
		}
		if(block!=null)
			GameRegistry.registerBlock(block, name);
	}
	
	public static void registerBlock(Block block, String name, Class<? extends ItemBlock> itemclass) {
		if(block!=null)
			GameRegistry.registerBlock(block, itemclass, name);
	}
}
