package theflogat.technomancy;

import java.io.File;

import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.items.TMItems;
import theflogat.technomancy.handlers.compat.BloodMagic;
import theflogat.technomancy.handlers.compat.Botania;
import theflogat.technomancy.handlers.compat.CoFH;
import theflogat.technomancy.handlers.compat.Thaumcraft;
import theflogat.technomancy.handlers.compat.ThermalExpansion;
import theflogat.technomancy.handlers.handlers.CompatibilityHandler;
import theflogat.technomancy.handlers.handlers.ConfigHandler;
import theflogat.technomancy.handlers.handlers.CraftingHandler;
import theflogat.technomancy.handlers.handlers.ResearchHandler;
import theflogat.technomancy.handlers.proxies.CommonProxy;
import theflogat.technomancy.handlers.util.Loc;
import theflogat.technomancy.lib.CreativeTabTM;
import theflogat.technomancy.lib.Ref;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Ref.MOD_ID, 
	name = Ref.MOD_NAME, 
	version = Ref.MOD_VERSION, 
	dependencies = "after:Thaumcraft;after:CoFHCore;after:AWWayofTime;after:Botania;after:ThermalExpansion;")

//@NetworkMod(channels = { Ref.CHANNEL_NAME }, 
//	clientSideRequired = true, 
//	serverSideRequired = false, 
//	packetHandler = PacketHandler.class)

public class Technomancy {

    @Instance(Ref.MOD_ID)
    public static Technomancy instance;

    @SidedProxy(clientSide = Ref.proxy_loc + "ClientProxy", serverSide = Ref.proxy_loc + "CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs tabsTM = new CreativeTabTM(CreativeTabs.getNextID(), Ref.MOD_ID);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	ConfigHandler.init(new File(event.getModConfigurationDirectory(), Ref.MOD_NAME + ".cfg"));
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {    
    	 BloodMagic.init();
         Thaumcraft.init();
         Botania.init();
         ThermalExpansion.init();
         CoFH.init();
         
         TMItems.initTechnomancy();
         TMBlocks.initTechnomancy();
         
         if(Loc.isClient()){
        	 Botania.client();
        	 CoFH.client();
        	 Thaumcraft.client();
         }

         if(Thaumcraft.th) {
         	TMItems.initThaumcraft();
         	TMBlocks.initThaumcraft();
         }
         if(BloodMagic.bm) {
         	TMBlocks.initBloodMagic();
         	TMItems.initBloodMagic();
         }
         if(Botania.bo) {
         	TMBlocks.initBotania();
         	TMItems.initBotania();
         }
         
    	proxy.initRenderers();
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if(BloodMagic.bm) {
        	CraftingHandler.initBloodMagicRecipes();
        }
        if(Botania.bo) {
        	CraftingHandler.initBotaniaRecipes();
        }
        if(Thaumcraft.th) {
        	CraftingHandler.initThaumcraftRecipes();
        	ResearchHandler.init();
        	CompatibilityHandler.smeltify();
            CraftingHandler.initFurnaceRecipe();
        }
    }
}