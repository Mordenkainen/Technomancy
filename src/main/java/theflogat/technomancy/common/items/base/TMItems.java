package theflogat.technomancy.common.items.base;

import net.minecraft.item.Item;
import theflogat.technomancy.common.items.technom.ItemBoost;
import theflogat.technomancy.common.items.technom.ItemCoilCoupler;
import theflogat.technomancy.common.items.technom.ItemProcessedOre;
import theflogat.technomancy.common.items.technom.existence.ItemExistenceGem;
import theflogat.technomancy.common.items.technom.existence.ItemTreasure;
import theflogat.technomancy.common.items.tome.ItemRitualTome;
import theflogat.technomancy.lib.TMConfig;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.util.Ore;
import cpw.mods.fml.common.registry.GameRegistry;

public class TMItems {

    // Item Instances
    public static Item essentiaCannon;
    public static Item itemMaterial;
    public static Item itemPen;
    public static Item itemWandCores;
    public static Item itemFusionFocus;
    public static Item itemBoost;
    public static Item ritualTome;
    public static Item itemBM;
    public static Item itemBO;
    public static Item manaBucket;
    public static Item coilCoupler;
    public static Item itemTechnoturgeScepter;
    public static Item exGem;
    public static ItemTreasure treasures;

    public static void initTechnomancy() {
        itemBoost = TMConfig.itemBoost ? new ItemBoost() : null;
        ritualTome = TMConfig.ritualTome ? new ItemRitualTome() : null;
        coilCoupler = TMConfig.coilCoupler ? new ItemCoilCoupler() : null;
        exGem = TMConfig.exGem ? new ItemExistenceGem() : null;
        treasures = TMConfig.treasures ? new ItemTreasure() : null;

        registerItem(itemBoost, Names.ITEMBOOST);
        registerItem(ritualTome, Names.RITUALTOME);
        registerItem(coilCoupler, Names.COILCOUPLER);
        registerItem(exGem, Names.EXGEM);
        registerItem(treasures, "treasures");
    }

    public static void initPureOres() {
        for (Ore ore : Ore.ORES) {
            if (ore.isEnabled()) {
                ore.setPure(new ItemProcessedOre(ore));
                GameRegistry.registerItem(ore.getPure(), "pure" + ore.oreName().substring(3));
            }
        }
    }

    private static void registerItem(Item item, String name) {
        if (item != null) {
            GameRegistry.registerItem(item, name);
        }
    }
}
