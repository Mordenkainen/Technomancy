package theflogat.technomancy;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.Logger;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.items.technom.ItemProcessedOre;
import theflogat.technomancy.common.potions.TMPotions;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.compat.IModModule;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import theflogat.technomancy.lib.handlers.ConfigHandler;
import theflogat.technomancy.lib.handlers.CraftingHandler;
import theflogat.technomancy.lib.handlers.EventRegister;
import theflogat.technomancy.network.PacketHandler;
import theflogat.technomancy.proxies.CommonProxy;
import theflogat.technomancy.util.Ore;

@Mod(modid = Ref.MOD_ID,
name = Ref.MOD_NAME, 
version = Ref.MOD_VERSION,
acceptedMinecraftVersions = "[1.12,1.12.2]",
dependencies = "after:*")

public class Technomancy {

	@Mod.Instance(Ref.MOD_ID)
	public static Technomancy instance;

	@SidedProxy(clientSide = Ref.proxy_loc + "ClientProxy", serverSide = Ref.proxy_loc + "CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs tabsTM = new CreativeTabs("technomancy") {
		@Override
		public ItemStack getTabIconItem() {
			if(Ore.ores.get(0) != null){
				return new ItemStack(Ore.ores.get(0).getPure(), 1, 5);
			}else {
				return new ItemStack(Blocks.STONE);
			}
		}
	};

	public static Logger logger;
	
	public static ArmorMaterial existencePower = EnumHelper.addArmorMaterial("existencePower", null, 20, new int[] {4, 4, 4, 4}, 35, null, 30);

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		MinecraftForge.EVENT_BUS.register(this);
		ConfigHandler.init(new File(event.getModConfigurationDirectory(), Ref.MOD_NAME + ".cfg"));

		CompatibilityHandler.init();

		TMPotions.initTechnomancy();
		TMBlocks.initTechnomancy();
		TMPotions.initTechnomancy();
		TMItems.initTechnomancy();

		for(IModModule mod : CompatibilityHandler.mods) {
			mod.RegisterItems();
			mod.RegisterBlocks();
		}

		Ore.init();
		ConfigHandler.initOreConfigs();
		TMItems.initPureOres();

		PacketHandler.instance = new PacketHandler();
		new EventRegister();

		if (event.getSide().isClient()) {
			proxy.preInit(event);
		}
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		for(IModModule mod : CompatibilityHandler.mods) {
			mod.Init();
		}

		if (event.getSide().isClient()) {
			TMItems.registerColor();
		}

		proxy.initRenderers();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		FMLInterModComms.sendMessage("waila", "register", "theflogat.technomancy.lib.compat.waila.WailaProvider.callbackRegister");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		CraftingHandler.initTechnomancyRecipes();
		CraftingHandler.initFurnaceRecipes();

		for(IModModule mod : CompatibilityHandler.mods) {
			mod.RegisterRecipes();
		}
	
		for(IModModule mod : CompatibilityHandler.mods) {
			mod.PostInit();
		}
	}
}