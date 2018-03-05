package theflogat.technomancy.lib.compat;

import cofh.thermalexpansion.block.dynamo.BlockDynamo;
import cofh.thermalexpansion.block.machine.BlockMachine;
import cofh.thermalexpansion.block.storage.BlockCell;
import cofh.thermalexpansion.block.storage.BlockTank;
import cofh.thermalexpansion.init.TEBlocks;
import cofh.thermalexpansion.item.ItemCapacitor;
import cofh.thermalexpansion.item.ItemFrame;
import cofh.thermalfoundation.item.ItemMaterial;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;

public class ThermalExpansion extends ModuleBase {

	private static ThermalExpansion instance;
	
	public static ItemStack powerCoilElectrum;
	public static ItemStack capacitorResonant;
	public static ItemStack powerCoilSilver;
	public static ItemStack powerCoilGold;

	public static ItemStack frameCellBasic;
	//public static ItemStack frameTesseractFull;
	public static ItemStack frameMachineBasic;
	
	public static ItemStack blockCell;
	public static ItemStack blockDynamo;
	public static ItemStack[] blockTank;
	
	
	private ThermalExpansion() {}
	
	public static ThermalExpansion getInstance() {
		if(instance == null) {
			instance = new ThermalExpansion();
		}
		return instance;
	}

	@Override
	public void preInit() {
	}

	@Override
	public void Init() {
		powerCoilElectrum = ItemMaterial.powerCoilElectrum;
		capacitorResonant = ItemCapacitor.capacitorResonant;
		powerCoilSilver = ItemMaterial.powerCoilSilver;
		powerCoilGold = ItemMaterial.powerCoilGold;

		blockCell = BlockCell.cell[0];
		blockDynamo = BlockDynamo.dynamoSteam;
		blockTank = BlockTank.tank;

		frameMachineBasic = ItemFrame.frameMachine;
		//frameTesseractFull = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalexpansion:frameTesseractFull")));
		frameCellBasic = ItemFrame.frameCell0;

		if(powerCoilElectrum != null && capacitorResonant != null && powerCoilSilver != null && blockCell != null &&
				blockDynamo != null && blockTank != null && frameMachineBasic != null /*&& frameTesseractFull != null*/ &&
				frameCellBasic != null) {
			Technomancy.logger.info("Thermal Expansion compatibility module loaded.");
		} else {
			Technomancy.logger.warn("Thermal Expansion compatibility module failed to load.");
			CompatibilityHandler.te = false;
		}
	}

	@Override
	public void PostInit() {}

	@Override
	public void RegisterItems() {
	}

	@Override
	public void RegisterBlocks() {}

	@Override
	public void RegisterRecipes() {}
}
