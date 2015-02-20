package theflogat.technomancy.client.renderers.gui.tome.render.pages;

import net.minecraft.item.ItemStack;

public class PageRecipeMult extends PageRecipe{
	
	ItemStack[] outputs;
	ItemStack[][] recipes;
	
	public PageRecipeMult(ItemStack[] outputs, ItemStack[][] recipes) {
		this.outputs = outputs;
		this.recipes = recipes;
	}

	@Override
	public ItemStack[] getRecipe() {
		return recipes[(((int)System.currentTimeMillis())%(recipes.length*10))/10];
	}

	@Override
	public ItemStack getOutput() {
		return outputs[(((int)System.currentTimeMillis())%(recipes.length*10))/10];
	}

}
