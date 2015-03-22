package theflogat.technomancy.lib.compat;

import java.util.Random;

import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import vazkii.botania.api.BotaniaAPI;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class Botania {

	public static void initBotaniaRecipes() {
		if(CompatibilityHandler.te){
			//ManaInfusion
			BotaniaAPI.registerManaInfusionRecipe(new ItemStack(TMItems.itemBO, 1, 0), ThermalExpansion.powerCoilSilver, 3000);		

			//Normal Recipes
			oreDictRecipe(new ItemStack(TMItems.itemBO, 1, 1),
					new Object[] {" M ", "MIM", " M ",
				'M', "ingotManasteel",
				'I', "ingotIron"		});
			oreDictRecipe(new ItemStack(TMBlocks.flowerDynamo), 
					new Object[] {" C ", "GIG", "IWI",
				'W', new ItemStack(Items.redstone),
				'C', new ItemStack(TMItems.itemBO, 1, 0),
				'G', new ItemStack(TMItems.itemBO, 1, 1),
				'I', "ingotManasteel"				});
			oreDictRecipe(new ItemStack(TMBlocks.manaFabricator), 
					new Object[] {"CDC", "IDI", " P ",
				'C', new ItemStack(TMItems.itemBO, 1, 1),
				'I', "ingotManasteel",
				'D', "manaDiamond",
				'P', ThermalExpansion.frameTesseractFull			});
			oreDictRecipe(new ItemStack(TMBlocks.processorBO),
					new Object[] {" A ", "BMB", "ICI",
				'M', ThermalExpansion.frameMachineBasic,
				'I', "ingotManasteel",
				'C', new ItemStack(TMItems.itemBO, 1, 0),
				'B', "livingrock",
				'A', new ItemStack(Items.redstone)				});
		}else{
			//ManaInfusion
			BotaniaAPI.registerManaInfusionRecipe(new ItemStack(TMItems.itemBO, 1, 0), new ItemStack(Items.redstone), 3000);		

			//Normal Recipes
			oreDictRecipe(new ItemStack(TMItems.itemBO, 1, 1),
					new Object[] {" M ", "MIM", " M ",
				'M', "ingotManasteel",
				'I', "ingotIron"		});
			oreDictRecipe(new ItemStack(TMBlocks.flowerDynamo), 
					new Object[] {" C ", "GIG", "IWI",
				'W', new ItemStack(Items.redstone),
				'C', new ItemStack(TMItems.itemBO, 1, 0),
				'G', new ItemStack(TMItems.itemBO, 1, 1),
				'I', "ingotManasteel"				});
			oreDictRecipe(new ItemStack(TMBlocks.manaFabricator), 
					new Object[] {"CDC", "IDI", " P ",
				'C', new ItemStack(TMItems.itemBO, 1, 1),
				'I', "ingotManasteel",
				'D', "manaDiamond",
				'P', new ItemStack(Items.ender_eye, 1, 0)			});
			oreDictRecipe(new ItemStack(TMBlocks.processorBO),
					new Object[] {" A ", "BMB", "ICI",
				'M', new ItemStack(Items.redstone),
				'I', "ingotManasteel",
				'C', new ItemStack(TMItems.itemBO, 1, 0),
				'B', "livingrock",
				'A', new ItemStack(Items.redstone)				});
		}
	}
	
	@SuppressWarnings("unchecked")
	private static IRecipe oreDictRecipe(ItemStack res, Object[] params) {
		IRecipe rec = new ShapedOreRecipe(res, params);
		CraftingManager.getInstance().getRecipeList().add(rec);
		return rec;
	}
	
	public static void sparkle(World world, double d, double d1, double f, Random r){
		BotaniaAPI.internalHandler.sparkleFX( world, d, d1, f, r.nextFloat(), r.nextFloat(), 1.0F, r.nextFloat() * 4, 10);
	}
}
