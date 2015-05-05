package theflogat.technomancy.lib.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.items.technom.ItemProcessedOre;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.util.Ore;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingHandler {
	public static IRecipe itemBoost;
	public static IRecipe coilCoupler;

	public static void initFurnaceRecipes() {
		for (Ore ore : Ore.ores) {
			if (ore.getEnabled()) {
				for(int i = 0; i < ItemProcessedOre.MAXSTAGE; i++) {
					FurnaceRecipes.smelting().func_151394_a(new ItemStack(ore.getPure(), 1, i), new ItemStack(ore.ingot().getItem(), ore.getIngotsPerStage(i), ore.ingot().getItemDamage()), 100);
				}
			}
		}
	}

	public static void initTechnomancyRecipes() {
		if(Ids.itemBoost)
			itemBoost = GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemBoost), new Object[]{
				" R ",
				"RNR",
				" G ",
				'R', Items.redstone, 'N', Items.quartz, 'G', Items.gold_ingot
			});

		if(Ids.crystalBlock){
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.crystalBlock, 1, 0), new Object[]{
				"GR ",
				"RG ",
				'G', Items.glowstone_dust, 'R', "dyeBlack"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.crystalBlock, 1, 1), new Object[]{
				"GR ",
				"RG ",
				'G', Items.glowstone_dust, 'R', "dyeWhite"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.crystalBlock, 1, 2), new Object[]{
				"GR ",
				"RG ",
				'G', Items.glowstone_dust, 'R', "dyeRed"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.crystalBlock, 1, 3), new Object[]{
				"GR ",
				"RG ",
				'G', Items.glowstone_dust, 'R', "dyeGreen"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.crystalBlock, 1, 4), new Object[]{
				"GR ",
				"RG ",
				'G', Items.glowstone_dust, 'R', "dyeBlue"
			}));
		}

		if(Ids.catalyst){
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.catalyst, 1, 0), new Object[]{
				"BRA",
				"RGR",
				"ARB",
				'G', Blocks.gold_block, 'R', "dyeBlack", 'B', Blocks.obsidian, 'A', Items.ender_pearl
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.catalyst, 1, 1), new Object[]{
				"BRA",
				"RGR",
				"ARB",
				'G', Blocks.gold_block, 'R', "dyeWhite", 'B', Items.golden_apple, 'A', Items.golden_carrot
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.catalyst, 1, 2), new Object[]{
				"BRB",
				"RGR",
				"BRB",
				'G', Blocks.gold_block, 'R', "dyeRed", 'B', Items.blaze_rod
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.catalyst, 1, 4), new Object[]{
				"ARB",
				"RGR",
				"BRA",
				'G', Blocks.gold_block, 'R', "dyeBlue", 'B', Blocks.clay, 'A', Items.fish
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.catalyst, 1, 3), new Object[]{
				"BRB",
				"RGR",
				"BRB",
				'G', Blocks.gold_block, 'R', "dyeGreen", 'B', Blocks.grass
			}));
		}
		if(Ids.coilCoupler){
			coilCoupler = GameRegistry.addShapedRecipe(new ItemStack(TMItems.coilCoupler, 1), new Object[]{
				"IRG",
				"SRR",
				"S  ",
				'G', Items.glowstone_dust, 'R', Items.redstone, 'S', Items.stick, 'I', Items.iron_ingot
			});
		}
		if(Ids.itemTransmitter){
			GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.itemTransmitter, 1), new Object[]{
				"III",
				"ISI",
				"IRI",
				'R', Items.ender_pearl, 'S', Items.stick, 'I', Items.iron_ingot
			});
		}
	}
}