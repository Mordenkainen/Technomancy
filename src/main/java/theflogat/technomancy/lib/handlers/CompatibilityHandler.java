package theflogat.technomancy.lib.handlers;

import java.util.LinkedList;

import net.minecraftforge.fml.common.Loader;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.Conf;
import theflogat.technomancy.lib.compat.BloodMagic;
import theflogat.technomancy.lib.compat.Botania;
import theflogat.technomancy.lib.compat.IModModule;
import theflogat.technomancy.lib.compat.Mekanism;
import theflogat.technomancy.lib.compat.ThermalExpansion;

public class CompatibilityHandler {
	public static boolean te = false;
	public static boolean bm = false;
	public static boolean bo = false;
	public static boolean th = false;
	public static boolean mk = false;
	public static boolean the = false;
	public static LinkedList<IModModule> mods = new LinkedList<IModModule>();

	public static void init() {
		if(Conf.mkfirst){
			if(Loader.isModLoaded("mekanism")){
				Technomancy.logger.info("Mekanism detected. Compatibility module will be loaded.");
				Technomancy.logger.info("Mekanism detected. Thermal Expansion Compatibility will be skiped.");
				mk = true;
				mods.add(Mekanism.getInstance());
			}else{
				Technomancy.logger.info("Mekanism not detected. Compatibility module will not be loaded.");
				if(Loader.isModLoaded("thermalexpansion")) {
					Technomancy.logger.info("Thermal Expansion detected. Compatibility module will be loaded.");
					te = true;
					mods.add(ThermalExpansion.getInstance());
				} else {
					Technomancy.logger.info("Thermal Expansion not detected. Compatibility module will not be loaded.");
				}
			}
		}else{
			if(Loader.isModLoaded("thermalexpansion")) {
				Technomancy.logger.info("Thermal Expansion detected. Compatibility will be loaded.");
				Technomancy.logger.info("Thermal Expansion detected. Mekanism Compatibility will be skiped.");
				te = true;
				mods.add(ThermalExpansion.getInstance());
			} else {
				Technomancy.logger.info("Thermal Expansion not detected. Compatibility module will not be loaded.");
				if(Loader.isModLoaded("mekanism")){
					Technomancy.logger.info("Mekanism detected. Compatibility module will be loaded.");
					mk = true;
					mods.add(Mekanism.getInstance());
				}else{
					Technomancy.logger.info("Mekanism not detected. Compatibility module will not be loaded.");
				}
			}
		}

		/**
		if(Loader.isModLoaded("Thaumcraft")) {
			Technomancy.logger.info("Thaumcraft detected. Compatibility module will be loaded.");
			th = true;
			mods.add(Thaumcraft.getInstance());
		} else {
			Technomancy.logger.info("Thaumcraft not detected. Compatibility module will not be loaded.");
		}
		*/

		if(Loader.isModLoaded("bloodmagic")) {
			Technomancy.logger.info("Blood Magic detected. Compatibility module will be loaded.");
			bm = true;
			mods.add(BloodMagic.getInstance());
		} else {
			Technomancy.logger.info("Blood Magic not detected. Compatibility module will not be loaded.");
		}

		if(Loader.isModLoaded("botania")) {
			Technomancy.logger.info("Botania detected. Compatibility module will be loaded.");
			bo = true;
			mods.add(Botania.getInstance());
		} else {
			Technomancy.logger.info("Botania not detected. Compatibility module will not be loaded.");
		}

		/**
		if(Loader.isModLoaded("thaumicenergistics")) {
			Technomancy.logger.info("Thaumic Energistics detected. Compatibility module will be loaded.");
			the = true;
			mods.add(ThaumicEnergistics.getInstance());
		} else {
			Technomancy.logger.info("Thaumic Energistics not detected. Compatibility module will not be loaded.");
		}
		*/
	}
}
