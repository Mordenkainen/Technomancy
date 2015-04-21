package theflogat.technomancy.lib.compat;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.blocks.botania.BlockManaFluid;
import theflogat.technomancy.common.blocks.botania.dynamos.BlockFlowerDynamo;
import theflogat.technomancy.common.blocks.botania.machines.BlockBOProcessor;
import theflogat.technomancy.common.blocks.botania.machines.BlockManaExchanger;
import theflogat.technomancy.common.blocks.botania.machines.BlockManaFabricator;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.items.botania.ItemBOMaterial;
import theflogat.technomancy.common.items.botania.ItemFlowerDynamo;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class Botania extends ModuleBase {
	private static Botania instance;
	
	private static RecipeManaInfusion manaCoilRec;
	private static IRecipe manaGear;
	private static IRecipe flowerDynamo;
	private static IRecipe manaFabricator;
	private static IRecipe processorBO;
	private static IRecipe manaExchanger;
	
	private Botania() {}
	
	public static Botania getInstance() {
		if(instance == null) {
			instance = new Botania();
		}
		return instance;
	}
	
	public static void initBotaniaLexicon() {
		if(Ids.matBO) {
			TechnoLexicon manaCoilLex = new TechnoLexicon("tc.research_name.TECHNOBASICS", BotaniaAPI.categoryDevices);
			manaCoilLex.setLexiconPages(BotaniaAPI.internalHandler.textPage("techno.research_page.TECHNOBASICS.1"),
					BotaniaAPI.internalHandler.manaInfusionRecipePage("Mana Coil", manaCoilRec),
					BotaniaAPI.internalHandler.craftingRecipePage("Manasteel Gear", manaGear));
		}
		if(Ids.flowerDyn) {
			TechnoLexicon flowerDynLex = new TechnoLexicon("tc.research_name.DYNAMO", BotaniaAPI.categoryDevices);
			flowerDynLex.setLexiconPages(BotaniaAPI.internalHandler.textPage("techno.lexicon_page.FLOWERDYNAMO.1"),
					BotaniaAPI.internalHandler.textPage("techno.lexicon_page.FLOWERDYNAMO.2"),
					BotaniaAPI.internalHandler.textPage("techno.lexicon_page.FLOWERDYNAMO.3"),
					BotaniaAPI.internalHandler.craftingRecipePage("Hippie Dynamo", flowerDynamo));
			if(Ids.itemBoost) {
				flowerDynLex.addPage(BotaniaAPI.internalHandler.textPage("techno.lexicon_page.FLOWERDYNAMO.4"));
				flowerDynLex.addPage(BotaniaAPI.internalHandler.craftingRecipePage("Potency Gem", CraftingHandler.itemBoost));
			}
		}
		if(Ids.manaFab) {
			TechnoLexicon manaFabLex = new TechnoLexicon("techno.lexicon_name.MANAFAB", BotaniaAPI.categoryDevices);
			manaFabLex.setLexiconPages(BotaniaAPI.internalHandler.textPage("techno.lexicon_page.MANAFAB.1"),
					BotaniaAPI.internalHandler.craftingRecipePage("Mana Fabricator", manaFabricator));
		}
		if(Ids.processorBO) {
			TechnoLexicon processorBOLex = new TechnoLexicon("techno.lexicon_name.PROCESSORBO", BotaniaAPI.categoryDevices);
			processorBOLex.setLexiconPages(BotaniaAPI.internalHandler.textPage("techno.lexicon_page.PROCESSORBO.1"),
					BotaniaAPI.internalHandler.textPage("techno.lexicon_page.PROCESSORBO.2"),
					BotaniaAPI.internalHandler.craftingRecipePage("Botanical Purifier", processorBO));
		}
		if(Ids.manaExchanger) {
			TechnoLexicon manaExchangerLex = new TechnoLexicon("techno.lexicon_name.MANAEXCHANGER", BotaniaAPI.categoryDevices);
			manaExchangerLex.setLexiconPages(BotaniaAPI.internalHandler.textPage("techno.lexicon_page.MANAEXCHANGER.1"),
					BotaniaAPI.internalHandler.textPage("techno.lexicon_page.MANAEXCHANGER.2"),
					BotaniaAPI.internalHandler.craftingRecipePage("Mana Exchanger", manaExchanger));
		}
	}
	
	public static void sparkle(World world, double d, double d1, double f, Random r){
		BotaniaAPI.internalHandler.sparkleFX( world, d, d1, f, r.nextFloat(), r.nextFloat(), 1.0F, r.nextFloat() * 4, 10);
	}

	@Override
	public void Init() {}

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
    	registerItem(TMItems.itemBO, Names.itemBO);
    	registerItem(TMItems.manaBucket, Names.manaBucket);
	}

	@Override
	public void RegisterBlocks() {
		TMBlocks.manaFluid = Ids.manaFluid ? new ManaFluid() : null;
		TMBlocks.manaFluidBlock = Ids.manaFluid ? new BlockManaFluid() : null;
		TMBlocks.flowerDynamo = Ids.flowerDyn ? new BlockFlowerDynamo() : null;
		TMBlocks.manaFabricator = Ids.manaFab ? new BlockManaFabricator() : null;
		TMBlocks.processorBO = Ids.processorBO ? new BlockBOProcessor() : null;
		TMBlocks.manaExchanger = Ids.manaExchanger ? new BlockManaExchanger() : null;
		
		registerBlock(TMBlocks.flowerDynamo, Names.flowerDynamo, ItemFlowerDynamo.class);
		registerBlock(TMBlocks.manaFabricator, Names.manaFabricator);
		registerBlock(TMBlocks.processorBO, Names.processor + "BO");
		registerBlock(TMBlocks.manaFluidBlock, Names.manaFluidBlock);
		registerBlock(TMBlocks.manaExchanger, Names.manaExchanger, ItemManaExchanger.class);
		
		registerTileEntity(TMBlocks.flowerDynamo, TileFlowerDynamo.class, "TileFlowerDynamo");
		registerTileEntity(TMBlocks.manaFabricator, TileManaFabricator.class, "ManaFabricator");
		registerTileEntity(TMBlocks.processorBO, TileBOProcessor.class, "TileProcessorBO");
		registerTileEntity(TMBlocks.manaExchanger, TileManaExchanger.class, "TileManaExchanger");
		
		registerBucket(TMBlocks.manaFluid, TMBlocks.manaFluidBlock, TMItems.manaBucket);
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
					'W', new ItemStack(Items.redstone),
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
					'P', ThermalExpansion.frameTesseractFull			});
			}
			if(Ids.processorBO) {
				processorBO = oreDictRecipe(new ItemStack(TMBlocks.processorBO),
						new Object[] {" A ", "BMB", "ICI",
					'M', ThermalExpansion.frameMachineBasic,
					'I', "ingotManasteel",
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'B', "livingrock",
					'A', new ItemStack(Items.redstone)				});
			}
			if(Ids.manaExchanger) {
				manaExchanger = oreDictRecipe(new ItemStack(TMBlocks.manaExchanger),
						new Object[] {" A ", "BMB", "ICI",
					'M', ThermalExpansion.frameMachineBasic,
					'I', new ItemStack(ThermalExpansion.blockTank, 1, 3),
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'B', "livingrock",
					'A', new ItemStack(GameRegistry.findItem("Botania", "pool"))				});
			}
		} else {
			//ManaInfusion
			if(Ids.matBO) {
				manaCoilRec = BotaniaAPI.registerManaInfusionRecipe(new ItemStack(TMItems.itemBO, 1, 0), new ItemStack(Items.redstone), 3000);
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
					'W', new ItemStack(Items.redstone),
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
					'P', new ItemStack(Items.ender_eye, 1, 0)			});
			}
			if(Ids.processorBO) {
				processorBO = oreDictRecipe(new ItemStack(TMBlocks.processorBO),
						new Object[] {" A ", "BMB", "ICI",
					'M', new ItemStack(Items.redstone),
					'I', "ingotManasteel",
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'B', "livingrock",
					'A', new ItemStack(Items.redstone)				});
			}
			if(Ids.manaExchanger) {
				manaExchanger = oreDictRecipe(new ItemStack(TMBlocks.manaExchanger),
						new Object[] {"IAI", "BMB", "ICI",
					'M', new ItemStack(Item.getItemFromBlock(Blocks.glass)),
					'I', "ingotManasteel",
					'C', new ItemStack(TMItems.itemBO, 1, 0),
					'B', "livingrock",
					'A', new ItemStack(GameRegistry.findItem("Botania", "pool"))				});
			}
		}
	}
}
