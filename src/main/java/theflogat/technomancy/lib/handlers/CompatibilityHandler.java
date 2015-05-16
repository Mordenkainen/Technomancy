package theflogat.technomancy.lib.handlers;

import java.util.LinkedList;
import cpw.mods.fml.common.Loader;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.Conf;
import theflogat.technomancy.lib.compat.BloodMagic;
import theflogat.technomancy.lib.compat.Botania;
import theflogat.technomancy.lib.compat.IModModule;
import theflogat.technomancy.lib.compat.Mekanism;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.lib.compat.ThermalExpansion;

public class CompatibilityHandler {
	public static boolean te = false;
	public static boolean bm = false;
	public static boolean bo = false;
	public static boolean th = false;
	public static boolean mk = false;
	public static LinkedList<IModModule> mods = new LinkedList<IModModule>();

	public static void init() {
		if(Conf.mkfirst){
			if(Loader.isModLoaded("Mekanism")){
				Technomancy.logger.info("Mekanism detected. Compatibility module will be loaded.");
				Technomancy.logger.info("Mekanism detected. Thermal Expansion Compatibility will be skiped.");
				mk = true;
				mods.add(Mekanism.getInstance());
			}else{
				Technomancy.logger.info("Mekanism not detected. Compatibility module will not be loaded.");
				if(Loader.isModLoaded("ThermalExpansion")) {
					Technomancy.logger.info("Thermal Expansion detected. Compatibility module will be loaded.");
					te = true;
					mods.add(ThermalExpansion.getInstance());
				} else {
					Technomancy.logger.info("Thermal Expansion not detected. Compatibility module will not be loaded.");
				}
			}
		}else{
			if(Loader.isModLoaded("ThermalExpansion")) {
				Technomancy.logger.info("Thermal Expansion detected. Compatibility will be loaded.");
				Technomancy.logger.info("Thermal Expansion detected. Mekanism Compatibility will be skiped.");
				te = true;
				mods.add(ThermalExpansion.getInstance());
			} else {
				Technomancy.logger.info("Thermal Expansion not detected. Compatibility module will not be loaded.");
				if(Loader.isModLoaded("Mekanism")){
					Technomancy.logger.info("Mekanism detected. Compatibility module will be loaded.");
					mk = true;
					mods.add(Mekanism.getInstance());
				}else{
					Technomancy.logger.info("Mekanism not detected. Compatibility module will not be loaded.");
				}
			}
		}
		
		if(Loader.isModLoaded("Thaumcraft")) {
			Technomancy.logger.info("Thaumcraft detected. Compatibility module will be loaded.");
			th = true;
			mods.add(Thaumcraft.getInstance());
		} else {
			Technomancy.logger.info("Thaumcraft not detected. Compatibility module will not be loaded.");
		}

		if(Loader.isModLoaded("AWWayofTime")) {
			Technomancy.logger.info("Blood Magic detected. Compatibility module will be loaded.");
			bm = true;
			mods.add(BloodMagic.getInstance());
		} else {
			Technomancy.logger.info("Blood Magic not detected. Compatibility module will not be loaded.");
		}

		if(Loader.isModLoaded("Botania")) {
			Technomancy.logger.info("Botania detected. Compatibility module will be loaded.");
			bo = true;
			mods.add(Botania.getInstance());
		} else {
			Technomancy.logger.info("Botania not detected. Compatibility module will not be loaded.");
		}
	}
}
