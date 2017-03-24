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
import theflogat.technomancy.common.blocks.technom.existence.BlockExistenceUser;
import theflogat.technomancy.common.blocks.technom.existence.BlockExistenceFountain;
import theflogat.technomancy.common.blocks.technom.existence.BlockExistencePylon;
import theflogat.technomancy.common.items.base.ItemAdvancedBase;
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
import theflogat.technomancy.lib.TMConfig;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

public class TMBlocks {

    // Block Instances
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
    // public static Block reconstructorBlock;
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
    public static Block fountainExistence;
    public static Block existenceBurner;
    public static Block existencePylon;
    public static Block existenceUser;

    public static Fluid manaFluid;

    public static void initTechnomancy() {
        crystalBlock = TMConfig.crystalBlock ? new BlockCrystal() : null;
        catalyst = TMConfig.catalyst ? new BlockCatalyst() : null;
        fakeAirLight = TMConfig.catalyst ? new BlockFakeAirLight() : null;
        itemTransmitter = TMConfig.itemTransmitter ? new BlockItemTransmitter() : null;
        basalt = TMConfig.basalt ? new BlockBasalt() : null;
        fountainExistence = TMConfig.existenceFountain ? new BlockExistenceFountain() : null;
        existenceBurner = TMConfig.existenceBurner ? new BlockExistenceBurner() : null;
        existencePylon = TMConfig.existencePylon ? new BlockExistencePylon() : null;
        existenceUser = TMConfig.existenceUser ? new BlockExistenceUser() : null;

        registerBlock(crystalBlock, Names.CRYSTALBLOCK, ItemCrystal.class);
        registerBlock(catalyst, Names.CATALYST, ItemCatalyst.class);
        registerBlock(fakeAirLight, Names.FAKEAIRLIGHT);
        registerBlock(itemTransmitter, Names.ITEMTRANSMITTER);
        registerBlock(basalt, Names.BASALT);
        registerBlock(fountainExistence, Names.EXISTENCEFOUNTAIN);
        registerBlock(existenceBurner, Names.EXISTENCEBURNER[0], ItemBlockExistenceBurner.class);
        registerBlock(existencePylon, Names.EXISTENCEPYLON, ItemBlockExistencePylon.class);
        registerBlock(existenceUser, "existenceUser", ItemBlockExistenceUser.class);

        OreDictionary.registerOre("basalt", basalt);

        GameRegistry.registerTileEntity(TileCrystal.class, Reference.MOD_PREFIX + "TileCrystal");
        GameRegistry.registerTileEntity(TileCatalyst.class, Reference.MOD_PREFIX + "TileCatalyst");
        GameRegistry.registerTileEntity(TileFakeAirCore.class, Reference.MOD_PREFIX + "TileFakeAirCore");
        GameRegistry.registerTileEntity(TileItemTransmitter.class, Reference.MOD_PREFIX + "TileItemTransmitter");
        GameRegistry.registerTileEntity(TileExistenceFountain.class, Reference.MOD_PREFIX + "TileExistenceFountain");
        GameRegistry.registerTileEntity(TileExistenceBurner.class, Reference.MOD_PREFIX + "TileExistenceBurner");
        GameRegistry.registerTileEntity(TileExistencePylon.class, Reference.MOD_PREFIX + "TileExistencePylon");
        GameRegistry.registerTileEntity(TileExistenceDynamicBurner.class, Reference.MOD_PREFIX + "TileExistenceDynamicBurner");
        GameRegistry.registerTileEntity(TileExistenceCropAccelerator.class, Reference.MOD_PREFIX + "TileExistenceCropAccelerator");
        GameRegistry.registerTileEntity(TileExistenceHarvester.class, Reference.MOD_PREFIX + "TileExistenceHarvester");
        GameRegistry.registerTileEntity(TileExistenceSealingDevice.class, Reference.MOD_PREFIX + "TileExistenceSealingDevice");

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
        // RitualRegistry.add(new RitualOfFireT3());
        RitualRegistry.add(new RitualOfFireT2());
        RitualRegistry.add(new RitualOfFireT1());
    }

    public static void registerBlock(Block block, String name) {
        if (block instanceof BlockContainerAdvanced) {
            GameRegistry.registerBlock(block, ItemAdvancedBase.class, name);
            return;
        }
        if (block != null)
            GameRegistry.registerBlock(block, name);
    }

    public static void registerBlock(Block block, String name, Class<? extends ItemBlock> itemclass) {
        if (block != null)
            GameRegistry.registerBlock(block, itemclass, name);
    }
}
