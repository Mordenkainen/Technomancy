package theflogat.technomancy.lib.compat;

import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BloodMagic {
	
	public static Fluid lifeEssenceFluid;
	public static Item divinationSigil;
	public static Item bucketLife;
	public static Item bloodRune;
	public static boolean bm = true;

	public static void init() {
		lifeEssenceFluid = FluidRegistry.getFluid("life essence");
		divinationSigil = GameRegistry.findItem("AWWayofTime", "divinationSigil");
		bucketLife = GameRegistry.findItem("AWWayofTime", "bucketLife");
		bloodRune = GameRegistry.findItem("AWWayofTime", "AlchemicalWizardrybloodRune");
		if (lifeEssenceFluid != null && divinationSigil != null && bucketLife != null && bloodRune != null) {
			Technomancy.logger.info("Blood Magic compatibility module loaded.");
		} else {
			Technomancy.logger.warn("Blood Magic compatibility module failed to load.");
			CompatibilityHandler.bm = false;
		}
	}
	
	public static void initBloodMagicRecipes() {
		if(CompatibilityHandler.te){
			//Altar Recipes
			if(Ids.bloodDynamo){
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMBlocks.bloodDynamo), new ItemStack(ThermalExpansion.blockDynamo), 2,
						10000, 100, 100, false);
			}
			if(Ids.matBM){
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 0), new ItemStack(Items.iron_ingot), 1, 1000, 100, 100, false);
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 1), ThermalExpansion.powerCoilGold, 1, 1000, 100, 100, false);
			}

			//Normal Recipes
			GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.bloodFabricator), 
					new Object[] {" T ", "IMI", "CAC",
				'T', new ItemStack(ThermalExpansion.blockTank, 1, 3),
				'I', new ItemStack(TMItems.itemBM, 1, 0),
				'M', ThermalExpansion.frameMachineBasic,
				'C', new ItemStack(TMItems.itemBM, 1, 1),
				'A', ThermalExpansion.frameTesseractFull});
			GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.processorBM),
					new Object[] {" A ", "BMB", "ICI",
				'M', ThermalExpansion.frameMachineBasic,
				'I', new ItemStack(TMItems.itemBM, 1, 0),
				'C', new ItemStack(TMItems.itemBM, 1, 1),
				'B', new ItemStack(bloodRune, 1, 0),
				'A', new ItemStack(Items.redstone)});
		} else {
			//Altar Recipes
			if(Ids.bloodDynamo)
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMBlocks.bloodDynamo), new ItemStack(Blocks.redstone_block, 1), 2, 10000, 100, 100, false);
			if(Ids.matBM){
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 0), new ItemStack(Items.iron_ingot, 1), 1, 1000, 100, 100, false);
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 1), new ItemStack(Items.redstone, 1), 1, 1000, 100, 100, false);
			}

			//Normal Recipes
			GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.bloodFabricator), 
					new Object[] {" T ", "IMI", "CAC",
				'T', new ItemStack(Blocks.glass, 1, 0),
				'I', new ItemStack(TMItems.itemBM, 1, 0),
				'M', new ItemStack(Blocks.redstone_block, 1, 0),
				'C', new ItemStack(TMItems.itemBM, 1, 1),
				'A', new ItemStack(Items.ender_eye, 1, 0)});
			GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.processorBM),
					new Object[] {" A ", "BMB", "ICI",
				'M', new ItemStack(Blocks.redstone_block, 1, 0),
				'I', new ItemStack(TMItems.itemBM, 1, 0),
				'C', new ItemStack(TMItems.itemBM, 1, 1),
				'B', new ItemStack(bloodRune, 1, 0),
				'A', new ItemStack(Items.redstone)});
		}
	}
}