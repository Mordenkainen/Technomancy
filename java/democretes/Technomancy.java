package democretes;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import democretes.blocks.TMBlocks;
import democretes.compat.BloodMagic;
import democretes.compat.Botania;
import democretes.compat.CoFH;
import democretes.compat.Thaumcraft;
import democretes.compat.ThermalExpansion;
import democretes.handlers.CompatibilityHandler;
import democretes.handlers.ConfigHandler;
import democretes.handlers.CraftingHandler;
import democretes.handlers.ResearchHandler;
import democretes.items.TMItems;
import democretes.lib.CreativeTabTM;
import democretes.lib.Ref;
import democretes.network.PacketHandler;
import democretes.proxies.CommonProxy;
import democretes.util.Loc;

@Mod(modid = Ref.MOD_ID, 
	name = Ref.MOD_NAME, 
	version = Ref.MOD_VERSION, 
	dependencies = "after:Thaumcraft;required-after:CoFHCore;after:AWWayofTime;after:Botania;after:ThermalExpansion;")

@NetworkMod(channels = { Ref.CHANNEL_NAME }, 
	clientSideRequired = true, 
	serverSideRequired = false, 
	packetHandler = PacketHandler.class)

public class Technomancy {

    @Instance(Ref.MOD_ID)
    public static Technomancy instance;

    @SidedProxy(clientSide = "democretes.proxies.ClientProxy", serverSide = "democretes.proxies.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs tabsTM = new CreativeTabTM(CreativeTabs.getNextID(), Ref.MOD_ID);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	ConfigHandler.init(new File(event.getModConfigurationDirectory(), "technomancy.cfg"));
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {    
    	 BloodMagic.init();
         Thaumcraft.init();
         Botania.init();
         ThermalExpansion.init();
         CoFH.init();
         
         if(Loc.isClient()){
        	 Botania.client();
        	 CoFH.client();
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
    	NetworkRegistry.instance().registerGuiHandler(instance, proxy);
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