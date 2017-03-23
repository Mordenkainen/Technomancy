package theflogat.technomancy;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.util.EnumHelper;
import org.apache.logging.log4j.Logger;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.potions.TMPotions;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.compat.IModModule;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import theflogat.technomancy.lib.handlers.ConfigHandler;
import theflogat.technomancy.lib.handlers.CraftingHandler;
import theflogat.technomancy.lib.handlers.CreativeTabTM;
import theflogat.technomancy.lib.handlers.EventRegister;
import theflogat.technomancy.network.PacketHandler;
import theflogat.technomancy.proxies.CommonProxy;
import theflogat.technomancy.util.Ore;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Ref.MOD_ID, name = Ref.MOD_NAME, version = Ref.MOD_VERSION, dependencies = "after:*")

public class Technomancy {

    @Instance(Ref.MOD_ID)
    public static Technomancy instance;

    @SidedProxy(clientSide = Ref.proxy_loc + "ClientProxy", serverSide = Ref.proxy_loc + "CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs tabsTM = new CreativeTabTM(CreativeTabs.getNextID(), Ref.MOD_ID);

    public static Logger logger;

    public static ArmorMaterial existencePower = EnumHelper.addArmorMaterial("existencePower", 20, new int[] { 4, 4, 4, 4 }, 35);

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        logger = event.getModLog();

        if (Potion.potionTypes.length < 256) {
            expandPotions();
        }

        ConfigHandler.init(new File(event.getModConfigurationDirectory(), Ref.MOD_NAME + ".cfg"));
        PacketHandler.instance = new PacketHandler();
        new EventRegister();
    }

    @EventHandler
    public void init(final FMLInitializationEvent event) {
        CompatibilityHandler.init();

        for (final IModModule mod : CompatibilityHandler.mods) {
            mod.Init();
        }

        TMItems.initTechnomancy();
        TMBlocks.initTechnomancy();
        TMPotions.initTechnomancy();

        for (final IModModule mod : CompatibilityHandler.mods) {
            mod.RegisterBlocks();
            mod.RegisterItems();
        }

        proxy.initRenderers();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
        FMLInterModComms.sendMessage("Waila", "register", "theflogat.technomancy.lib.compat.waila.WailaProvider.callbackRegister");
    }

    @EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        Ore.init();

        ConfigHandler.initOreConfigs();

        TMItems.initPureOres();

        CraftingHandler.initTechnomancyRecipes();
        CraftingHandler.initFurnaceRecipes();

        for (final IModModule mod : CompatibilityHandler.mods) {
            mod.RegisterRecipes();
        }

        for (final IModModule mod : CompatibilityHandler.mods) {
            mod.PostInit();
        }
    }

    private void expandPotions() {
        Potion[] potionTypes = null;

        for (final Field f : Potion.class.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
                    final Field modfield = Field.class.getDeclaredField("modifiers");
                    modfield.setAccessible(true);
                    modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                    potionTypes = (Potion[]) f.get(null);
                    final Potion[] newPotionTypes = new Potion[256];
                    System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
                    f.set(null, newPotionTypes);
                }
            } catch (IllegalArgumentException e) {
                logger.warn("Unable to expand potion array. Error:", e);
                break;
            } catch (IllegalAccessException e) {
                logger.warn("Unable to expand potion array. Error:", e);
                break;
            } catch (NoSuchFieldException e) {
                logger.warn("Unable to expand potion array. Error:", e);
                break;
            } catch (SecurityException e) {
                logger.warn("Unable to expand potion array. Error:", e);
                break;
            }
        }
    }

}