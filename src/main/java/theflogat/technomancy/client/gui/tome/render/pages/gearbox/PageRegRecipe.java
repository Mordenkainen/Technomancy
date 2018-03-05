package theflogat.technomancy.client.gui.tome.render.pages.gearbox;

import net.minecraft.item.ItemStack;
import theflogat.technomancy.client.gui.tome.render.pages.PageRecipe;

public class PageRegRecipe extends PageRecipe{

	ItemStack output;
	ItemStack[] pattern = new ItemStack[9];

	public PageRegRecipe(ItemStack out) {
		out.setCount(1);
		output = out;

//		List list = CraftingManager.getInstance().getRecipeList();
//
//		for(Object rec : list){
//			//String id = "";
//			if(rec instanceof ShapedRecipes){
//				ShapedRecipes recipe = (ShapedRecipes) rec;
//				//id = OreDictionary.getOreName(OreDictionary.getOreID(recipe.getRecipeOutput()));
//				ItemStack found = recipe.getRecipeOutput();
//				found.stackSize = 1;
//
//				System.out.println("ShapedRecipes");
//				if(found.getItem() == out.getItem()){
//					pattern = recipe.recipeItems;
//					System.out.println("Found");
//					return;
//				}
//			}
//
//			if(rec instanceof ShapelessRecipes){
//				ShapelessRecipes recipe = (ShapelessRecipes) rec;
//				//id = OreDictionary.getOreName(OreDictionary.getOreID(recipe.getRecipeOutput()));
//				ItemStack found = recipe.getRecipeOutput();
//				found.stackSize = 1;
//
//				System.out.println("ShapelessRecipes");
//				if(ItemStack.areItemStacksEqual(found, out)){
//					for(int i = 0; i<list.size(); i++){
//						ItemStack in = (ItemStack) recipe.recipeItems.get(i);
//						pattern[i] = in;
//					}
//
//					System.out.println("Found");
//					return;
//				}
//			}
//		}
//		System.out.println("Not Found");
	}

	@Override
	public ItemStack[] getRecipe() {
		return pattern;
	}

	@Override
	public ItemStack getOutput() {
		return output;
	}

}
