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
import theflogat.technomancy.lib.compat.ThermalExpansion;
import theflogat.technomancy.util.Ore;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingHandler {

    public static IRecipe itemBoost;
    public static IRecipe coilCoupler;

    public static void initFurnaceRecipes() {
        for (Ore ore : Ore.ORES) {
            if (ore.isEnabled()) {
                for (int i = 0; i < ItemProcessedOre.MAXSTAGE; i++) {
                    FurnaceRecipes.smelting().func_151394_a(new ItemStack(ore.getPure(), 1, i), new ItemStack(ore.ingot().getItem(), ore.getIngotsPerStage(i), ore.ingot().getItemDamage()), 1.0F);
                }
            }
        }
    }

    public static void initTechnomancyRecipes() {
        if (Ids.itemBoost)
            itemBoost = GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemBoost), new Object[] { " R ", "RNR", " G ", 'R', Items.redstone, 'N', Items.quartz, 'G', Items.gold_ingot });

        if (Ids.crystalBlock) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.crystalBlock, 3, 0), new Object[] { "GR ", "RG ", 'G', Items.glowstone_dust, 'R', "dyeBlack" }));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.crystalBlock, 3, 1), new Object[] { "GR ", "RG ", 'G', Items.glowstone_dust, 'R', "dyeWhite" }));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.crystalBlock, 3, 2), new Object[] { "GR ", "RG ", 'G', Items.glowstone_dust, 'R', "dyeRed" }));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.crystalBlock, 3, 3), new Object[] { "GR ", "RG ", 'G', Items.glowstone_dust, 'R', "dyeGreen" }));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.crystalBlock, 3, 4), new Object[] { "GR ", "RG ", 'G', Items.glowstone_dust, 'R', "dyeBlue" }));
        }
        if (Ids.catalyst) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.catalyst, 1, 0), new Object[] { "BRA", "RGR", "ARB", 'G', Blocks.gold_block, 'R', "dyeBlack", 'B', Blocks.obsidian, 'A', Items.ender_pearl }));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.catalyst, 1, 1), new Object[] { "BRA", "RGR", "ARB", 'G', Blocks.gold_block, 'R', "dyeWhite", 'B', Items.golden_apple, 'A', Items.golden_carrot }));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.catalyst, 1, 2), new Object[] { "BRB", "RGR", "BRB", 'G', Blocks.gold_block, 'R', "dyeRed", 'B', Items.blaze_rod }));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.catalyst, 1, 4), new Object[] { "ARB", "RGR", "BRA", 'G', Blocks.gold_block, 'R', "dyeBlue", 'B', Blocks.clay, 'A', Items.fish }));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMBlocks.catalyst, 1, 3), new Object[] { "BRB", "RGR", "BRB", 'G', Blocks.gold_block, 'R', "dyeGreen", 'B', Blocks.grass }));
        }
        if (Ids.coilCoupler) {
            coilCoupler = GameRegistry.addShapedRecipe(new ItemStack(TMItems.coilCoupler, 1), new Object[] { "IRG", "SRR", "S  ", 'G', Items.glowstone_dust, 'R', Items.redstone, 'S', Items.stick, 'I', Items.iron_ingot });
        }
        if (Ids.itemTransmitter) {
            GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.itemTransmitter, 1), new Object[] { "III", "ISI", "ERE", 'R', Items.ender_pearl, 'S', Items.stick, 'I', Items.iron_ingot, 'E', Items.redstone });
        }
        if (Ids.ritualTome) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TMItems.ritualTome, 1), new Object[] { "RI ", "IS ", "   ", 'R', Items.book, 'S', "dyeBlack", 'I', Items.glowstone_dust }));
        }
        if (Ids.exGem) {
            GameRegistry.addRecipe(new ItemStack(TMItems.exGem, 1, 100), new Object[] { " N ", "NEN", " N ", 'E', Items.emerald, 'N', Items.gold_nugget });
            if (Ids.existenceBurner) {
                GameRegistry.addRecipe(new ItemStack(TMBlocks.existenceBurner, 1), new Object[] { "E  ", "A  ", "   ", 'E', TMItems.exGem, 'A', Blocks.anvil });
                if (CompatibilityHandler.te) {
                    GameRegistry.addShapelessRecipe(new ItemStack(TMBlocks.existenceBurner, 1, 1), new Object[] { TMBlocks.existenceBurner, ThermalExpansion.frameMachineBasic, ThermalExpansion.powerCoilSilver });
                } else {
                    GameRegistry.addShapelessRecipe(new ItemStack(TMBlocks.existenceBurner, 1, 1), new Object[] { TMBlocks.existenceBurner, Items.redstone, Blocks.piston });
                }
            }
            if (Ids.existenceUser) {
                GameRegistry.addRecipe(new ItemStack(TMBlocks.existenceUser, 1), new Object[] { " A ", "BGB", " A ", 'G', TMItems.exGem, 'B', Items.golden_apple, 'A', Items.golden_carrot });
                GameRegistry.addRecipe(new ItemStack(TMBlocks.existenceUser, 1, 1), new Object[] { " H ", "BGB", " H ", 'G', TMItems.exGem, 'B', Items.golden_apple, 'H', Items.iron_hoe });
                GameRegistry.addRecipe(new ItemStack(TMBlocks.existenceUser, 1, 2), new Object[] { "OFO", "FGF", "OFO", 'G', TMItems.exGem, 'O', Blocks.obsidian, 'F', Items.flint_and_steel });
            }
            if (Ids.existencePylon) {
                GameRegistry.addRecipe(new ItemStack(TMBlocks.existencePylon, 1, 0), new Object[] { "RRR", "RER", "RPR", 'R', Items.redstone, 'E', TMItems.exGem, 'P', Blocks.piston });
                GameRegistry.addShapelessRecipe(new ItemStack(TMBlocks.existencePylon, 1, 1), new Object[] { Items.diamond, TMItems.itemBoost, TMItems.exGem, new ItemStack(TMBlocks.existencePylon, 1, 0) });
                GameRegistry.addShapelessRecipe(new ItemStack(TMBlocks.existencePylon, 1, 2), new Object[] { Items.ender_pearl, Items.diamond, TMItems.exGem, new ItemStack(TMBlocks.existencePylon, 1, 1) });
            }
        }
    }
}