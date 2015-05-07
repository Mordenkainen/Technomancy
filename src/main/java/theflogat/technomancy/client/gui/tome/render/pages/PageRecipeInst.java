package theflogat.technomancy.client.gui.tome.render.pages;

import net.minecraft.item.ItemStack;

public class PageRecipeInst extends PageRecipe{

	ItemStack[] rec;
	ItemStack out;
	
	public PageRecipeInst(ItemStack out, ItemStack[] rec) {
		this.rec = rec;
		this.out = out;
	}
	
	@Override
	public ItemStack[] getRecipe() {
		return rec;
	}

	@Override
	public ItemStack getOutput() {
		return out;
	}
}
