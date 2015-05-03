package theflogat.technomancy.client.gui.tome.render.pages.gearbox;

import java.awt.Color;

import net.minecraftforge.fluids.FluidStack;
import theflogat.technomancy.client.gui.tome.render.pages.PageRecipeLiquidFuel;

public class PageLiquidFuel extends PageRecipeLiquidFuel{
	
	FluidStack[] outputs;
	int[] val;
	
	public PageLiquidFuel(FluidStack[] start, int[] end) {
		outputs = start;
		val = end;
	}

	@Override
	public int[] getValues() {
		return val;
	}

	@Override
	public FluidStack[] getOutputs() {
		return outputs;
	}

	@Override
	public Color getColor() {
		return Color.RED;
	}
}