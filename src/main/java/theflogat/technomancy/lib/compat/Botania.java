package theflogat.technomancy.lib.compat;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
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
import theflogat.technomancy.lib.TMConfig;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.compat.botania.TechnoLexicon;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import theflogat.technomancy.lib.handlers.CraftingHandler;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import cpw.mods.fml.common.registry.GameRegistry;

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
        if (instance == null) {
            instance = new Botania();
        }
        return instance;
    }

    public static void initBotaniaLexicon() {
        if (TMConfig.matBO) {
            TechnoLexicon manaCoilLex = new TechnoLexicon("tc.research_name.TECHNOBASICS", BotaniaAPI.categoryDevices);
            manaCoilLex.setLexiconPages(BotaniaAPI.internalHandler.textPage("techno.research_page.TECHNOBASICS.1"), BotaniaAPI.internalHandler.manaInfusionRecipePage("Mana Coil", manaCoilRec), BotaniaAPI.internalHandler.craftingRecipePage("Manasteel Gear", manaGear));
        }
        if (TMConfig.flowerDyn) {
            TechnoLexicon flowerDynLex = new TechnoLexicon("tc.research_name.DYNAMO", BotaniaAPI.categoryDevices);
            flowerDynLex.setLexiconPages(BotaniaAPI.internalHandler.textPage("techno.lexicon_page.FLOWERDYNAMO.1"), BotaniaAPI.internalHandler.textPage("techno.lexicon_page.FLOWERDYNAMO.2"), BotaniaAPI.internalHandler.craftingRecipePage("Hippie Dynamo", flowerDynamo));
            if (TMConfig.itemBoost) {
                flowerDynLex.addPage(BotaniaAPI.internalHandler.textPage("techno.lexicon_page.FLOWERDYNAMO.3"));
                flowerDynLex.addPage(BotaniaAPI.internalHandler.craftingRecipePage("Potency Gem", CraftingHandler.itemBoost));
            }
        }
        if (TMConfig.manaFab) {
            TechnoLexicon manaFabLex = new TechnoLexicon("techno.lexicon_name.MANAFAB", BotaniaAPI.categoryDevices);
            manaFabLex.setLexiconPages(BotaniaAPI.internalHandler.textPage("techno.lexicon_page.MANAFAB.1"), BotaniaAPI.internalHandler.craftingRecipePage("Mana Fabricator", manaFabricator));
        }
        if (TMConfig.processorBO) {
            TechnoLexicon processorBOLex = new TechnoLexicon("techno.lexicon_name.PROCESSORBO", BotaniaAPI.categoryDevices);
            processorBOLex.setLexiconPages(BotaniaAPI.internalHandler.textPage("techno.lexicon_page.PROCESSORBO.1"), BotaniaAPI.internalHandler.textPage("techno.lexicon_page.PROCESSORBO.2"), BotaniaAPI.internalHandler.craftingRecipePage("Botanical Purifier", processorBO));
        }
        if (TMConfig.manaExchanger) {
            TechnoLexicon manaExchangerLex = new TechnoLexicon("techno.lexicon_name.MANAEXCHANGER", BotaniaAPI.categoryDevices);
            manaExchangerLex.setLexiconPages(BotaniaAPI.internalHandler.textPage("techno.lexicon_page.MANAEXCHANGER.1"), BotaniaAPI.internalHandler.textPage("techno.lexicon_page.MANAEXCHANGER.2"), BotaniaAPI.internalHandler.craftingRecipePage("Mana Exchanger", manaExchanger));
        }
    }

    public static void sparkle(World world, double d, double d1, double f, Random r) {
        BotaniaAPI.internalHandler.sparkleFX(world, d, d1, f, r.nextFloat(), r.nextFloat(), 1.0F, r.nextFloat() * 4, 10);
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
        // Initializations
        TMItems.itemBO = TMConfig.matBO ? new ItemBOMaterial() : null;
        TMItems.manaBucket = TMConfig.manaFluid ? new ItemManaBucket(TMBlocks.manaFluidBlock) : null;

        // Registration
        registerItem(TMItems.itemBO, Names.ITEMBO);
        registerItem(TMItems.manaBucket, Names.MANABUCKET);

        registerBucket(TMBlocks.manaFluid, TMBlocks.manaFluidBlock, TMItems.manaBucket);
    }

    @Override
    public void RegisterBlocks() {
        TMBlocks.manaFluid = TMConfig.manaFluid ? new ManaFluid() : null;
        TMBlocks.manaFluidBlock = TMConfig.manaFluid ? new BlockManaFluid() : null;
        TMBlocks.flowerDynamo = TMConfig.flowerDyn ? new BlockFlowerDynamo() : null;
        TMBlocks.manaFabricator = TMConfig.manaFab ? new BlockManaFabricator() : null;
        TMBlocks.processorBO = TMConfig.processorBO ? new BlockBOProcessor() : null;
        TMBlocks.manaExchanger = TMConfig.manaExchanger ? new BlockManaExchanger() : null;

        registerBlock(TMBlocks.flowerDynamo, Names.FLOWERDYNAMO);
        registerBlock(TMBlocks.manaFabricator, Names.MANAFABRICATOR);
        registerBlock(TMBlocks.processorBO, Names.PROCESSOR + "BO");
        registerBlock(TMBlocks.manaFluidBlock, Names.MANAFLUIDBLOCK);
        registerBlock(TMBlocks.manaExchanger, Names.MANAEXCHANGER, ItemManaExchanger.class);

        registerTileEntity(TMBlocks.flowerDynamo, TileFlowerDynamo.class, "TileFlowerDynamo");
        registerTileEntity(TMBlocks.manaFabricator, TileManaFabricator.class, "ManaFabricator");
        registerTileEntity(TMBlocks.processorBO, TileBOProcessor.class, "TileProcessorBO");
        registerTileEntity(TMBlocks.manaExchanger, TileManaExchanger.class, "TileManaExchanger");
    }

    @Override
    public void RegisterRecipes() {
        if (CompatibilityHandler.te) {
            // ManaInfusion
            if (TMConfig.matBO) {
                manaCoilRec = BotaniaAPI.registerManaInfusionRecipe(new ItemStack(TMItems.itemBO, 1, 0), ThermalExpansion.powerCoilSilver, 3000);
            }

            // Normal Recipes
            if (TMConfig.matBO) {
                manaGear = oreDictRecipe(new ItemStack(TMItems.itemBO, 1, 1), new Object[] { " M ", "MIM", " M ", 'M', "ingotManasteel", 'I', "ingotIron" });
            }
            if (TMConfig.flowerDyn) {
                flowerDynamo = oreDictRecipe(new ItemStack(TMBlocks.flowerDynamo), new Object[] { " C ", "GIG", "IWI", 'W', new ItemStack(Items.redstone), 'C', new ItemStack(TMItems.itemBO, 1, 0), 'G', new ItemStack(TMItems.itemBO, 1, 1), 'I', "ingotManasteel" });
            }
            if (TMConfig.manaFab) {
                manaFabricator = oreDictRecipe(new ItemStack(TMBlocks.manaFabricator), new Object[] { "CDC", "IDI", " P ", 'C', new ItemStack(TMItems.itemBO, 1, 1), 'I', "ingotManasteel", 'D', "manaDiamond", 'P', ThermalExpansion.frameTesseractFull });
            }
            if (TMConfig.processorBO) {
                processorBO = oreDictRecipe(new ItemStack(TMBlocks.processorBO), new Object[] { " A ", "BMB", "ICI", 'M', ThermalExpansion.frameMachineBasic, 'I', "ingotManasteel", 'C', new ItemStack(TMItems.itemBO, 1, 0), 'B', "livingrock", 'A', new ItemStack(Items.redstone) });
            }
            if (TMConfig.manaExchanger) {
                manaExchanger = oreDictRecipe(new ItemStack(TMBlocks.manaExchanger), new Object[] { " A ", "BMB", "ICI", 'M', ThermalExpansion.frameMachineBasic, 'I', new ItemStack(ThermalExpansion.blockTank, 1, 3), 'C', new ItemStack(TMItems.itemBO, 1, 0), 'B', "livingrock", 'A', new ItemStack(GameRegistry.findItem("Botania", "pool")) });
            }
        } else {
            // ManaInfusion
            if (TMConfig.matBO) {
                manaCoilRec = BotaniaAPI.registerManaInfusionRecipe(new ItemStack(TMItems.itemBO, 1, 0), new ItemStack(Items.redstone), 3000);
            }

            // Normal Recipes
            if (TMConfig.matBO) {
                manaGear = oreDictRecipe(new ItemStack(TMItems.itemBO, 1, 1), new Object[] { " M ", "MIM", " M ", 'M', "ingotManasteel", 'I', "ingotIron" });
            }
            if (TMConfig.flowerDyn) {
                flowerDynamo = oreDictRecipe(new ItemStack(TMBlocks.flowerDynamo), new Object[] { " C ", "GIG", "IWI", 'W', new ItemStack(Items.redstone), 'C', new ItemStack(TMItems.itemBO, 1, 0), 'G', new ItemStack(TMItems.itemBO, 1, 1), 'I', "ingotManasteel" });
            }
            if (TMConfig.manaFab) {
                manaFabricator = oreDictRecipe(new ItemStack(TMBlocks.manaFabricator), new Object[] { "CDC", "IDI", " P ", 'C', new ItemStack(TMItems.itemBO, 1, 1), 'I', "ingotManasteel", 'D', "manaDiamond", 'P', new ItemStack(Items.ender_eye, 1, 0) });
            }
            if (TMConfig.processorBO) {
                processorBO = oreDictRecipe(new ItemStack(TMBlocks.processorBO), new Object[] { " A ", "BMB", "ICI", 'M', new ItemStack(Items.redstone), 'I', "ingotManasteel", 'C', new ItemStack(TMItems.itemBO, 1, 0), 'B', "livingrock", 'A', new ItemStack(Items.redstone) });
            }
            if (TMConfig.manaExchanger) {
                manaExchanger = oreDictRecipe(new ItemStack(TMBlocks.manaExchanger), new Object[] { "IAI", "BMB", "ICI", 'M', new ItemStack(Item.getItemFromBlock(Blocks.glass)), 'I', "ingotManasteel", 'C', new ItemStack(TMItems.itemBO, 1, 0), 'B', "livingrock", 'A', new ItemStack(GameRegistry.findItem("Botania", "pool")) });
            }
        }
    }
}
