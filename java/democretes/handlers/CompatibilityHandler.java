package democretes.handlers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import democretes.compat.Thaumcraft;

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
			if(ConfigHandler.bonus) {
				Thaumcraft.addSmeltingBonus.invoke(null, "oreGold", new ItemStack(Item.goldNugget, 9, 0));
				Thaumcraft.addSmeltingBonus.invoke(null, "oreIron", new ItemStack(Thaumcraft.itemNugget, 9, 0));
				Thaumcraft.addSmeltingBonus.invoke(null, "oreCinnabar", new ItemStack(Thaumcraft.itemNugget, 9, 5));
				Thaumcraft.addSmeltingBonus.invoke(null, "oreCopper", new ItemStack(Thaumcraft.itemNugget, 9, 1));
				Thaumcraft.addSmeltingBonus.invoke(null, "oreTin", new ItemStack(Thaumcraft.itemNugget, 9, 2));
				Thaumcraft.addSmeltingBonus.invoke(null, "oreSilver", new ItemStack(Thaumcraft.itemNugget, 9, 3));
				Thaumcraft.addSmeltingBonus.invoke(null, "oreLead", new ItemStack(Thaumcraft.itemNugget, 9, 4));
			}
		}catch(Exception e){e.printStackTrace();}
	}

}
