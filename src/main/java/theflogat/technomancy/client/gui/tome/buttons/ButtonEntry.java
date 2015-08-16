package theflogat.technomancy.client.gui.tome.buttons;

import java.awt.Color;
import java.util.ArrayList;
import theflogat.technomancy.client.gui.tome.GuiTomeTemplate;
import theflogat.technomancy.client.gui.tome.render.pages.Page;



public class ButtonEntry {
		
	private int x = 0;
	private int y = 0;
	int id;
	ArrayList<String> text;
	int xsize = 0;
	int ysize = 6;
	Page[] allPages;
	int currentPage;
	
	public ButtonEntry(ArrayList<String> txt, Page[] pages){
		text=txt;
		ysize += 8*(txt.size() - 1);
		allPages = pages;
	}
	
	public ButtonEntry(String txt, Page[] pages){
		text = new ArrayList<String>();
		text.add(txt);
		allPages = pages;
	}
	
	public void setYX(int x2, int y2){
		x=x2;
		y=y2;
	}
	
	public void setId(int newId){
		id = newId;
	}

	public void draw(int left, int top, GuiTomeTemplate gui, boolean isHovering){
		for(int i=0; i<text.size();i++){
			gui.getFont().drawString(text.get(i), left+x, top+y+(i*8), isHovering ? Color.blue.getRGB():0);
		}
	}
	
	public boolean isPointInRegion(int mouseX, int mouseY, int left, int top, GuiTomeTemplate gui){
		if(xsize==0){
			for(String s : text){
				xsize = Math.max(gui.getFont().getStringWidth(s), xsize);
			}
		}
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
			//System.out.println("Error, this is not fatal. Report to Mod Author");
		} catch(ArrayIndexOutOfBoundsException e ){
			
		}
	}
	
	public int getId() {
		return id;
	}
}
