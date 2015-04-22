package theflogat.technomancy.lib;

import net.minecraftforge.common.config.Configuration;

public final class Rate {

	public Rate() {}
	
	public static final String category = "Rate";
	
	public static void modifyRate(Configuration config) {
		manaFabCost = config.get(category, "ManaFabricator", manaFabCost).getInt(Rate.manaFabCost);
       	bloodFabCost = config.get(category, "BloodFabricator", bloodFabCost).getInt(Rate.bloodFabCost);
       	condenserCost = config.get(category, "Condenser", condenserCost).getInt(Rate.condenserCost);
       	biomeMorpherCost = config.get(category, "BiomeMorpher", biomeMorpherCost).getInt(Rate.biomeMorpherCost);
       	consumerCost = config.get(category, "EldritchConsumer", consumerCost).getInt(Rate.consumerCost);
       	bellowsCost = config.get(category, "ElectricBellows", bellowsCost).getInt(Rate.bellowsCost);
       	exchangerCost = config.get(category, "ManaExchanger", exchangerCost).getInt(Rate.exchangerCost);
	}
	
	public static int manaFabCost = 1000000;
	public static int bloodFabCost = 1000000;
	public static int condenserCost = 1000000;
	public static int biomeMorpherCost = 20000;
	public static int consumerCost = 20000;
	public static int bellowsCost = 500;
	public static int exchangerCost = 1000;
}