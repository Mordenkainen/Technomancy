package theflogat.technomancy.common.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import theflogat.technomancy.api.rituals.RitualRegistry;
import theflogat.technomancy.api.tiles.MovableTileRegistry;
import theflogat.technomancy.common.blocks.air.BlockFakeAirLight;
import theflogat.technomancy.common.blocks.botania.BlockManaFluid;
import theflogat.technomancy.common.blocks.technom.BlockBasalt;
import theflogat.technomancy.common.blocks.technom.BlockCatalyst;
import theflogat.technomancy.common.blocks.technom.BlockCrystal;
import theflogat.technomancy.common.blocks.technom.BlockItemTransmitter;
import theflogat.technomancy.common.blocks.technom.existence.BlockExistenceBurner;
import theflogat.technomancy.common.blocks.technom.existence.BlockExistenceUser;
import theflogat.technomancy.common.blocks.technom.existence.BlockExistenceFountain;
import theflogat.technomancy.common.blocks.technom.existence.BlockExistencePylon;
import theflogat.technomancy.common.items.base.ItemAdvancedBase;
import theflogat.technomancy.common.items.botania.ManaFluid;
import theflogat.technomancy.common.items.technom.ItemCatalyst;
import theflogat.technomancy.common.items.technom.ItemCrystal;
import theflogat.technomancy.common.items.technom.existence.ItemBlockExistenceBurner;
import theflogat.technomancy.common.items.technom.existence.ItemBlockExistencePylon;
import theflogat.technomancy.common.items.technom.existence.ItemBlockExistenceUser;
import theflogat.technomancy.common.rituals.b.RitualBlackHoleT1;
import theflogat.technomancy.common.rituals.b.RitualBlackHoleT2;
import theflogat.technomancy.common.rituals.b.RitualBlackHoleT3;
import theflogat.technomancy.common.rituals.b.RitualFountainExistence;
import theflogat.technomancy.common.rituals.e.RitualCaveInT1;
import theflogat.technomancy.common.rituals.e.RitualCaveInT2;
import theflogat.technomancy.common.rituals.e.RitualCaveInT3;
import theflogat.technomancy.common.rituals.e.RitualExtraction;
import theflogat.technomancy.common.rituals.f.RitualOfFireT1;
import theflogat.technomancy.common.rituals.f.RitualOfFireT2;
import theflogat.technomancy.common.rituals.l.RitualPurificationT1;
import theflogat.technomancy.common.rituals.l.RitualPurificationT2;
import theflogat.technomancy.common.rituals.l.RitualPurificationT3;
import theflogat.technomancy.common.rituals.w.RitualWaterT1;
import theflogat.technomancy.common.rituals.w.RitualWaterT2;
import theflogat.technomancy.common.rituals.w.RitualWaterT3;
import theflogat.technomancy.common.tiles.air.TileFakeAirCore;
import theflogat.technomancy.common.tiles.base.TileSelector;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.common.tiles.technom.TileCrystal;
import theflogat.technomancy.common.tiles.technom.TileItemTransmitter;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceBurner;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceCropAccelerator;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceDynamicBurner;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceFountain;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceHarvester;
import theflogat.technomancy.common.tiles.technom.existence.TileExistencePylon;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceSealingDevice;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class TMBlocks {

	public static Block crystalBlock = Ids.crystalBlock ? new BlockCrystal() : null;
	public static Block bloodDynamo;
	public static Block bloodFabricator;
	public static Block flowerDynamo;
	public static Block manaFabricator;
	public static Block processorBM;
	public static Block processorBO;
	public static Block catalyst = Ids.catalyst ? new BlockCatalyst() : null;
	public static Block fakeAirLight = Ids.catalyst ? new BlockFakeAirLight() : null;
	public static Block manaExchanger;
	public static Block itemTransmitter = Ids.itemTransmitter ? new BlockItemTransmitter() : null;
	public static Block basalt = Ids.basalt ? new BlockBasalt() : null;
	public static Block	fountainExistence = Ids.existenceFountain ? new BlockExistenceFountain() : null;
	public static Block	existenceBurner = Ids.existenceBurner ? new BlockExistenceBurner() : null;
	public static Block existencePylon = Ids.existencePylon ? new BlockExistencePylon() : null;
	public static Block existenceUser = Ids.existenceUser ? new BlockExistenceUser() : null;

	public static Fluid manaFluid = new ManaFluid();
	public static BlockManaFluid manaFluidBlock;

	public static void initTechnomancy(){
		registerBlock(crystalBlock);
		registerBlock(catalyst);
		registerBlock(fakeAirLight, Names.fakeAirLight);
		registerBlock(itemTransmitter, Names.itemTransmitter);
		registerBlock(basalt, Names.basalt);
		registerBlock(fountainExistence, Names.existenceFountain);
		registerBlock(existenceBurner);
		registerBlock(existencePylon);
		registerBlock(existenceUser);
		
		OreDictionary.registerOre("basalt", basalt);

		GameRegistry.registerTileEntity(TileCrystal.class, Ref.MOD_PREFIX + "TileCrystal");
		GameRegistry.registerTileEntity(TileCatalyst.class, Ref.MOD_PREFIX + "TileCatalyst");
		GameRegistry.registerTileEntity(TileFakeAirCore.class, Ref.MOD_PREFIX + "TileFakeAirCore");
		GameRegistry.registerTileEntity(TileItemTransmitter.class, Ref.MOD_PREFIX + "TileItemTransmitter");
		GameRegistry.registerTileEntity(TileExistenceFountain.class, Ref.MOD_PREFIX + "TileExistenceFountain");
		GameRegistry.registerTileEntity(TileExistenceBurner.class, Ref.MOD_PREFIX + "TileExistenceBurner");
		GameRegistry.registerTileEntity(TileExistencePylon.class, Ref.MOD_PREFIX + "TileExistencePylon");
		GameRegistry.registerTileEntity(TileExistenceDynamicBurner.class, Ref.MOD_PREFIX + "TileExistenceDynamicBurner");
		GameRegistry.registerTileEntity(TileExistenceCropAccelerator.class, Ref.MOD_PREFIX + "TileExistenceCropAccelerator");
		GameRegistry.registerTileEntity(TileExistenceHarvester.class, Ref.MOD_PREFIX + "TileExistenceHarvester");
		GameRegistry.registerTileEntity(TileExistenceSealingDevice.class, Ref.MOD_PREFIX + "TileExistenceSealingDevice");
		GameRegistry.registerTileEntity(TileSelector.class, Ref.MOD_PREFIX + "TileSelector");

		
		MovableTileRegistry.addAllowed(TileCrystal.class);
		MovableTileRegistry.addAllowed(TileCatalyst.class);
		

		RitualRegistry.add(new RitualExtraction());
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
		RitualRegistry.add(new RitualOfFireT2());
		RitualRegistry.add(new RitualOfFireT1());
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenders() {
		regCrystal();
		regRenders();
		regBlock(basalt);
		regBlock(fountainExistence);
		regBlock(itemTransmitter);
		regBlock(fakeAirLight);
	}
	
	public static void registerBlock(Block block, String name) {
		if (block instanceof BlockContainerAdvanced) {
			ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
			ForgeRegistries.ITEMS.register(new ItemAdvancedBase(block).setRegistryName(name));
			return;
		}
		if (block != null) {
			ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
			ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
	}
	
	public static void registerBlock(Block block) {
		if(block!=null) {
			ForgeRegistries.BLOCKS.register(block);
			if(block instanceof BlockCatalyst) {
				ForgeRegistries.ITEMS.register(new ItemCatalyst(block).setRegistryName(block.getRegistryName()).setHasSubtypes(true));
			}else if(block instanceof BlockCrystal) {
				ForgeRegistries.ITEMS.register(new ItemCrystal(block).setRegistryName(block.getRegistryName()).setHasSubtypes(true));
			}else if(block instanceof BlockExistenceBurner) {
				ForgeRegistries.ITEMS.register(new ItemBlockExistenceBurner(block).setRegistryName(block.getRegistryName()));
			}else if(block instanceof BlockExistencePylon) {
				ForgeRegistries.ITEMS.register(new ItemBlockExistencePylon(block).setRegistryName(block.getRegistryName()));
			}else if(block instanceof BlockExistenceUser) {
				ForgeRegistries.ITEMS.register(new ItemBlockExistenceUser(block).setRegistryName(block.getRegistryName()));
			}else {
				ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
			}
		}
	}

	public static void regBlock(Block block) {
		if(block != null) {
			ModelResourceLocation res = new
					ModelResourceLocation(block.getRegistryName().toString(), "inventory");
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, res);
		}
	}

	public static void regCrystal() {
		for(BlockCrystal.Types type : BlockCrystal.Types.values()) {
			ModelResourceLocation res = new
					ModelResourceLocation(crystalBlock.getRegistryName().toString() + type.ordinal(), "inventory");
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(crystalBlock), type.ordinal(), res);
		}

		for(BlockCatalyst.Types type : BlockCatalyst.Types.values()) {
			ModelResourceLocation res = new
					ModelResourceLocation(catalyst.getRegistryName().toString() + type.ordinal(), "inventory");
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(catalyst), type.ordinal(), res);
		}
	}

	public static void regRenders() {
		for (int i = 0; i < 3; i++) {
			ModelResourceLocation res = new
					ModelResourceLocation(existencePylon.getRegistryName().toString(), "type=" + i);
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(existencePylon), i, res);
		}

		for (int i = 0; i < 2; i++) {
			ModelResourceLocation res = new
					ModelResourceLocation(existenceBurner.getRegistryName().toString(), "type=" + i);
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(existenceBurner), i, res);
		}

		for (int i = 0; i < Names.existenceUser.length; i++) {
			ModelResourceLocation res = new
					ModelResourceLocation(existenceUser.getRegistryName().toString() + i, "inventory");
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(existenceUser), i, res);
		}
	}
}
