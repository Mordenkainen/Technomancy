package theflogat.technomancy.lib.handlers;

import cpw.mods.fml.common.Loader;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.Conf;
import theflogat.technomancy.lib.compat.Thaumcraft;

public class CompatibilityHandler {
	public static boolean te = false;
	public static boolean bm = false;
	public static boolean bo = false;

	public static void init() {
		if(Loader.isModLoaded("Botania")) {
			bo = true;
			Technomancy.logger.info("Botania detected. Compatibility module will be loaded.");
		} else {
			Technomancy.logger.info("Botania not detected. Compatibility module will not be loaded.");
		}
		
		if(Loader.isModLoaded("AWWayofTime")) {
			bm = true;
			Technomancy.logger.info("Blood Magic detected. Compatibility module will be loaded.");
		} else {
			Technomancy.logger.info("Blood Magic not detected. Compatibility module will not be loaded.");
		}
		
		if(Loader.isModLoaded("ThermalExpansion")) {
			te = true;
			Technomancy.logger.info("Thermal Expansion detected. Compatibility module will be loaded.");
		} else {
			Technomancy.logger.info("Thermal Expansion not detected. Compatibility module will not be loaded.");
		}
	}

	public static void smeltify() {
		try{
			if(Conf.bonus) {
				ThaumcraftApi.addSmeltingBonus("oreGold", new ItemStack(Items.gold_nugget, 9, 0));
				ThaumcraftApi.addSmeltingBonus("oreIron", new ItemStack(Thaumcraft.itemNugget, 9, 0));
				ThaumcraftApi.addSmeltingBonus("oreCinnabar", new ItemStack(Thaumcraft.itemNugget, 9, 5));
				ThaumcraftApi.addSmeltingBonus("oreCopper", new ItemStack(Thaumcraft.itemNugget, 9, 1));
				ThaumcraftApi.addSmeltingBonus("oreTin", new ItemStack(Thaumcraft.itemNugget, 9, 2));
				ThaumcraftApi.addSmeltingBonus("oreSilver", new ItemStack(Thaumcraft.itemNugget, 9, 3));
				ThaumcraftApi.addSmeltingBonus("oreLead", new ItemStack(Thaumcraft.itemNugget, 9, 4));
			}
		}catch(Exception e){e.printStackTrace();}
	}

}
