package theflogat.technomancy.lib.compat;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.wands.WandRod;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.air.BlockFakeAirNG;
import theflogat.technomancy.common.blocks.base.BlockCosmeticOpaque;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.blocks.thaumcraft.dynamos.BlockEssentiaDynamo;
import theflogat.technomancy.common.blocks.thaumcraft.dynamos.BlockNodeDynamo;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockAdvDeconTable;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockBiomeMorpher;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockCondenser;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockEldritchConsumer;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockElectricBellows;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockEssentiaFusor;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockEssentiaTransmitter;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockFluxLamp;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockNodeGenerator;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockTCProcessor;
import theflogat.technomancy.common.blocks.thaumcraft.storage.BlockCreativeJar;
import theflogat.technomancy.common.blocks.thaumcraft.storage.BlockEssentiaContainer;
import theflogat.technomancy.common.blocks.thaumcraft.storage.BlockReservoir;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.items.thaumcraft.ElectricWandUpdate;
import theflogat.technomancy.common.items.thaumcraft.ItemFusionFocus;
import theflogat.technomancy.common.items.thaumcraft.ItemPen;
import theflogat.technomancy.common.items.thaumcraft.ItemTHMaterial;
import theflogat.technomancy.common.items.thaumcraft.ItemTechnoturgeScepter;
import theflogat.technomancy.common.items.thaumcraft.ItemWandCores;
import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.common.tiles.thaumcraft.dynamos.TileEssentiaDynamo;
import theflogat.technomancy.common.tiles.thaumcraft.dynamos.TileNodeDynamo;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileAdvDeconTable;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileBiomeMorpher;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileCondenser;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEldritchConsumer;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileElectricBellows;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaFusor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaTransmitter;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileFluxLamp;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileTCProcessor;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileCreativeJar;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileEssentiaContainer;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileEssentiaReservoir;
import theflogat.technomancy.lib.TMConfig;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;
import theflogat.technomancy.lib.compat.thaumcraft.ScepterRecipe;
import theflogat.technomancy.lib.compat.thaumcraft.TechnoResearch;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class Thaumcraft extends ModuleBase {

    private static Thaumcraft instance;

    public static Fluid FLUXGOO;

    public static Item itemResource;
    public static Item itemEssence;
    public static Item itemNugget;
    public static Item itemShard;
    public static Item itemWandRod;
    public static Item itemWandCap;
    public static Item itemWandCasting;
    public static Item itemPickThaumium;
    public static Item itemJarFilled;

    public static Block blockCosmeticSolid;
    public static Block blockMetalDevice;
    public static Block blockStoneDevice;
    public static Block blockJar;
    public static Block blockTube;
    public static Block blockCustomPlant;
    public static Block blockWoodenDevice;
    public static Block blockTable;

    public static WandRod WAND_ROD_ELECTRIC;
    public static WandRod WAND_ROD_TECHNOTURGE;

    private Thaumcraft() {}

    public static Thaumcraft getInstance() {
        if (instance == null) {
            instance = new Thaumcraft();
        }
        return instance;
    }

    public static void wispFX3(World worldObj, double posX, double posY, double posZ, double posX2, double posY2, double posZ2, float size, int type, boolean shrink, float gravity) {
        thaumcraft.common.Thaumcraft.proxy.wispFX3(worldObj, posX, posY, posZ, posX2, posY2, posZ2, size, type, shrink, gravity);
    }

    public static TileEntity getConnectableTile(World world, int x, int y, int z, ForgeDirection face) {
        TileEntity te = world.getTileEntity(x + face.offsetX, y + face.offsetY, z + face.offsetZ);
        if (!(te instanceof TileFakeAirNG) && ((te instanceof IEssentiaTransport)) && (((IEssentiaTransport) te).isConnectable(face.getOpposite()))) {
            return te;
        }
        return null;
    }

    public static TileEntity getConnectableAsContainer(IBlockAccess w, int x, int y, int z, ForgeDirection face) {
        TileEntity te = w.getTileEntity(x + face.offsetX, y + face.offsetY, z + face.offsetZ);
        if (te instanceof IAspectContainer || (te instanceof IEssentiaTransport && ((IEssentiaTransport) te).isConnectable(face.getOpposite()))) {
            return te;
        }
        return null;
    }

    public static void addAspectsToPool(String owner, Aspect aspect, short i) {
        thaumcraft.common.Thaumcraft.proxy.playerKnowledge.setAspectPool(owner, aspect, i);
    }

    public static short getAspectPoolFor(String owner, Aspect aspect) {
        return thaumcraft.common.Thaumcraft.proxy.playerKnowledge.getAspectPoolFor(owner, aspect);
    }

    public static void smeltify() {
        if (TMConfig.bonus) {
            ThaumcraftApi.addSmeltingBonus("oreGold", new ItemStack(Items.gold_nugget, 9, 0));
            ThaumcraftApi.addSmeltingBonus("oreIron", new ItemStack(Thaumcraft.itemNugget, 9, 0));
            ThaumcraftApi.addSmeltingBonus("oreCinnabar", new ItemStack(Thaumcraft.itemNugget, 9, 5));
            ThaumcraftApi.addSmeltingBonus("oreCopper", new ItemStack(Thaumcraft.itemNugget, 9, 1));
            ThaumcraftApi.addSmeltingBonus("oreTin", new ItemStack(Thaumcraft.itemNugget, 9, 2));
            ThaumcraftApi.addSmeltingBonus("oreSilver", new ItemStack(Thaumcraft.itemNugget, 9, 3));
            ThaumcraftApi.addSmeltingBonus("oreLead", new ItemStack(Thaumcraft.itemNugget, 9, 4));
        }
    }

    @Override
    public void Init() {
        FLUXGOO = FluidRegistry.getFluid("fluxgoo");
        blockCosmeticSolid = GameRegistry.findBlock("Thaumcraft", "blockCosmeticSolid");
        blockMetalDevice = GameRegistry.findBlock("Thaumcraft", "blockMetalDevice");
        blockStoneDevice = GameRegistry.findBlock("Thaumcraft", "blockStoneDevice");
        blockJar = GameRegistry.findBlock("Thaumcraft", "blockJar");
        blockTube = GameRegistry.findBlock("Thaumcraft", "blockTube");
        blockCustomPlant = GameRegistry.findBlock("Thaumcraft", "blockCustomPlant");
        blockWoodenDevice = GameRegistry.findBlock("Thaumcraft", "blockWoodenDevice");
        blockTable = GameRegistry.findBlock("Thaumcraft", "blockTable");

        itemResource = GameRegistry.findItem("Thaumcraft", "ItemResource");
        itemEssence = GameRegistry.findItem("Thaumcraft", "ItemEssence");
        itemNugget = GameRegistry.findItem("Thaumcraft", "ItemNugget");
        itemShard = GameRegistry.findItem("Thaumcraft", "ItemShard");
        itemWandRod = GameRegistry.findItem("Thaumcraft", "WandRod");
        itemWandCap = GameRegistry.findItem("Thaumcraft", "WandCap");
        itemWandCasting = GameRegistry.findItem("Thaumcraft", "WandCasting");
        itemPickThaumium = GameRegistry.findItem("Thaumcraft", "ItemPickThaumium");
        itemJarFilled = GameRegistry.findItem("Thaumcraft", "BlockJarFilledItem");

        if (FLUXGOO != null && blockCosmeticSolid != null && blockMetalDevice != null && blockStoneDevice != null && blockJar != null && blockTube != null && blockCustomPlant != null && blockWoodenDevice != null && blockTable != null && itemResource != null && itemEssence != null && itemNugget != null && itemShard != null && itemWandRod != null && itemWandCap != null && itemWandCasting != null && itemPickThaumium != null && itemJarFilled != null) {
            Technomancy.logger.info("Thaumcraft compatibility module loaded.");
        } else {
            Technomancy.logger.warn("Thaumcraft compatibility module failed to load.");
            CompatibilityHandler.th = false;
        }
    }

    @Override
    public void PostInit() {
        TechnoResearch.init();
        smeltify();
    }

    @Override
    public void RegisterItems() {
        // Item Initializations
        // essentiaCannon = new ItemEssentiaCannon(ItemIds.idESSENTIA_CANNON);
        TMItems.itemMaterial = TMConfig.itemMaterial ? new ItemTHMaterial() : null;
        TMItems.itemPen = TMConfig.pen ? new ItemPen() : null;
        TMItems.itemWandCores = TMConfig.wandCores ? new ItemWandCores() : null;
        TMItems.itemFusionFocus = TMConfig.focusFusion ? new ItemFusionFocus() : null;
        TMItems.itemTechnoturgeScepter = TMConfig.scepter ? new ItemTechnoturgeScepter() : null;

        // Registry
        // GameRegistry.registerItem(essentiaCannon,
        // LibNames.ESSENTIA_CANNON_NAME);
        registerItem(TMItems.itemMaterial, Names.ITEMMATERIAL);
        registerItem(TMItems.itemPen, Names.PEN);
        registerItem(TMItems.itemFusionFocus, Names.FUSIONFOCUS);
        registerItem(TMItems.itemTechnoturgeScepter, Names.SCEPTER);

        registerItem(TMItems.itemWandCores, Names.WANDCORES);
        if (TMConfig.wandCores) {
            WAND_ROD_ELECTRIC = new WandRod("electric", 25, new ItemStack(TMItems.itemWandCores, 1, 0), 10, new ElectricWandUpdate(), new ResourceLocation("technom", "textures/models/electricWand.png"));
            WAND_ROD_TECHNOTURGE = new WandRod("technoturge", 100, new ItemStack(TMItems.itemWandCores, 1, 1), 11, new ResourceLocation("technom", "textures/models/electricWand.png"));
        }
    }

    @Override
    public void RegisterBlocks() {
        TMBlocks.nodeDynamo = TMConfig.dynNode ? new BlockNodeDynamo() : null;
        TMBlocks.essentiaContainer = TMConfig.contEssentia ? new BlockEssentiaContainer() : null;
        TMBlocks.cosmeticOpaque = TMConfig.cosmeticOpaque ? new BlockCosmeticOpaque() : null;
        TMBlocks.essentiaDynamo = TMConfig.dynEssentia ? new BlockEssentiaDynamo() : null;
        TMBlocks.biomeMorpher = TMConfig.biomeMorpher ? new BlockBiomeMorpher() : null;
        TMBlocks.nodeGenerator = TMConfig.nodeGen ? new BlockNodeGenerator() : null;
        TMBlocks.fakeAirNG = TMConfig.nodeGen ? new BlockFakeAirNG() : null;
        TMBlocks.fluxLamp = TMConfig.fluxLamp ? new BlockFluxLamp() : null;
        TMBlocks.teslaCoil = TMConfig.wirelessCoil ? new BlockEssentiaTransmitter() : null;
        TMBlocks.electricBellows = TMConfig.electricBellows ? new BlockElectricBellows() : null;
        TMBlocks.creativeJar = TMConfig.creativeJar ? new BlockCreativeJar() : null;
        // TMBlocks.reconstructorBlock = Ids.reconstructor ? new
        // BlockReconstructor() : null;
        TMBlocks.condenserBlock = TMConfig.condenser ? new BlockCondenser() : null;
        TMBlocks.processorTC = TMConfig.processorTC ? new BlockTCProcessor() : null;
        TMBlocks.eldritchConsumer = TMConfig.eldrichConsumer ? new BlockEldritchConsumer() : null;
        TMBlocks.reservoir = TMConfig.reservoir ? new BlockReservoir() : null;
        TMBlocks.advDeconTable = TMConfig.advDeconTable ? new BlockAdvDeconTable() : null;
        TMBlocks.essentiaFusor = TMConfig.fusor ? new BlockEssentiaFusor() : null;

        registerBlock(TMBlocks.nodeDynamo, Names.NODEDYNAMO);
        registerBlock(TMBlocks.essentiaContainer, Names.ESSENTIACONTAINER);
        registerBlock(TMBlocks.cosmeticOpaque, Names.COSMETICOPAQUE);
        registerBlock(TMBlocks.essentiaDynamo, Names.ESSENTIADYNAMO);
        registerBlock(TMBlocks.biomeMorpher, Names.BIOMEMORPHER);
        registerBlock(TMBlocks.nodeGenerator, Names.NODEGENERATOR);
        registerBlock(TMBlocks.fakeAirNG, Names.FAKEAIRNG);
        registerBlock(TMBlocks.fluxLamp, Names.FLUXLAMP);
        registerBlock(TMBlocks.teslaCoil, Names.ESSENTIATRANS);
        registerBlock(TMBlocks.electricBellows, Names.ELECTRICBELLOWS);
        registerBlock(TMBlocks.creativeJar, Names.CREATIVEJAR);
        // registerBlock(TMBlocks.reconstructorBlock, Names.reconstructor);
        registerBlock(TMBlocks.condenserBlock, Names.CONDENSERBLOCK);
        registerBlock(TMBlocks.processorTC, Names.PROCESSOR + "TC");
        registerBlock(TMBlocks.eldritchConsumer, Names.ELDRITCHCONSUMER);
        registerBlock(TMBlocks.reservoir, Names.RESERVOIR);
        registerBlock(TMBlocks.advDeconTable, Names.ADVDECONTABLE);
        registerBlock(TMBlocks.essentiaFusor, Names.FUSOR);

        registerTileEntity(TMBlocks.essentiaContainer, TileEssentiaContainer.class, "TileEssentiacontainer");
        registerTileEntity(TMBlocks.nodeDynamo, TileNodeDynamo.class, "TileNodeDynamo");
        registerTileEntity(TMBlocks.essentiaDynamo, TileEssentiaDynamo.class, "TileEssentiaDynamo");
        registerTileEntity(TMBlocks.biomeMorpher, TileBiomeMorpher.class, "TileBiomeMorpher");
        registerTileEntity(TMBlocks.nodeGenerator, TileNodeGenerator.class, "TileNodeGenerator");
        registerTileEntity(TMBlocks.fakeAirNG, TileFakeAirNG.class, Reference.MOD_PREFIX + "TileFakeAir");
        registerTileEntity(TMBlocks.fluxLamp, TileFluxLamp.class, "TileFluxLamp");
        registerTileEntity(TMBlocks.teslaCoil, TileEssentiaTransmitter.class, "TileTeslaCoil");
        registerTileEntity(TMBlocks.electricBellows, TileElectricBellows.class, "TileElectricBellows");
        registerTileEntity(TMBlocks.creativeJar, TileCreativeJar.class, "TileCreativeJar");
        // registerTileEntity(TMBlocks.reconstructorBlock,
        // TileReconstructor.class, "TileReconstructor");
        registerTileEntity(TMBlocks.condenserBlock, TileCondenser.class, "TileCondenser");
        registerTileEntity(TMBlocks.processorTC, TileTCProcessor.class, "TileProcessorTC");
        registerTileEntity(TMBlocks.eldritchConsumer, TileEldritchConsumer.class, "TileEldrichConsumer");
        registerTileEntity(TMBlocks.reservoir, TileEssentiaReservoir.class, Reference.MOD_PREFIX + "TileEssentiaReservoir");
        registerTileEntity(TMBlocks.advDeconTable, TileAdvDeconTable.class, Reference.MOD_PREFIX + "TileAdvDeconTable");
        registerTileEntity(TMBlocks.essentiaFusor, TileEssentiaFusor.class, Reference.MOD_PREFIX + "TileEssentiaFusor");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void RegisterRecipes() {
        // Crucible Recipes
        if (TMConfig.itemMaterial) {
            TechnoResearch.recipes.put("NeutronizedMetal", ThaumcraftApi.addCrucibleRecipe("THAUMIUM", new ItemStack(TMItems.itemMaterial, 1, 0), new ItemStack(Thaumcraft.itemResource, 1, 2), new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENERGY, 2)));
            // Crafting Recipes
            TechnoResearch.recipes.put("MagicCoil", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemMaterial, 1, 1), new Object[] { "  N", " T ", "N  ", 'N', new ItemStack(Items.redstone), 'T', new ItemStack(Thaumcraft.itemResource, 1, 2) }));
            TechnoResearch.recipes.put("NeutronizedGear", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemMaterial, 1, 2), new Object[] { " T ", "TIT", " T ", 'T', new ItemStack(TMItems.itemMaterial, 0), 'I', new ItemStack(Items.iron_ingot) }));
        }
        if (TMConfig.pen) {
            TechnoResearch.recipes.put("PenCore", oreDictRecipe(new ItemStack(TMItems.itemMaterial, 1, 3), new Object[] { " NI", "NIN", "IN ", Character.valueOf('N'), "nuggetIron", Character.valueOf('I'), "dyeBlack" }));
            TechnoResearch.recipes.put("Pen", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemPen, 1), new Object[] { " IC", "IPI", "NI ", 'I', new ItemStack(Items.iron_ingot), 'C', new ItemStack(Thaumcraft.itemWandCap, 1, 0), 'P', new ItemStack(TMItems.itemMaterial, 1, 3), 'N', new ItemStack(Items.gold_nugget) }));
        }
        if (TMConfig.fluxLamp) {
            TechnoResearch.recipes.put("FluxLamp",
                    ThaumcraftApi.addInfusionCraftingRecipe("FLUXLAMP", new ItemStack(TMBlocks.fluxLamp), 10, new AspectList().add(Aspect.MECHANISM, 45).add(Aspect.TAINT, 45).add(Aspect.ORDER, 45).add(Aspect.LIGHT, 45), new ItemStack(Thaumcraft.blockMetalDevice, 1, 7), new ItemStack[] { new ItemStack(Thaumcraft.itemShard, 1, 4), new ItemStack(Thaumcraft.itemShard, 1, 4), new ItemStack(Items.bucket), new ItemStack(Items.bucket), new ItemStack(Items.emerald), new ItemStack(Items.emerald) }));
        }
        if (TMConfig.wandCores) {
            if (TMConfig.scepter) {
                ScepterRecipe scepter = new ScepterRecipe();
                ThaumcraftApi.getCraftingRecipes().add(scepter);
                ItemStack scepter2 = new ItemStack(TMItems.itemWandCores, 1, 1);
                TechnoResearch.recipes.put("TechnoturgeScepterRod", ThaumcraftApi.addInfusionCraftingRecipe("TECHNOTURGESCEPTER", scepter2, 20, new AspectList().add(Aspect.ENERGY, 64).add(Aspect.MAGIC, 64).add(Aspect.MECHANISM, 64).add(Aspect.EXCHANGE, 64).add(Aspect.FIRE, 32).add(Aspect.EARTH, 32).add(Aspect.AIR, 32).add(Aspect.WATER, 32).add(Aspect.ORDER, 32).add(Aspect.ENTROPY, 32), new ItemStack(Thaumcraft.itemWandRod, 1, 2),
                        new ItemStack[] { new ItemStack(TMItems.itemWandCores, 1, 0), new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(TMItems.itemWandCores, 1, 0), new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(TMItems.itemWandCores, 1, 0), new ItemStack(TMItems.itemMaterial, 1, 1) }));
            }
        }
        // Arcane Recipes
        if (TMConfig.cosmeticOpaque) {
            TechnoResearch.recipes.put("QuantumGlass", ThaumcraftApi.addArcaneCraftingRecipe("QUANTUMJARS", new ItemStack(TMBlocks.cosmeticOpaque, 4, 0), new AspectList().add(Aspect.ORDER, 5).add(Aspect.FIRE, 5), new Object[] { "GG", "GG", 'G', new ItemStack(Blocks.glass) }));
        }
        if (TMConfig.contEssentia) {
            TechnoResearch.recipes.put("QuantumJar", ThaumcraftApi.addArcaneCraftingRecipe("QUANTUMJARS", new ItemStack(TMBlocks.essentiaContainer, 1, 0), new AspectList().add(Aspect.ORDER, 15).add(Aspect.WATER, 10), new Object[] { "QNQ", "Q Q", "QQQ", 'Q', new ItemStack(TMBlocks.cosmeticOpaque), 'N', new ItemStack(TMItems.itemMaterial, 0) }));
        }
        if (TMConfig.dynNode) {
            TechnoResearch.recipes.put("NodeDynamo", ThaumcraftApi.addArcaneCraftingRecipe("DYNAMO", new ItemStack(TMBlocks.nodeDynamo, 1, 0), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ORDER, 25).add(Aspect.FIRE, 15).add(Aspect.ENTROPY, 10), new Object[] { " C ", "GIG", "IRI", 'C', new ItemStack(TMItems.itemMaterial, 1, 1), 'G', new ItemStack(TMItems.itemMaterial, 1, 2), 'I', new ItemStack(TMItems.itemMaterial, 0), 'R', new ItemStack(Items.redstone) }));
        }
        if (TMConfig.dynEssentia) {
            TechnoResearch.recipes.put("EssentiaDynamo", ThaumcraftApi.addArcaneCraftingRecipe("DYNAMO", new ItemStack(TMBlocks.essentiaDynamo, 1, 0), new AspectList().add(Aspect.WATER, 15).add(Aspect.ORDER, 10).add(Aspect.FIRE, 5).add(Aspect.ENTROPY, 25), new Object[] { " C ", "GIG", "IWI", 'W', new ItemStack(Thaumcraft.blockJar, 1, 0), 'C', new ItemStack(TMItems.itemMaterial, 1, 1), 'G', new ItemStack(TMItems.itemMaterial, 1, 2), 'I', new ItemStack(Thaumcraft.blockTube, 1, 0), }));
        }
        if (TMConfig.biomeMorpher) {
            TechnoResearch.recipes.put("BiomeMorpher", ThaumcraftApi.addArcaneCraftingRecipe("BIOMEMORPHER", new ItemStack(TMBlocks.biomeMorpher), new AspectList().add(Aspect.EARTH, 30).add(Aspect.ORDER, 30).add(Aspect.WATER, 30),
                    new Object[] { " E ", "TOB", "GCG", 'E', new ItemStack(Items.emerald), 'T', new ItemStack(Thaumcraft.itemResource, 1, 11), 'O', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 0), 'B', new ItemStack(Thaumcraft.blockCustomPlant, 1, 4), 'G', new ItemStack(TMItems.itemMaterial, 1, 2), 'C', new ItemStack(TMItems.itemMaterial, 1, 1) }));
        }
        if (TMConfig.wirelessCoil) {
            TechnoResearch.recipes.put("TeslaCoil",
                    ThaumcraftApi.addArcaneCraftingRecipe("TESLACOIL", new ItemStack(TMBlocks.teslaCoil), new AspectList().add(Aspect.WATER, 20).add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20).add(Aspect.AIR, 20).add(Aspect.FIRE, 20).add(Aspect.EARTH, 20), new Object[] { " N ", " C ", "TBT", 'N', new ItemStack(TMItems.itemMaterial, 1, 0), 'C', new ItemStack(TMItems.itemMaterial, 1, 1), 'T', new ItemStack(Thaumcraft.itemResource, 1, 2), 'B', new ItemStack(Thaumcraft.blockTube, 1, 4) }));
            TechnoResearch.recipes.put("CoilCoupler", ThaumcraftApi.addArcaneCraftingRecipe("TESLACOIL", new ItemStack(TMItems.itemMaterial, 1, 4), new AspectList().add(Aspect.AIR, 10).add(Aspect.ORDER, 15), new Object[] { " C ", " T ", " S ", 'C', new ItemStack(TMItems.itemMaterial, 1, 1), 'T', new ItemStack(Thaumcraft.itemResource, 1, 2), 'S', new ItemStack(Items.stick) }));
        }
        // Infusion Recipes
        if (TMConfig.advDeconTable) {
            TechnoResearch.recipes.put("AdvDeconTable", ThaumcraftApi.addInfusionCraftingRecipe("ADVDECONTABLE", new ItemStack(TMBlocks.advDeconTable), 10, new AspectList().add(Aspect.MAGIC, 32).add(Aspect.CRAFT, 8).add(Aspect.EXCHANGE, 16).add(Aspect.TOOL, 16), new ItemStack(Thaumcraft.blockTable, 1, 14), new ItemStack[] { new ItemStack(Blocks.piston), new ItemStack(Blocks.piston), new ItemStack(Blocks.piston), new ItemStack(Items.emerald) }));
        }

        if (TMConfig.fusor) {
            TechnoResearch.recipes.put("EssentiaFusor", ThaumcraftApi.addInfusionCraftingRecipe("ESSENTIAFUSOR", new ItemStack(TMBlocks.essentiaFusor), 10, new AspectList().add(Aspect.MAGIC, 32).add(Aspect.CRAFT, 32).add(Aspect.EXCHANGE, 16).add(Aspect.MECHANISM, 16), new ItemStack(blockTube, 1, 2),
                    new ItemStack[] { new ItemStack(blockJar), new ItemStack(blockTube, 1, 1), new ItemStack(blockJar), new ItemStack(TMItems.itemMaterial, 1, 0), new ItemStack(blockJar), new ItemStack(blockTube, 1, 1), new ItemStack(blockJar), new ItemStack(TMItems.itemMaterial, 1, 0) }));
        }

        if (CompatibilityHandler.te) {
            // Infusion Recipes
            if (TMConfig.nodeGen) {
                TechnoResearch.recipes.put("NodeGenerator", ThaumcraftApi.addInfusionCraftingRecipe("NODEGENERATOR", new ItemStack(TMBlocks.nodeGenerator, 1, 0), 20, new AspectList().add(Aspect.AURA, 75).add(Aspect.ENERGY, 75).add(Aspect.TAINT, 75).add(Aspect.MAGIC, 75), new ItemStack(TMBlocks.nodeDynamo, 1, 0),
                        new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(Blocks.diamond_block), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(TMBlocks.essentiaContainer, 1, 0), new ItemStack(TMBlocks.essentiaContainer, 1, 0), new ItemStack(ThermalExpansion.blockCell, 1, 3) }));
            }
            if (TMConfig.wandCores) {
                TechnoResearch.recipes.put("EnergizedWandRod", ThaumcraftApi.addInfusionCraftingRecipe("ROD_electric", new ItemStack(TMItems.itemWandCores, 1, 0), 8, new AspectList().add(Aspect.ENERGY, 50).add(Aspect.TOOL, 50).add(Aspect.MAGIC, 50).add(Aspect.MECHANISM, 50).add(Aspect.EXCHANGE, 50), new ItemStack(Thaumcraft.itemWandRod, 1, 2),
                        new ItemStack[] { new ItemStack((ThermalExpansion.capacitorResonant).getItem(), 1, 4), new ItemStack((ThermalExpansion.powerCoilElectrum).getItem(), 1, 3), new ItemStack((ThermalExpansion.powerCoilSilver).getItem(), 1, 2), new ItemStack((ThermalExpansion.powerCoilGold).getItem(), 1, 1), new ItemStack(TMItems.itemMaterial, 1, 1) }));
            }
            if (TMConfig.condenser) {
                TechnoResearch.recipes.put("Condenser", ThaumcraftApi.addInfusionCraftingRecipe("CONDENSER", new ItemStack(TMBlocks.condenserBlock), 8, new AspectList().add(Aspect.ENERGY, 100).add(Aspect.MECHANISM, 50).add(Aspect.EXCHANGE, 25).add(Aspect.ORDER, 25), new ItemStack(Thaumcraft.blockStoneDevice, 1, 2),
                        new ItemStack[] { ThermalExpansion.frameMachineBasic, new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4) }));
            }
            if (TMConfig.eldrichConsumer) {
                TechnoResearch.recipes.put("EldritchConsumer", ThaumcraftApi.addInfusionCraftingRecipe("ELDRITCHCONSUMER", new ItemStack(TMBlocks.eldritchConsumer, 1), 16, new AspectList().add(Aspect.EXCHANGE, 256).add(Aspect.MINE, 128).add(Aspect.ENERGY, 64).add(Aspect.MOTION, 32), new ItemStack(Thaumcraft.blockMetalDevice, 1, 9),
                        new ItemStack[] { new ItemStack(Items.diamond_pickaxe), ThermalExpansion.powerCoilGold, new ItemStack(Thaumcraft.blockJar), new ItemStack(Thaumcraft.blockJar), new ItemStack(Thaumcraft.itemPickThaumium) }));
            }
            if (TMConfig.electricBellows) {
                TechnoResearch.recipes.put("ElectricBellows",
                        ThaumcraftApi.addArcaneCraftingRecipe("ELECTRICBELLOWS", new ItemStack(TMBlocks.electricBellows, 1, 0), new AspectList().add(Aspect.AIR, 30).add(Aspect.ORDER, 30).add(Aspect.FIRE, 30), new Object[] { "TG ", "EBC", "TG ", 'T', new ItemStack(Thaumcraft.itemResource, 1, 2), 'G', new ItemStack(TMItems.itemMaterial, 1, 2), 'E', ThermalExpansion.frameCellBasic, 'B', new ItemStack(Thaumcraft.blockWoodenDevice, 1, 0), 'C', new ItemStack(TMItems.itemMaterial, 1, 1) }));
            }
            if (TMConfig.processorTC) {
                TechnoResearch.recipes.put("Processor",
                        ThaumcraftApi.addArcaneCraftingRecipe("PROCESSOR", new ItemStack(TMBlocks.processorTC, 1, 0), new AspectList().add(Aspect.FIRE, 25).add(Aspect.ENTROPY, 25).add(Aspect.ORDER, 25), new Object[] { " A ", "BMB", "ICI", 'A', new ItemStack(Items.redstone), 'B', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 6), 'M', ThermalExpansion.frameMachineBasic, 'I', new ItemStack(Thaumcraft.itemResource, 1, 2), 'C', new ItemStack(TMItems.itemMaterial, 1, 1) }));
            }
        } else if (CompatibilityHandler.mk) {
            // Infusion Recipes
            if (TMConfig.nodeGen) {
                TechnoResearch.recipes.put("NodeGenerator", ThaumcraftApi.addInfusionCraftingRecipe("NODEGENERATOR", new ItemStack(TMBlocks.nodeGenerator, 1, 0), 20, new AspectList().add(Aspect.AURA, 75).add(Aspect.ENERGY, 75).add(Aspect.TAINT, 75).add(Aspect.MAGIC, 75), new ItemStack(TMBlocks.nodeDynamo, 1, 0),
                        new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(Blocks.diamond_block), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(TMBlocks.essentiaContainer, 1, 0), new ItemStack(TMBlocks.essentiaContainer, 1, 0), Mekanism.energyCube }));
            }
            if (TMConfig.wandCores) {
                TechnoResearch.recipes.put("EnergizedWandRod", ThaumcraftApi.addInfusionCraftingRecipe("ROD_electric", new ItemStack(TMItems.itemWandCores, 1, 0), 8, new AspectList().add(Aspect.ENERGY, 50).add(Aspect.TOOL, 50).add(Aspect.MAGIC, 50).add(Aspect.MECHANISM, 50).add(Aspect.EXCHANGE, 50), new ItemStack(Thaumcraft.itemWandRod, 1, 2),
                        new ItemStack[] { Mekanism.lithiumDust, Mekanism.lithiumDust, Mekanism.lithiumDust, new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(TMItems.itemMaterial, 1, 2), new ItemStack(TMItems.itemMaterial, 1, 1) }));
            }
            if (TMConfig.condenser) {
                TechnoResearch.recipes.put("Condenser", ThaumcraftApi.addInfusionCraftingRecipe("CONDENSER", new ItemStack(TMBlocks.condenserBlock), 8, new AspectList().add(Aspect.ENERGY, 100).add(Aspect.MECHANISM, 50).add(Aspect.EXCHANGE, 25).add(Aspect.ORDER, 25), new ItemStack(Thaumcraft.blockStoneDevice, 1, 2),
                        new ItemStack[] { Mekanism.steelCasing, new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4) }));
            }
            if (TMConfig.eldrichConsumer) {
                TechnoResearch.recipes.put("EldritchConsumer",
                        ThaumcraftApi.addInfusionCraftingRecipe("ELDRITCHCONSUMER", new ItemStack(TMBlocks.eldritchConsumer, 1), 16, new AspectList().add(Aspect.EXCHANGE, 256).add(Aspect.MINE, 128).add(Aspect.ENERGY, 64).add(Aspect.MOTION, 32), new ItemStack(Thaumcraft.blockMetalDevice, 1, 9), new ItemStack[] { new ItemStack(Items.diamond_pickaxe), Mekanism.lithiumDust, new ItemStack(Thaumcraft.blockJar), new ItemStack(Thaumcraft.blockJar), new ItemStack(Thaumcraft.itemPickThaumium) }));
            }
            if (TMConfig.electricBellows) {
                TechnoResearch.recipes.put("ElectricBellows",
                        ThaumcraftApi.addArcaneCraftingRecipe("ELECTRICBELLOWS", new ItemStack(TMBlocks.electricBellows, 1, 0), new AspectList().add(Aspect.AIR, 30).add(Aspect.ORDER, 30).add(Aspect.FIRE, 30), new Object[] { "TG ", "EBC", "TG ", 'T', new ItemStack(Thaumcraft.itemResource, 1, 2), 'G', new ItemStack(TMItems.itemMaterial, 1, 2), 'E', Mekanism.steelCasing, 'B', new ItemStack(Thaumcraft.blockWoodenDevice, 1, 0), 'C', new ItemStack(TMItems.itemMaterial, 1, 1) }));
            }
            if (TMConfig.processorTC) {
                TechnoResearch.recipes.put("Processor", ThaumcraftApi.addArcaneCraftingRecipe("PROCESSOR", new ItemStack(TMBlocks.processorTC, 1, 0), new AspectList().add(Aspect.FIRE, 25).add(Aspect.ENTROPY, 25).add(Aspect.ORDER, 25), new Object[] { " A ", "BMB", "ICI", 'A', new ItemStack(Items.redstone), 'B', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 6), 'M', Mekanism.steelCasing, 'I', new ItemStack(Thaumcraft.itemResource, 1, 2), 'C', new ItemStack(TMItems.itemMaterial, 1, 1) }));
            }
        } else {
            // Infusion Recipes
            if (TMConfig.nodeGen) {
                TechnoResearch.recipes.put("NodeGenerator", ThaumcraftApi.addInfusionCraftingRecipe("NODEGENERATOR", new ItemStack(TMBlocks.nodeGenerator, 1, 0), 20, new AspectList().add(Aspect.AURA, 75).add(Aspect.ENERGY, 75).add(Aspect.TAINT, 75).add(Aspect.MAGIC, 75), new ItemStack(TMBlocks.nodeDynamo, 1, 0),
                        new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(Blocks.diamond_block), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(TMBlocks.essentiaContainer, 1, 0), new ItemStack(TMBlocks.essentiaContainer, 1, 0), new ItemStack(Blocks.gold_block, 1, 0) }));
            }
            if (TMConfig.wandCores) {
                TechnoResearch.recipes.put("EnergizedWandRod", ThaumcraftApi.addInfusionCraftingRecipe("ROD_electric", new ItemStack(TMItems.itemWandCores, 1), 8, new AspectList().add(Aspect.ENERGY, 50).add(Aspect.TOOL, 50).add(Aspect.MAGIC, 50).add(Aspect.MECHANISM, 50).add(Aspect.EXCHANGE, 50), new ItemStack(Thaumcraft.itemWandRod, 1, 2),
                        new ItemStack[] { new ItemStack(Blocks.redstone_block, 1), new ItemStack(Blocks.gold_block, 1, 193), new ItemStack(TMItems.itemMaterial, 1, 2), new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(TMItems.itemMaterial, 1, 1) }));
            }
            if (TMConfig.condenser) {
                TechnoResearch.recipes.put("Condenser", ThaumcraftApi.addInfusionCraftingRecipe("CONDENSER", new ItemStack(TMBlocks.condenserBlock), 8, new AspectList().add(Aspect.ENERGY, 100).add(Aspect.MECHANISM, 50).add(Aspect.EXCHANGE, 25).add(Aspect.ORDER, 25), new ItemStack(Thaumcraft.blockStoneDevice, 1, 2),
                        new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 2), new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4) }));
            }
            if (TMConfig.eldrichConsumer) {
                TechnoResearch.recipes.put("EldritchConsumer", ThaumcraftApi.addInfusionCraftingRecipe("ELDRITCHCONSUMER", new ItemStack(TMBlocks.eldritchConsumer, 1), 16, new AspectList().add(Aspect.EXCHANGE, 256).add(Aspect.MINE, 128).add(Aspect.ENERGY, 64).add(Aspect.MOTION, 32), new ItemStack(Thaumcraft.blockMetalDevice, 1, 9),
                        new ItemStack[] { new ItemStack(Items.diamond_pickaxe), new ItemStack(Items.redstone), new ItemStack(Thaumcraft.blockJar), new ItemStack(Thaumcraft.blockJar), new ItemStack(Thaumcraft.itemPickThaumium) }));
            }
            if (TMConfig.electricBellows) {
                TechnoResearch.recipes.put("ElectricBellows",
                        ThaumcraftApi.addArcaneCraftingRecipe("ELECTRICBELLOWS", new ItemStack(TMBlocks.electricBellows, 1, 0), new AspectList().add(Aspect.AIR, 30).add(Aspect.ORDER, 30).add(Aspect.FIRE, 30), new Object[] { "TG ", "EBC", "TG ", 'T', new ItemStack(Thaumcraft.itemResource, 1, 2), 'G', new ItemStack(TMItems.itemMaterial, 1, 2), 'E', new ItemStack(Blocks.redstone_block, 1), 'B', new ItemStack(Thaumcraft.blockWoodenDevice, 1, 0), 'C', new ItemStack(TMItems.itemMaterial, 1, 1) }));
            }
            if (TMConfig.processorTC) {
                TechnoResearch.recipes.put("Processor",
                        ThaumcraftApi.addArcaneCraftingRecipe("PROCESSOR", new ItemStack(TMBlocks.processorTC, 1, 0), new AspectList().add(Aspect.FIRE, 25).add(Aspect.ENTROPY, 25).add(Aspect.ORDER, 25), new Object[] { " A ", "BMB", "ICI", 'A', new ItemStack(Items.redstone), 'B', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 6), 'M', new ItemStack(Blocks.redstone_block, 1), 'I', new ItemStack(Thaumcraft.itemResource, 1, 2), 'C', new ItemStack(TMItems.itemMaterial, 1, 1) }));
            }
        }
    }

    public static boolean isFull(IAspectContainer cont) {
        for (Aspect as : Aspect.aspects.values()) {
            if (cont.doesContainerAccept(as)) {
                if (cont.addToContainer(as, 1) == 0) {
                    cont.takeFromContainer(as, 1);
                    return false;
                }
            }
        }
        return true;
    }
}