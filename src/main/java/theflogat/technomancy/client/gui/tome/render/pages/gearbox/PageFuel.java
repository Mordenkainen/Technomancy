package theflogat.technomancy.client.gui.tome.render.pages.gearbox;

import java.awt.Color;

import net.minecraft.item.ItemStack;
import theflogat.technomancy.client.gui.tome.render.pages.PageRecipeFuel;

public class PageFuel extends PageRecipeFuel{
	
	ItemStack[] outputs;
	int[] val;
	
	public PageFuel(ItemStack[] start, int[] end) {
		outputs = start;
		val = end;
	}

	@Override
	public int[] getValues() {
		return val;
	}

	@Override
	public ItemStack[] getOutputs() {
		return outputs;
	}

	@Override
	public Color getColor() {
		return Color.RED;
	}
}