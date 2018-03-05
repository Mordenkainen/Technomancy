package theflogat.technomancy.lib.compat;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Loader;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.blocks.botania.BlockManaFluid;
import theflogat.technomancy.common.blocks.botania.dynamos.BlockFlowerDynamo;
import theflogat.technomancy.common.blocks.botania.machines.BlockBOProcessor;
import theflogat.technomancy.common.blocks.botania.machines.BlockManaExchanger;
import theflogat.technomancy.common.blocks.botania.machines.BlockManaFabricator;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.items.botania.ItemBOMaterial;
import theflogat.technomancy.common.items.botania.ItemManaBucket;
import theflogat.technomancy.common.items.botania.ItemManaExchanger;
import theflogat.technomancy.common.items.botania.ManaFluid;
import theflogat.technomancy.common.tiles.botania.dynamos.TileFlowerDynamo;
import theflogat.technomancy.common.tiles.botania.machines.TileBOProcessor;
import theflogat.technomancy.common.tiles.botania.machines.TileManaExchanger;
import theflogat.technomancy.common.tiles.botania.machines.TileManaFabricator;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.compat.botania.TechnoLexicon;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import theflogat.technomancy.lib.handlers.CraftingHandler;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeManaInfusion;

public class Botania extends ModuleBase {
	private static Botania instance;
	
	private static RecipeManaInfusion manaCoilRec;
	private static IRecipe manaGear;
	private static IRecipe flowerDynamo;
	private static IRecipe manaFabricator;
	private static IRecipe processorBO;
	private static IRecipe manaExchanger;
	
	private Botania() {
	}
	
	public static Botania getInstance() {
		if(instance == null) {
			instance = new Botania();
		}
		return instance;
	}
	
	public static void initBotaniaLexicon() {
		if(Ids.matBO && manaGear != null && manaGear.getRegistryName() != null) {
			TechnoLexicon manaCoilLex = new TechnoLexicon("tc.research_name.TECHNOBASICS", BotaniaAPI.categoryDevices);
			manaCoilLex.setLexiconPages(BotaniaAPI.internalHandler.textPage("techno.research_page.TECHNOBASICS.1"),
					BotaniaAPI.internalHandler.manaInfusionRecipePage("Mana Coil", manaCoilRec),
					BotaniaAPI.internalHandler.craftingRecipePage("Manasteel Gear", manaGear.getRegistryName()));
		}
		if(Ids.flowerDyn && flowerDynamo != null && flowerDynamo.getRegistryName() != null) {
			TechnoLexicon flowerDynLex = new TechnoLexicon("tc.research_name.DYNAMO", BotaniaAPI.categoryDevices);
			flowerDynLex.setLexiconPages(BotaniaAPI.internalHandler.textPage("techno.lexicon_page.FLOWERDYNAMO.1"),
					BotaniaAPI.internalHandler.textPage("techno.lexicon_page.FLOWERDYNAMO.2"),
					BotaniaAPI.internalHandler.craftingRecipePage("Hippie Dynamo", flowerDynamo.getRegistryName()));
			if(Ids.itemBoost) {
				flowerDynLex.addPage(BotaniaAPI.internalHandler.textPage("techno.lexicon_page.FLOWERDYNAMO.3"));
				flowerDynLex.addPage(BotaniaAPI.internalHandler.craftingRecipePage("Potency Gem", CraftingHandler.itemBoost.getRegistryName()));
			}
		}
		if(Ids.manaFab && manaFabricator != null && manaFabricator.getRegistryName() != null) {
			TechnoLexicon manaFabLex = new TechnoLexicon("techno.lexicon_name.MANAFAB", BotaniaAPI.categoryDevices);
			manaFabLex.setLexiconPages(BotaniaAPI.internalHandler.textPage("techno.lexicon_page.MANAFAB.1"),
					BotaniaAPI.internalHandler.craftingRecipePage("Mana Fabricator", manaFabricator.getRegistryName()));
		}
		if(Ids.processorBO && processorBO != null && processorBO.getRegistryName() != null) {
			TechnoLexicon processorBOLex = new TechnoLexicon("techno.lexicon_name.PROCESSORBO", BotaniaAPI.categoryDevices);
			processorBOLex.setLexiconPages(BotaniaAPI.internalHandler.textPage("techno.lexicon_page.PROCESSORBO.1"),
					BotaniaAPI.internalHandler.textPage("techno.lexicon_page.PROCESSORBO.2"),
					BotaniaAPI.internalHandler.craftingRecipePage("Botanical Purifier", processorBO.getRegistryName()));
		}
		if(Ids.manaExchanger && manaExchanger != null && manaExchanger.getRegistryName() != null) {
			TechnoLexicon manaExchangerLex = new TechnoLexicon("techno.lexicon_name.MANAEXCHANGER", BotaniaAPI.categoryDevices);
			manaExchangerLex.setLexiconPages(BotaniaAPI.internalHandler.textPage("techno.lexicon_page.MANAEXCHANGER.1"),
					BotaniaAPI.internalHandler.textPage("techno.lexicon_page.MANAEXCHANGER.2"),
					BotaniaAPI.internalHandler.craftingRecipePage("Mana Exchanger", manaExchanger.getRegistryName()));
		}
	}
	
	public static void sparkle(World world, double d, double d1, double f, Random r){
		BotaniaAPI.internalHandler.sparkleFX( world, d, d1, f, r.nextFloat(), r.nextFloat(), 1.0F, r.nextFloat() * 4, 10);
	}

	@Override
	public void preInit() {
		regBlock(TMBlocks.flowerDynamo);
		regBlock(TMBlocks.manaFabricator);
		regBlock(TMBlocks.manaExchanger);
		regBlock(TMBlocks.processorBO);
		regBlock(TMBlocks.manaFluidBlock);
		TMBlocks.manaFluidBlock.regFluid();
	}

	@Override
	public void Init() {
		Technomancy.logger.info("Botania compatibility module loaded.");
	}

	@Override
	public void PostInit() {
		initBotaniaLexicon();
	}

	@Override
	public void RegisterItems() {
		//Initializations
    	TMItems.itemBO = Ids.matBO ? new ItemBOMaterial() : null;
    	TMItems.manaBucket = Ids.manaFluid ? new ItemManaBucket(TMBlocks.manaFluidBlock) : null;
    	
    	//Registration
    	registerItem(TMItems.itemBO);

    	registerBucket(TMBlocks.manaFluid, TMBlocks.manaFluidBlock, FluidUtil.getFilledBucket(new FluidStack(TMBlocks.manaFluid, 1000)).getItem());
	}

	@Override
	public void RegisterBlocks() {
		TMBlocks.manaFluidBlock = new BlockManaFluid(TMBlocks.manaFluid, Material.WATER);
		TMBlocks.flowerDynamo = Ids.flowerDyn ? new BlockFlowerDynamo() : null;
		TMBlocks.manaFabricator = Ids.manaFab ? new BlockManaFabricator() : null;
		TMBlocks.processorBO = Ids.processorBO ? new BlockBOProcessor() : null;
		TMBlocks.manaExchanger = Ids.manaExchanger ? new BlockManaExchanger() : null;
		
		registerBlock(TMBlocks.flowerDynamo, Names.flowerDynamo);
		registerBlock(TMBlocks.manaFabricator, Names.manaFabricator);
		registerBlock(TMBlocks.processorBO, Names.processor + "bo");
		registerBlock(TMBlocks.manaFluidBlock);
		registerBlock(TMBlocks.manaExchanger, Names.manaExchanger, ItemManaExchanger.class);
		
		registerTileEntity(TMBlocks.flowerDynamo, TileFlowerDynamo.class, "TileFlowerDynamo");
		registerTileEntity(TMBlocks.manaFabricator, TileManaFabricator.class, "ManaFabricator");
		registerTileEntity(TMBlocks.processorBO, TileBOProcessor.class, "TileProcessorBO");
		registerTileEntity(TMBlocks.manaExchanger, TileManaExchanger.class, "TileManaExchanger");
	}

	@Override
	public void RegisterRecipes() {
		if(CompatibilityHandler.te) {
			//ManaInfusion
			if(Ids.matBO) {
				manaCoilRec = BotaniaAPI.registerManaInfusionRecipe(new ItemStack(TMItems.itemBO, 1, 0), ThermalExpansion.powerCoilSilver, 3000);
			}

			//Normal Recipes
			if(Ids.matBO) {
				manaGear = oreDictRecipe(new ItemStack(TMItems.itemBO, 1, 1),
						new Object[] {" M ", "MIM", " M ",
					'M', "ingotManasteel",
					'I', "ingotIron"		});
			}
			if(Ids.flowerDyn) {
				flowerDynamo = oreDictRecipe(new ItemStack(TMBlocks.flowerDynamo), 
						new Object[] {" C ", "GIG", "IWI",
					'W', new ItemStack(Items.REDSTONE),
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'G', new ItemStack(TMItems.itemBO, 1, 1),
					'I', "ingotManasteel"				});
			}
			if(Ids.manaFab) {
				manaFabricator = oreDictRecipe(new ItemStack(TMBlocks.manaFabricator), 
						new Object[] {"CDC", "IDI", " P ",
					'C', new ItemStack(TMItems.itemBO, 1, 1),
					'I', "ingotManasteel",
					'D', "manaDiamond",
					'P', Loader.isModLoaded("thermalexpansion") ? ThermalExpansion.frameCellBasic : Blocks.DIAMOND_BLOCK			});
			}
			if(Ids.processorBO) {
				processorBO = oreDictRecipe(new ItemStack(TMBlocks.processorBO),
						new Object[] {" A ", "BMB", "ICI",
					'M', ThermalExpansion.frameMachineBasic,
					'I', "ingotManasteel",
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'B', "livingrock",
					'A', new ItemStack(Items.REDSTONE)				});
			}
			if(Ids.manaExchanger) {
				manaExchanger = oreDictRecipe(new ItemStack(TMBlocks.manaExchanger),
						new Object[] {" A ", "BMB", "ICI",
					'M', ThermalExpansion.frameMachineBasic,
					'I', ThermalExpansion.blockTank[3],
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'B', "livingrock",
					'A', new ItemStack(Block.REGISTRY.getObject(new ResourceLocation("botania:pool")))				});
			}
		} else {
			//ManaInfusion
			if(Ids.matBO) {
				manaCoilRec = BotaniaAPI.registerManaInfusionRecipe(new ItemStack(TMItems.itemBO, 1, 0), new ItemStack(Items.REDSTONE), 3000);
			}

			//Normal Recipes
			if(Ids.matBO) {
				manaGear = oreDictRecipe(new ItemStack(TMItems.itemBO, 1, 1),
						new Object[] {" M ", "MIM", " M ",
					'M', "ingotManasteel",
					'I', "ingotIron"		});
			}
			if(Ids.flowerDyn) {
				flowerDynamo = oreDictRecipe(new ItemStack(TMBlocks.flowerDynamo), 
						new Object[] {" C ", "GIG", "IWI",
					'W', new ItemStack(Items.REDSTONE),
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'G', new ItemStack(TMItems.itemBO, 1, 1),
					'I', "ingotManasteel"				});
			}
			if(Ids.manaFab) {
				manaFabricator = oreDictRecipe(new ItemStack(TMBlocks.manaFabricator), 
						new Object[] {"CDC", "IDI", " P ",
					'C', new ItemStack(TMItems.itemBO, 1, 1),
					'I', "ingotManasteel",
					'D', "manaDiamond",
					'P', new ItemStack(Items.ENDER_EYE, 1, 0)			});
			}
			if(Ids.processorBO) {
				processorBO = oreDictRecipe(new ItemStack(TMBlocks.processorBO),
						new Object[] {" A ", "BMB", "ICI",
					'M', new ItemStack(Items.REDSTONE),
					'I', "ingotManasteel",
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'B', "livingrock",
					'A', new ItemStack(Items.REDSTONE)				});
			}
			if(Ids.manaExchanger) {
				manaExchanger = oreDictRecipe(new ItemStack(TMBlocks.manaExchanger),
						new Object[] {"IAI", "BMB", "ICI",
					'M', new ItemStack(Item.getItemFromBlock(Blocks.GLASS)),
					'I', "ingotManasteel",
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'B', "livingrock",
					'A', new ItemStack(Block.REGISTRY.getObject(new ResourceLocation("botania:pool")))				});
			}
		}
	}
}
