package theflogat.technomancy.lib.handlers;

import com.google.common.base.Predicate;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.items.technom.ItemProcessedOre;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.compat.ThermalExpansion;
import theflogat.technomancy.util.Ore;

public class CraftingHandler {

	public static IRecipe itemBoost;
	public static IRecipe coilCoupler;

	public static void initFurnaceRecipes() {
		for (Ore ore : Ore.ores) {
			if (ore.getEnabled()) {
				for(int i = 0; i < ItemProcessedOre.MAXSTAGE; i++) {
					FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ore.getPure(), 1, i), new ItemStack(ore.ingot().getItem(), ore.getIngotsPerStage(i), ore.ingot().getItemDamage()), 1.0F);
				}
			}
		}
	}

	public static void initTechnomancyRecipes() {

		if(Ids.itemBoost)
			GameRegistry.addShapedRecipe(TMItems.itemBoost.getRegistryName(), null, new ItemStack(TMItems.itemBoost), new Object[]{
				" R ",
				"RNR",
				" G ",
				'R', Items.REDSTONE, 'N', Items.QUARTZ, 'G', Items.GOLD_INGOT
			});

		if(Ids.crystalBlock){
			GameRegistry.addShapedRecipe(TMBlocks.crystalBlock.getRegistryName(), new ResourceLocation("crystalblock"), new ItemStack(TMBlocks.crystalBlock, 3, 0), new Object[]{
				"GR ",
				"RG ",
				'G', Items.GLOWSTONE_DUST, 'R', "dyeBlack"
			});
			GameRegistry.addShapedRecipe(TMBlocks.crystalBlock.getRegistryName(), new ResourceLocation("crystalblock"), new ItemStack(TMBlocks.crystalBlock, 3, 1), new Object[]{
				"GR ",
				"RG ",
				'G', Items.GLOWSTONE_DUST, 'R', "dyeWhite"
			});
			GameRegistry.addShapedRecipe(TMBlocks.crystalBlock.getRegistryName(), new ResourceLocation("crystalblock"), new ItemStack(TMBlocks.crystalBlock, 3, 2), new Object[]{
				"GR ",
				"RG ",
				'G', Items.GLOWSTONE_DUST, 'R', "dyeRed"
			});
			GameRegistry.addShapedRecipe(TMBlocks.crystalBlock.getRegistryName(), new ResourceLocation("crystalblock"), new ItemStack(TMBlocks.crystalBlock, 3, 3), new Object[]{
				"GR ",
				"RG ",
				'G', Items.GLOWSTONE_DUST, 'R', "dyeGreen"
			});
			GameRegistry.addShapedRecipe(TMBlocks.crystalBlock.getRegistryName(), new ResourceLocation("crystalblock"), new ItemStack(TMBlocks.crystalBlock, 3, 4), new Object[]{
				"GR ",
				"RG ",
				'G', Items.GLOWSTONE_DUST, 'R', "dyeBlue"
			});
		}
		if(Ids.catalyst){
			GameRegistry.addShapedRecipe(TMBlocks.catalyst.getRegistryName(), null, new ItemStack(TMBlocks.catalyst, 1, 0), new Object[]{
				"BRA",
				"RGR",
				"ARB",
				'G', Blocks.GOLD_BLOCK, 'R', "dyeBlack", 'B', Blocks.OBSIDIAN, 'A', Items.ENDER_PEARL
			});
			GameRegistry.addShapedRecipe(TMBlocks.catalyst.getRegistryName(), null, new ItemStack(TMBlocks.catalyst, 1, 1), new Object[]{
				"BRA",
				"RGR",
				"ARB",
				'G', Blocks.GOLD_BLOCK, 'R', "dyeWhite", 'B', Items.GOLDEN_APPLE, 'A', Items.GOLDEN_CARROT
			});
			GameRegistry.addShapedRecipe(TMBlocks.catalyst.getRegistryName(), null, new ItemStack(TMBlocks.catalyst, 1, 2), new Object[]{
				"BRB",
				"RGR",
				"BRB",
				'G', Blocks.GOLD_BLOCK, 'R', "dyeRed", 'B', Items.BLAZE_ROD
			});
			GameRegistry.addShapedRecipe(TMBlocks.catalyst.getRegistryName(), null, new ItemStack(TMBlocks.catalyst, 1, 4), new Object[]{
				"ARB",
				"RGR",
				"BRA",
				'G', Blocks.GOLD_BLOCK, 'R', "dyeBlue", 'B', Blocks.CLAY, 'A', Items.FISH
			});
			GameRegistry.addShapedRecipe(TMBlocks.catalyst.getRegistryName(), null, new ItemStack(TMBlocks.catalyst, 1, 3), new Object[]{
				"BRB",
				"RGR",
				"BRB",
				'G', Blocks.GOLD_BLOCK, 'R', "dyeGreen", 'B', Blocks.GRASS
			});
		}

		/**
		if(Ids.coilCoupler){
			GameRegistry.addShapedRecipe(TMItems.coilCoupler.getRegistryName(), null, new ItemStack(TMItems.coilCoupler, 1), new Object[]{
				"IRG",
				"SRR",
				"S  ",
				'G', Items.GLOWSTONE_DUST, 'R', Items.REDSTONE, 'S', Items.STICK, 'I', Items.IRON_INGOT
			});
		}
		 */

		if(Ids.itemTransmitter){
			GameRegistry.addShapedRecipe(TMBlocks.itemTransmitter.getRegistryName(), null, new ItemStack(TMBlocks.itemTransmitter, 1), new Object[]{
				"III",
				"ISI",
				"ERE",
				'R', Items.ENDER_PEARL, 'S', Items.STICK, 'I', Items.IRON_INGOT, 'E', Items.REDSTONE
			});
		}
		if(Ids.ritualTome){
			GameRegistry.addShapedRecipe(TMItems.ritualTome.getRegistryName(), null, new ItemStack(TMItems.ritualTome, 1), new Object[]{
				"RI ",
				"IS ",
				"   ",
				'R', Items.BOOK, 'S', "dyeBlack", 'I', Items.GLOWSTONE_DUST
			});
		}
		if(Ids.exGem){
			GameRegistry.addShapedRecipe(TMItems.exGem.getRegistryName(), null, new ItemStack(TMItems.exGem, 1, 100), new Object[]{
				" N ",
				"NEN",
				" N ",
				'E', Items.EMERALD, 'N', Items.GOLD_NUGGET
			});
			if(Ids.existenceBurner){
				GameRegistry.addShapedRecipe(TMBlocks.existenceBurner.getRegistryName(), null, new ItemStack(TMBlocks.existenceBurner, 1), new Object[]{
					"E  ",
					"A  ",
					"   ",
					'E', TMItems.exGem, 'A', Blocks.ANVIL
				});
				if(CompatibilityHandler.te){
					GameRegistry.addShapelessRecipe(TMBlocks.existenceBurner.getRegistryName(), null, new ItemStack(TMBlocks.existenceBurner, 1, 1), Ingredient.fromStacks(
						new ItemStack(TMBlocks.existenceBurner), ThermalExpansion.frameMachineBasic, ThermalExpansion.powerCoilSilver
					));
				} else {
					GameRegistry.addShapelessRecipe(TMBlocks.existenceBurner.getRegistryName(), null, new ItemStack(TMBlocks.existenceBurner, 1, 1), Ingredient.fromStacks(
						new ItemStack(TMBlocks.existenceBurner), new ItemStack(Items.REDSTONE), new ItemStack(Blocks.PISTON)
					));
				}
			}
			if(Ids.existenceUser){
				GameRegistry.addShapedRecipe(TMBlocks.existenceUser.getRegistryName(), null, new ItemStack(TMBlocks.existenceUser, 1), new Object[]{
					" A ",
					"BGB",
					" A ",
					'G', TMItems.exGem, 'B', Items.GOLDEN_APPLE, 'A', Items.GOLDEN_CARROT
				});
				GameRegistry.addShapedRecipe(TMBlocks.existenceUser.getRegistryName(), null, new ItemStack(TMBlocks.existenceUser, 1, 1), new Object[]{
					" H ",
					"BGB",
					" H ",
					'G', TMItems.exGem, 'B', Items.GOLDEN_APPLE, 'H', Items.IRON_HOE
				});
				GameRegistry.addShapedRecipe(TMBlocks.existenceUser.getRegistryName(), null, new ItemStack(TMBlocks.existenceUser, 1, 2), new Object[]{
					"OFO",
					"FGF",
					"OFO",
					'G', TMItems.exGem, 'O', Blocks.OBSIDIAN, 'F', Items.FLINT_AND_STEEL
				});
			}
			if(Ids.existencePylon){
				GameRegistry.addShapedRecipe(TMBlocks.existencePylon.getRegistryName(), null, new ItemStack(TMBlocks.existencePylon, 1, 0), new Object[] {
					"RRR",
					"RER",
					"RPR",
					'R', Items.REDSTONE, 'E', TMItems.exGem, 'P', Blocks.PISTON
				});
				GameRegistry.addShapelessRecipe(TMBlocks.existencePylon.getRegistryName(), null, new ItemStack(TMBlocks.existencePylon, 1, 1), Ingredient.fromStacks(
					new ItemStack(Items.DIAMOND), new ItemStack(TMItems.itemBoost), new ItemStack(TMItems.exGem), new ItemStack(TMBlocks.existencePylon, 1, 0)
				));
				GameRegistry.addShapelessRecipe(TMBlocks.existencePylon.getRegistryName(), null, new ItemStack(TMBlocks.existencePylon, 1, 2), Ingredient.fromStacks(
					new ItemStack(Items.ENDER_PEARL), new ItemStack(Items.DIAMOND), new ItemStack(TMItems.exGem), new ItemStack(TMBlocks.existencePylon, 1, 1)
				));
			}
		}
	}
}