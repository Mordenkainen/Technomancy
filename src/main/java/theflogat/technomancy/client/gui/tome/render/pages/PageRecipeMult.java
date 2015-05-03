package theflogat.technomancy.client.gui.tome.render.pages;

import net.minecraft.item.ItemStack;

public class PageRecipeMult extends PageRecipe{
	
	int count = 0;
	int pos = 0;
	ItemStack[] outputs;
	ItemStack[][] recipes;
	
	public PageRecipeMult(ItemStack[] outputs, ItemStack[][] recipes) {
		this.outputs = outputs;
		this.recipes = recipes;
	}

	@Override
	public ItemStack[] getRecipe() {
		return recipes[getPos(recipes.length)];
	}

	@Override
	public ItemStack getOutput() {
		return outputs[getPos(outputs.length)];
	}
	
	private int getPos(int length) {
		if(System.currentTimeMillis()%length==0){
			count++;
		}
		if(count==60){
			count = 0;
			pos += pos+1==length ? -pos : 1;
		}
		return pos;
	}

}