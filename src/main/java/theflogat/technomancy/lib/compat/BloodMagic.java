package theflogat.technomancy.lib.compat;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.blocks.bloodmagic.dynamos.BlockBloodDynamo;
import theflogat.technomancy.common.blocks.bloodmagic.machines.BlockBMProcessor;
import theflogat.technomancy.common.blocks.bloodmagic.machines.BlockBloodFabricator;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.items.bloodmagic.ItemBMMaterial;
import theflogat.technomancy.common.tiles.bloodmagic.dynamos.TileBloodDynamo;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBMProcessor;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBloodFabricator;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class BloodMagic extends ModuleBase{
	
	private static BloodMagic instance;
	
	public static Fluid lifeEssenceFluid;
	public static Item divinationSigil;
	public static Item bucketLife;
	public static Item bloodRune;

	private BloodMagic() {}
	
	public static BloodMagic getInstance() {
		if(instance == null) {
			instance = new BloodMagic();
		}
		return instance;
	}
	
	@Override
	public void Init() {
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

	@Override
	public void PostInit() {}

	@Override
	public void RegisterItems() {
    	//Initializations
        TMItems.itemBM = Ids.matBM ? new ItemBMMaterial() : null;        
        
        //Registration
        registerItem(TMItems.itemBM, Names.itemBM);
	}

	@Override
	public void RegisterBlocks() {
		TMBlocks.bloodDynamo = Ids.bloodDynamo ? new BlockBloodDynamo() : null;	
		TMBlocks.bloodFabricator = Ids.bloodFabricator ? new BlockBloodFabricator() : null;
		TMBlocks.processorBM = Ids.processorBM ? new BlockBMProcessor() : null;
		
		registerBlock(TMBlocks.bloodDynamo, Names.bloodDynamo);
		registerBlock(TMBlocks.bloodFabricator, Names.bloodFabricator);
		registerBlock(TMBlocks.processorBM, Names.processor + "BM");
		
		registerTileEntity(TMBlocks.bloodDynamo, TileBloodDynamo.class, "TileBloodDynamo");
		registerTileEntity(TMBlocks.bloodFabricator, TileBloodFabricator.class, "TileBloodFabricator");
		registerTileEntity(TMBlocks.processorBM, TileBMProcessor.class, "TileProcessorBM");
	}

	@Override
	public void RegisterRecipes() {
		if(CompatibilityHandler.te) {
			//Altar Recipes
			if(Ids.bloodDynamo) {
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMBlocks.bloodDynamo), new ItemStack(ThermalExpansion.blockDynamo), 2,
						10000, 100, 100, false);
			}
			if(Ids.matBM) {
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 0), new ItemStack(Items.iron_ingot), 1, 1000, 100, 100, false);
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 1), ThermalExpansion.powerCoilGold, 1, 1000, 100, 100, false);
			}

			//Normal Recipes
			if(Ids.bloodFabricator) {
				GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.bloodFabricator), 
						new Object[] {" T ", "IMI", "CAC",
					'T', new ItemStack(ThermalExpansion.blockTank, 1, 3),
					'I', new ItemStack(TMItems.itemBM, 1, 0),
					'M', ThermalExpansion.frameMachineBasic,
					'C', new ItemStack(TMItems.itemBM, 1, 1),
					'A', ThermalExpansion.frameTesseractFull});
			}
			if(Ids.processorBM) {
				GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.processorBM),
						new Object[] {" A ", "BMB", "ICI",
					'M', ThermalExpansion.frameMachineBasic,
					'I', new ItemStack(TMItems.itemBM, 1, 0),
					'C', new ItemStack(TMItems.itemBM, 1, 1),
					'B', new ItemStack(bloodRune, 1, 0),
					'A', new ItemStack(Items.redstone)});
			}
		} else {
			//Altar Recipes
			if(Ids.bloodDynamo) {
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMBlocks.bloodDynamo), new ItemStack(Blocks.redstone_block, 1), 2, 10000, 100, 100, false);
			}
			if(Ids.matBM) {
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 0), new ItemStack(Items.iron_ingot, 1), 1, 1000, 100, 100, false);
				AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 1), new ItemStack(Items.redstone, 1), 1, 1000, 100, 100, false);
			}

			//Normal Recipes
			if(Ids.bloodFabricator) {
				GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.bloodFabricator), 
						new Object[] {" T ", "IMI", "CAC",
					'T', new ItemStack(Blocks.glass, 1, 0),
					'I', new ItemStack(TMItems.itemBM, 1, 0),
					'M', new ItemStack(Blocks.redstone_block, 1, 0),
					'C', new ItemStack(TMItems.itemBM, 1, 1),
					'A', new ItemStack(Items.ender_eye, 1, 0)});
			}
			if(Ids.processorBM) {
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
}