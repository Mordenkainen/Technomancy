package theflogat.technomancy.lib.compat;

import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import WayofTime.bloodmagic.api.impl.BloodMagicRecipeRegistrar;
import WayofTime.bloodmagic.api.impl.recipe.RecipeBloodAltar;
import WayofTime.bloodmagic.block.BlockLifeEssence;
import WayofTime.bloodmagic.core.RegistrarBloodMagicBlocks;
import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.core.registry.AltarRecipeRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistry;
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

public class BloodMagic extends ModuleBase{
	
	private static BloodMagic instance;
	
	public static Fluid lifeEssenceFluid;
	public static Item divinationSigil;
	public static Item bucketLife;
	public static Item bloodRune;
	public BloodMagicRecipeRegistrar registrar = BloodMagicAPI.INSTANCE.getRecipeRegistrar();

	private BloodMagic() {}
	
	public static BloodMagic getInstance() {
		if(instance == null) {
			instance = new BloodMagic();
		}
		return instance;
	}

	@Override
	public void preInit() {
		regBlock(TMBlocks.bloodDynamo);
		regBlock(TMBlocks.bloodFabricator);
		regBlock(TMBlocks.processorBM);
	}

	@Override
	public void Init() {
		lifeEssenceFluid = BlockLifeEssence.getLifeEssence();
		divinationSigil = RegistrarBloodMagicItems.SIGIL_DIVINATION;
		bucketLife = FluidUtil.getFilledBucket(new FluidStack(BlockLifeEssence.getLifeEssence(), 1000)).getItem();
		bloodRune = Item.getItemFromBlock(RegistrarBloodMagicBlocks.BLOOD_RUNE);

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
        registerItem(TMItems.itemBM);
	}

	@Override
	public void RegisterBlocks() {
		TMBlocks.bloodDynamo = Ids.bloodDynamo ? new BlockBloodDynamo() : null;	
		TMBlocks.bloodFabricator = Ids.bloodFabricator ? new BlockBloodFabricator() : null;
		TMBlocks.processorBM = Ids.processorBM ? new BlockBMProcessor() : null;
		
		registerBlock(TMBlocks.bloodDynamo, Names.bloodDynamo);
		registerBlock(TMBlocks.bloodFabricator, Names.bloodFabricator);
		registerBlock(TMBlocks.processorBM, Names.processor + "bm");
		
		registerTileEntity(TMBlocks.bloodDynamo, TileBloodDynamo.class, "TileBloodDynamo");
		registerTileEntity(TMBlocks.bloodFabricator, TileBloodFabricator.class, "TileBloodFabricator");
		registerTileEntity(TMBlocks.processorBM, TileBMProcessor.class, "TileProcessorBM");
	}

	@Override
	public void RegisterRecipes() {
		if(CompatibilityHandler.te) {
			//Altar Recipes
			if(Ids.bloodDynamo) {
				registrar.addBloodAltar(Ingredient.fromStacks(ThermalExpansion.blockDynamo), new ItemStack(TMBlocks.bloodDynamo), 2,
						10000, 100, 100);
			}
			if(Ids.matBM) {
				registrar.addBloodAltar(Ingredient.fromStacks(new ItemStack(Items.IRON_INGOT)), new ItemStack(TMItems.itemBM, 1, 0), 1, 1000, 100, 100);
				registrar.addBloodAltar(Ingredient.fromStacks(Loader.isModLoaded("thermalexpansion") ? ThermalExpansion.powerCoilGold : new ItemStack(Items.GOLD_INGOT)), new ItemStack(TMItems.itemBM, 1, 1), 1, 1000, 100, 100);
			}

			//Normal Recipes
			if(Ids.bloodFabricator) {
				GameRegistry.addShapedRecipe(TMBlocks.bloodFabricator.getRegistryName(), null, new ItemStack(TMBlocks.bloodFabricator),
						new Object[] {" T ", "IMI", "CAC",
					'T', ThermalExpansion.blockTank[3],
					'I', new ItemStack(TMItems.itemBM, 1, 0),
					'M', ThermalExpansion.frameMachineBasic,
					'C', new ItemStack(TMItems.itemBM, 1, 1),
					'A', ThermalExpansion.frameCellBasic});
			}
			if(Ids.processorBM) {
				GameRegistry.addShapedRecipe(TMBlocks.processorBM.getRegistryName(), null, new ItemStack(TMBlocks.processorBM),
						new Object[] {" A ", "BMB", "ICI",
					'M', ThermalExpansion.frameMachineBasic,
					'I', new ItemStack(TMItems.itemBM, 1, 0),
					'C', new ItemStack(TMItems.itemBM, 1, 1),
					'B', new ItemStack(bloodRune, 1, 0),
					'A', new ItemStack(Items.REDSTONE)});
			}

		} else {
			//Altar Recipes
			if(Ids.bloodDynamo) {
				registrar.addBloodAltar(Ingredient.fromStacks(new ItemStack(Blocks.REDSTONE_BLOCK, 1)), new ItemStack(TMBlocks.bloodDynamo, 1), 2, 10000, 100, 100);
			}
			if(Ids.matBM) {
				registrar.addBloodAltar(Ingredient.fromStacks(new ItemStack(Items.IRON_INGOT, 1)), new ItemStack(TMItems.itemBM, 1, 0), 1, 1000, 100, 100);
				registrar.addBloodAltar(Ingredient.fromStacks(new ItemStack(Items.REDSTONE, 1)), new ItemStack(TMItems.itemBM, 1, 1), 1, 1000, 100, 100);
			}

			//Normal Recipes

			if(Ids.bloodFabricator) {
				GameRegistry.addShapedRecipe(TMBlocks.bloodFabricator.getRegistryName(), null, new ItemStack(TMBlocks.bloodFabricator),
						new Object[] {" T ", "IMI", "CAC",
					'T', new ItemStack(Blocks.GLASS, 1, 0),
					'I', new ItemStack(TMItems.itemBM, 1, 0),
					'M', new ItemStack(Blocks.REDSTONE_BLOCK, 1, 0),
					'C', new ItemStack(TMItems.itemBM, 1, 1),
					'A', new ItemStack(Items.ENDER_EYE, 1, 0)});
			}
			if(Ids.processorBM) {
				GameRegistry.addShapedRecipe(TMBlocks.processorBM.getRegistryName(), null, new ItemStack(TMBlocks.processorBM),
						new Object[] {" A ", "BMB", "ICI",
					'M', new ItemStack(Blocks.REDSTONE_BLOCK, 1, 0),
					'I', new ItemStack(TMItems.itemBM, 1, 0),
					'C', new ItemStack(TMItems.itemBM, 1, 1),
					'B', new ItemStack(bloodRune, 1, 0),
					'A', new ItemStack(Items.REDSTONE)});
			}
		}
	}
}