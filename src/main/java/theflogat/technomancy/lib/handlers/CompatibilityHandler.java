package theflogat.technomancy.lib.handlers;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import theflogat.technomancy.lib.Conf;
import theflogat.technomancy.lib.compat.Thaumcraft;

public class CompatibilityHandler {


	public static void checkThisShit() {
		//		if(ConfigHandler.compatiblity) {
		//			if(Loader.isModLoaded("AWWayofTime")) {
		//				ConfigHandler.bloodmagic = true;
		//				System.out.println("Nazi super science activated");
		//			}
		//			if(Loader.isModLoaded("Botania")) {
		//				ConfigHandler.botania = true;
		//				System.out.println("Hippy scientists encountered");
		//			}
		//			if(Loader.isModLoaded("Thaumcraft")) {
		//				ConfigHandler.thaumcraft = true;
		//			}
		//			ConfigHandler.compatiblity = false;
		//		}
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
