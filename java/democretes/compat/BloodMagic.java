package democretes.compat;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class BloodMagic {

	public static boolean bm = true;
	public static Fluid lifeEssenceFluid;
	public static Item divinationSigil;


	public static void init() {
		try{
			Class BMM = Class.forName("WayofTime.alchemicalWizardry.AlchemicalWizardry");
			lifeEssenceFluid = (Fluid) BMM.getField("lifeEssenceFluid").get(BMM);
			Class BMI = Class.forName("WayofTime.alchemicalWizardry.ModItems");
			divinationSigil = (Item) BMI.getField("divinationSigil").get(BMI);
			System.out.println("Technomancy: Blood Magic Module Activated");
		}catch(Exception e){bm = false;}
	}
}