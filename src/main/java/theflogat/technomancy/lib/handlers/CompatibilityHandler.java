package theflogat.technomancy.lib.handlers;

import java.util.LinkedList;

import cpw.mods.fml.common.Loader;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.compat.BloodMagic;
import theflogat.technomancy.lib.compat.Botania;
import theflogat.technomancy.lib.compat.IModModule;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.lib.compat.ThermalExpansion;

public class CompatibilityHandler {
	public static boolean te = false;
	public static boolean bm = false;
	public static boolean bo = false;
	public static boolean th = false;
	public static LinkedList<IModModule> mods = new LinkedList<IModModule>();

	public static void init() {
		if(Loader.isModLoaded("ThermalExpansion")) {
			te = true;
			mods.add(ThermalExpansion.getInstance());
			Technomancy.logger.info("Thermal Expansion detected. Compatibility module will be loaded.");
		} else {
			Technomancy.logger.info("Thermal Expansion not detected. Compatibility module will not be loaded.");
		}
		
		if(Loader.isModLoaded("Thaumcraft")) {
			th = true;
			mods.add(Thaumcraft.getInstance());
			Technomancy.logger.info("Thaumcraft detected. Compatibility module will be loaded.");
		} else {
			Technomancy.logger.info("Thaumcraft not detected. Compatibility module will not be loaded.");
		}
		
		if(Loader.isModLoaded("AWWayofTime")) {
			bm = true;
			mods.add(BloodMagic.getInstance());
			Technomancy.logger.info("Blood Magic detected. Compatibility module will be loaded.");
		} else {
			Technomancy.logger.info("Blood Magic not detected. Compatibility module will not be loaded.");
		}
		
		if(Loader.isModLoaded("Botania")) {
			bo = true;
			mods.add(Botania.getInstance());
			Technomancy.logger.info("Botania detected. Compatibility module will be loaded.");
		} else {
			Technomancy.logger.info("Botania not detected. Compatibility module will not be loaded.");
		}
	}
}
