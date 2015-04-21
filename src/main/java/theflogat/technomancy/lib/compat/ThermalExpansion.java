package theflogat.technomancy.lib.compat;

import cpw.mods.fml.common.registry.GameRegistry;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ThermalExpansion extends ModuleBase {

	private static ThermalExpansion instance;
	
	public static ItemStack powerCoilElectrum;
	public static ItemStack capacitorResonant;
	public static ItemStack powerCoilSilver;
	public static ItemStack powerCoilGold;

	public static ItemStack frameCellBasic;
	public static ItemStack frameTesseractFull;
	public static ItemStack frameMachineBasic;
	
	public static Block blockCell;
	public static Block blockDynamo;
	public static Block blockTank;
	
	private ThermalExpansion() {}
	
	public static ThermalExpansion getInstance() {
		if(instance == null) {
			instance = new ThermalExpansion();
		}
		return instance;
	}

	@Override
	public void Init() {
		powerCoilElectrum = GameRegistry.findItemStack("ThermalExpansion", "powerCoilElectrum", 1);
		capacitorResonant = GameRegistry.findItemStack("ThermalExpansion", "capacitorResonant", 1);
		powerCoilSilver = GameRegistry.findItemStack("ThermalExpansion", "powerCoilSilver", 1);
		powerCoilGold = GameRegistry.findItemStack("ThermalExpansion", "powerCoilElectrum", 1);
		
		blockCell = GameRegistry.findBlock("ThermalExpansion", "Cell");
		blockDynamo = GameRegistry.findBlock("ThermalExpansion", "Dynamo");
		blockTank = GameRegistry.findBlock("ThermalExpansion", "Tank");
		
		frameMachineBasic = GameRegistry.findItemStack("ThermalExpansion", "frameMachineBasic", 1);
		frameTesseractFull = GameRegistry.findItemStack("ThermalExpansion", "frameTesseractFull", 1);
		frameCellBasic = GameRegistry.findItemStack("ThermalExpansion", "frameCellBasic", 1);
		
		if(powerCoilElectrum != null && capacitorResonant != null && powerCoilSilver != null && blockCell != null &&
				blockDynamo != null && blockTank != null && frameMachineBasic != null && frameTesseractFull != null &&
				frameCellBasic != null) {
			Technomancy.logger.info("Thermal Expansion compatibility module loaded.");
		} else {
			Technomancy.logger.warn("Thermal Expansion compatibility module failed to load. Vanilla recipies will be used.");
			CompatibilityHandler.te = false;
		}
	}

	@Override
	public void PostInit() {}

	@Override
	public void RegisterItems() {}

	@Override
	public void RegisterBlocks() {}

	@Override
	public void RegisterRecipes() {}
}
