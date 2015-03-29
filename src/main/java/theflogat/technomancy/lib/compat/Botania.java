package theflogat.technomancy.lib.compat;

import java.util.Random;

import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.compat.botania.TechnoLexicon;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import theflogat.technomancy.lib.handlers.CraftingHandler;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class Botania {
	private static RecipeManaInfusion manaCoilRec;
	private static IRecipe manaGear;
	private static IRecipe flowerDynamo;
	private static IRecipe manaFabricator;
	private static IRecipe processorBO;
	
	public static void initBotaniaRecipes() {
		if(CompatibilityHandler.te){
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
		}else{
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
		}
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
					BotaniaAPI.internalHandler.craftingRecipePage("Flower Dynamo", flowerDynamo));
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
	}
	
	@SuppressWarnings("unchecked")
	private static IRecipe oreDictRecipe(ItemStack res, Object[] params) {
		IRecipe rec = new ShapedOreRecipe(res, params);
		CraftingManager.getInstance().getRecipeList().add(rec);
		return rec;
	}
	
	public static void sparkle(World world, double d, double d1, double f, Random r){
		BotaniaAPI.internalHandler.sparkleFX( world, d, d1, f, r.nextFloat(), r.nextFloat(), 1.0F, r.nextFloat() * 4, 10);
	}
}
