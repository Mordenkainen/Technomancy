package democretes.compat;

import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import democretes.lib.Conf;

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
		}catch(Exception e){bm = false;System.out.println("Technomancy: Failed to load Blood Magic Module");Conf.ex(e);}
	}
}