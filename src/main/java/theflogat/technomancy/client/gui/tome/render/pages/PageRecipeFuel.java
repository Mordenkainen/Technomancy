package theflogat.technomancy.client.gui.tome.render.pages;

import java.awt.Color;

import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.client.gui.tome.GuiTomeTemplate;

public abstract class PageRecipeFuel extends PageRender{

	@Override
	public void render(GuiTomeTemplate gui, int left, int top, boolean page2) {
		
		int x = left + (page2 ? 134 : 28);
		int y = top + 16;
		
		GL11.glDisable(GL11.GL_LIGHTING);
		for(int i = 0; i<getOutputs().length; i++){
			if(getOutputs()[i]!=null){
				boolean isTrans = getOutputs()[i].getTagCompound() != null && getOutputs()[i].getTagCompound().getBoolean("trans");
				if(isTrans){GL11.glEnable(GL11.GL_LIGHTING);}
				gui.getItemRend().renderItemAndEffectIntoGUI(getOutputs()[i], x, y + i*16);
				gui.getItemRend().renderItemOverlayIntoGUI(gui.getFont(), getOutputs()[i], x, y, "");
				gui.getFont().drawString(Integer.toString(getValues()[i]) + " tick(s)", x + 16, y + i*16 + 4, getColor().getRGB());
				if(isTrans){GL11.glDisable(GL11.GL_LIGHTING);}
			}
			GL11.glColor3f(1, 1, 1);
		}
	}

	public abstract int[] getValues();

	public abstract ItemStack[] getOutputs();
	
	public abstract Color getColor();
}