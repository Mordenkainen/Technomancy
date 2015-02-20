package theflogat.technomancy.client.renderers.gui.tome.render.pages;

import java.awt.Color;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.client.renderers.gui.tome.GuiTomeTemplate;


public class Page {
	
	public enum Type{
		IMAGE,
		TEXT,
		HANDLER;
	}
	
	private Type type;
	ResourceLocation image;
	int maxChar = 16*6;
	int maxLine = 27;
	String str;
	PageRender pageRend;
	int u;
	int v;
	
	public Page(Type type){
		this.type = type;
	}
	
	public Page(Type type, PageRender rend){
		this.type = type;
		pageRend = rend;
	}
	
	public Page(Type type, int u2, int v2){
		this.type = type;
		u = u2;
		v = v2;
	}
	
	public void setImage(ResourceLocation loc) {
		image = loc;
	}
	
	public void addText(String txt){
		str = txt;
	}
	
	public Type getType() {
		return type;
	}
	
	public void draw(GuiTomeTemplate gui, int left, int top, boolean page2) {
		switch(getType()){
		case IMAGE:
			
			GL11.glDisable(GL11.GL_LIGHTING);
			gui.getMinecraft().renderEngine.bindTexture(image);
			gui.drawTexturedModalRect(left + 5, top, u, v, 256, 256);
			
			break;
		case TEXT:
			gui.getFont().drawSplitString(str, page2 ? left + 136: left + gui.startText, top + gui.startTextY,  page2 ? 100 : 100, Color.BLACK.getRGB());
//			int actualLine = 0;
//			int actualInd = 0;
//			String[] words = str.split(" ");
//			for(String s : words){
//								
//				if(actualInd + s.length()* 5.95<maxChar){
//					
//					gui.getFont().drawString(s, (int) ((page2 ? left + 136: left + gui.startText) + actualInd), top + gui.startTextY + 9 * actualLine, 0);
//					
//					actualInd += s.length() * 6;
//					gui.getFont().drawString(" ", (int) ((page2 ? left + 136: left + gui.startText) + actualInd), top + gui.startTextY + 9 * actualLine, 0);
//					actualInd += 3;
//				} else if(actualInd + s.length()* 6==maxChar){
//					
//					gui.getFont().drawString(s, (int) ((page2 ? left + 136: left + gui.startText) + actualInd), top + gui.startTextY + 9 * actualLine, 0);
//					gui.getFont().drawString(" ", (page2 ? left + 136: left + gui.startText)+ actualInd + s.length() + 1, top + gui.startTextY + 9 * actualLine, 0);
//					actualLine++;
//					actualInd = 0;
//					
//				} else {
//					
//					actualLine++;
//					actualInd = 0;
//					gui.getFont().drawString(s, (page2 ? left + 136: left + gui.startText), top + gui.startTextY + 9 * actualLine, 0);
//					
//					actualInd += s.length() * 6;
//					gui.getFont().drawString(" ", page2 ? left + 136: left + gui.startText, top + gui.startTextY + 9 * actualLine, 0);
//					actualInd += 3;
//				}
//			}
			break;
		case HANDLER:
			pageRend.render(gui, left, top, page2);
			break;
		}
	}
}
