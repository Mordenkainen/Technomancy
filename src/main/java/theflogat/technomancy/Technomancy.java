package theflogat.technomancy;

import java.io.File;

import org.apache.logging.log4j.Logger;

import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.lib.CreativeTabTM;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.compat.BloodMagic;
import theflogat.technomancy.lib.compat.Botania;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.lib.compat.ThermalExpansion;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import theflogat.technomancy.lib.handlers.ConfigHandler;
import theflogat.technomancy.lib.handlers.CraftingHandler;
import theflogat.technomancy.lib.handlers.EventRegister;
import theflogat.technomancy.lib.handlers.ResearchHandler;
import theflogat.technomancy.proxies.CommonProxy;
import theflogat.technomancy.util.Loc;
import theflogat.technomancy.util.Ore;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Ref.MOD_ID, 
	name = Ref.MOD_NAME, 
	version = Ref.MOD_VERSION, 
	dependencies = "after:*")

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

    public static Logger logger;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	logger = event.getModLog();
    	ConfigHandler.init(new File(event.getModConfigurationDirectory(), Ref.MOD_NAME + ".cfg"));
    	new EventRegister();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	CompatibilityHandler.init();
    	
    	if (CompatibilityHandler.bm) {
    		BloodMagic.init();
    	}
         Thaumcraft.init();
        
        if (CompatibilityHandler.te) {
        	ThermalExpansion.init();
        }
         
         TMItems.initTechnomancy();
         TMBlocks.initTechnomancy();
         
         if(Loc.isClient()){
        	 Thaumcraft.client();
         }

         if(Thaumcraft.th) {
         	TMItems.initThaumcraft();
         	TMBlocks.initThaumcraft();
         }
         
        if(CompatibilityHandler.bm) {
        	TMBlocks.initBloodMagic();
         	TMItems.initBloodMagic();
        }
        
         if(CompatibilityHandler.bo) {
         	TMBlocks.initBotania();
         	TMItems.initBotania();
         }
         
    	proxy.initRenderers();
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
    	FMLInterModComms.sendMessage("Waila", "register", "theflogat.technomancy.lib.compat.waila.WailaProvider.callbackRegister");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	Ore.init();
    	
    	ConfigHandler.initOreConfigs();
    	
    	TMItems.initPureOres();
    	
    	CraftingHandler.initTechnomancyRecipes();
    	CraftingHandler.initFurnaceRecipes();
    	
        if(CompatibilityHandler.bm) {
        	BloodMagic.initBloodMagicRecipes();
        }
        
        if(CompatibilityHandler.bo) {
        	Botania.initBotaniaRecipes();
        	Botania.initBotaniaLexicon();
        }
        if(Thaumcraft.th) {
        	CraftingHandler.initThaumcraftRecipes();
        	ResearchHandler.init();
        	CompatibilityHandler.smeltify();
        }
    }
}