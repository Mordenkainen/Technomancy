package theflogat.technomancy.lib.compat;

import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Mekanism implements IModModule {

    private static Mekanism mekanism;

    public static ItemStack energyCube;
    public static ItemStack steelCasing;
    public static ItemStack energyTablet;
    public static ItemStack enrichedAlloy;
    public static ItemStack lithiumDust;

    public static Mekanism getInstance() {
        if (mekanism == null) {
            mekanism = new Mekanism();
        }
        return mekanism;
    }

    @Override
    public void init() {
        energyCube = new ItemStack(GameRegistry.findItem("Mekanism", "EnergyCube"), 1);
        setEnergyCubeTier(BaseTier.ADVANCED, energyCube);
        steelCasing = new ItemStack(GameRegistry.findItem("Mekanism", "BasicBlock"), 1, 8);
        energyTablet = GameRegistry.findItemStack("Mekanism", "EnergyTablet", 1);
        enrichedAlloy = GameRegistry.findItemStack("Mekanism", "EnrichedAlloy", 1);
        lithiumDust = new ItemStack(GameRegistry.findItem("Mekanism", "OtherDust"), 1, 4);
        if (energyCube == null || steelCasing == null || energyTablet == null || enrichedAlloy == null || lithiumDust == null) {
            Technomancy.logger.warn("Mekanism compatibility module failed to load.");
            CompatibilityHandler.mk = false;
        } else {
            Technomancy.logger.info("Mekanism compatibility module loaded.");
        }
    }

    public static enum BaseTier {
        BASIC("Basic"),
        ADVANCED("Advanced"),
        ELITE("Elite"),
        ULTIMATE("Ultimate"),
        CREATIVE("Creative");
        
        private String name;
        
        public String getName() {
            return name;
        }

        private BaseTier(final String s) {
            name = s;
        }
    }

    public void setEnergyCubeTier(final BaseTier tier, final ItemStack items) {
        if (tier == BaseTier.BASIC) {
            items.stackTagCompound = null;
        } else {
            items.stackTagCompound = new NBTTagCompound();
            items.stackTagCompound.setString("tier", tier.name);
        }
    }

    @Override
    public void postInit() {

    }

    @Override
    public void registerItems() {

    }

    @Override
    public void registerBlocks() {

    }

    @Override
    public void registerRecipes() {

    }
}