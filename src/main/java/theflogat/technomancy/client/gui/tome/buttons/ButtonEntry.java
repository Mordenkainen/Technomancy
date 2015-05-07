package theflogat.technomancy.client.gui.tome.buttons;

import java.awt.Color;

import theflogat.technomancy.client.gui.tome.GuiTomeTemplate;
import theflogat.technomancy.client.gui.tome.render.pages.Page;



public class ButtonEntry {
		
	private int x;
	private int y;
	int id;
	String text;
	int xsize;
	int ysize = 6;
	Page[] allPages;
	int currentPage;
	
	public ButtonEntry(int x, int y, String txt, int id, Page[] pages){
		setX(x);
		setY(y);
		text=txt;
		this.id = id;
		xsize=txt.length()*5;
		allPages = pages;
	}
		
	private void setY(int y2) {
		y=y2;	
	}

	private void setX(int x2) {
		x=x2;
	}

	public void draw(int left, int top, GuiTomeTemplate gui, boolean isHovering){
		gui.getFont().drawString(text, left+x, top+y, isHovering ? Color.blue.getRGB():0);
	}
	
	public boolean isPointInRegion(int mouseX, int mouseY, int left, int top, GuiTomeTemplate gui){
		int posX=mouseX-left;
		int posY=mouseY-top;
		if(getX()<=posX && posX<=getX()+xsize && getY()<=posY && posY<=getY()+ysize){
			return true;
		}
		return false;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void drawPage(GuiTomeTemplate gui, int left, int top, int activePage){
		try{
			allPages[activePage*2].draw(gui, left, top, false);
			allPages[activePage*2 + 1].draw(gui, left, top, true);
		} catch (NullPointerException e){
			//System.out.println("Error, this is not fatal. Report to Mod Author of Tomes");
		} catch(ArrayIndexOutOfBoundsException e ){
			
		}
	}
	
	public int getId() {
		return id;
	}
}
