package theflogat.technomancy.client.gui.tome.render.pages;

import java.awt.Color;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.client.gui.tome.GuiTomeTemplate;


public class Page {

	public enum Type{
		IMAGE,
		TEXT,
		HANDLER;
	}

	Color color = Color.BLACK;
	private Type type;
	ResourceLocation image;
	int maxLine = 27;
	boolean lines = false;
	String str = "";
	PageRender pageRend;
	int u;
	int v;
	int offsetX;
	int offsetY;

	public Page(Type type){
		this.type = type;
	}

	public Page(Type type, PageRender rend){
		this.type = type;
		pageRend = rend;
	}

	public Page(Type type, ResourceLocation img){
		this.type = type;
		image = img;
	}
	
	public void setUAndV(int u, int v) {
		this.u = u;
		this.v = v;
	}
	
	public void setImageOffsets(int x, int y) {
		offsetX = x;
		offsetY = y;
	}
	
	public void addText(String txt){
		str = txt;
	}

	public void addLines(String[] txt){
		for(String t : txt){
			str += "É " + t;
		}
		lines = true;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Type getType() {
		return type;
	}

	public void draw(GuiTomeTemplate gui, int left, int top, boolean page2) {
		switch(getType()){
		case IMAGE:

			GL11.glDisable(GL11.GL_LIGHTING);
			gui.getMinecraft().renderEngine.bindTexture(image);
			gui.drawTexturedModalRect(left + 5 + offsetX, top + offsetY, u, v, 256, 256);

			break;
		case TEXT:
			int maxLength = 100;
			int spaceW = gui.getFont().getCharWidth(' ');
			int actualLine = 0;
			int actualInd = 0;
			if(lines){
				String[] sent = str.split("É ");
				for(String str : sent){
					String[] words = str.split(" ");
					for(String s : words){
						if(actualInd + gui.getFont().getStringWidth(s)<=maxLength){
							gui.getFont().drawString(s, (page2 ? left + 136: left + gui.startText) + actualInd,
									top + gui.startTextY + actualLine*(gui.getFont().FONT_HEIGHT+1), color.getRGB());
							actualInd += gui.getFont().getStringWidth(s) + spaceW;
						} else {
							actualLine++;
							actualInd = 0;
							gui.getFont().drawString(s, (page2 ? left + 136: left + gui.startText) + actualInd,
									top + gui.startTextY + actualLine*(gui.getFont().FONT_HEIGHT+1), color.getRGB());
							actualInd += gui.getFont().getStringWidth(s) + spaceW;
						}
					}
					actualLine++;
					actualInd = 0;
				}
			}else{
				String[] words = str.split(" ");
				for(String s : words){
					if(actualInd + gui.getFont().getStringWidth(s)<=maxLength){
						gui.getFont().drawString(s, (page2 ? left + 136: left + gui.startText) + actualInd,
								top + gui.startTextY + actualLine*(gui.getFont().FONT_HEIGHT+1), color.getRGB());
						actualInd += gui.getFont().getStringWidth(s) + spaceW;
					} else {
						actualLine++;
						actualInd = 0;
						gui.getFont().drawString(s, (page2 ? left + 136: left + gui.startText) + actualInd,
								top + gui.startTextY + actualLine*(gui.getFont().FONT_HEIGHT+1), color.getRGB());
						actualInd += gui.getFont().getStringWidth(s) + spaceW;
					}
				}
			}
			break;
		case HANDLER:
			pageRend.render(gui, left, top, page2);
			break;
		}
	}
}
