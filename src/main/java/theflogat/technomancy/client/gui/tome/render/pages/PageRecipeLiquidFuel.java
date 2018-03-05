package theflogat.technomancy.client.gui.tome.render.pages;

import java.awt.Color;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.client.gui.tome.GuiTomeTemplate;

public abstract class PageRecipeLiquidFuel extends PageRender{

	@Override
	public void render(GuiTomeTemplate gui, int left, int top, boolean page2) {
		
		int x = left + (page2 ? 134 : 28);
		int y = top + 16;
		
		GL11.glDisable(GL11.GL_LIGHTING);
		for(int i = 0; i<getOutputs().length; i++){
			if(getOutputs()[i]!=null){
				ItemStack toRender = new ItemStack(getOutputs()[i].getFluid().getBlock());
				gui.getItemRend().renderItemAndEffectIntoGUI(toRender, x, y + i*16);
				gui.getItemRend().renderItemOverlayIntoGUI(gui.getFont(), toRender, x, y, "");
				gui.getFont().drawString(Integer.toString(getValues()[i]) + " tick(s)", x + 16, y + i*16 + 4, getColor().getRGB());
			}
			GL11.glColor3f(1, 1, 1);
		}
	}

	public abstract int[] getValues();

	public abstract FluidStack[] getOutputs();
	
	public abstract Color getColor();
}