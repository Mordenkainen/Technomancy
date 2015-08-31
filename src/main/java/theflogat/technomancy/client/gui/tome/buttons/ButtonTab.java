package theflogat.technomancy.client.gui.tome.buttons;

import net.minecraft.client.gui.GuiScreen;
import theflogat.technomancy.client.gui.tome.GuiTomeTemplate;

public class ButtonTab {
	
	private int x;
	private int y;
	int u;
	int v;
	int id;
	int sizes = 16;
	ButtonEntry[] entries = new ButtonEntry[20];
	private String name;
	
	public ButtonTab(int x, int y, int u, int v, int id, ButtonEntry[] entries, String name){
		setX(x);
		setY(y);
		this.u = u;
		this.v = v;
		this.id = id;
		this.name = name;
		this.entries = entries;
		int np = 0;
		for(int i=0;i<entries.length;i++){
			ButtonEntry entry = this.entries[i];
			entry.setId(i);
			entry.setYX(np>15 ? 50 : 30, 12 + (np*8));
			np += entry.text.size();
		}
	}
	
	public void draw(int left, int top, GuiScreen gui){
		gui.drawTexturedModalRect(left+getX(), top+getY(), u, v, sizes, sizes);
	}
	
	public boolean isPointInRegion(int mouseX, int mouseY, int left, int top, GuiTomeTemplate gui){
		int posX=mouseX-left;
		int posY=mouseY-top;
		if(getX()<posX && posX<getX()+sizes && getY()<posY && posY<getY()+sizes){
			return true;
		}
		return false;
	}
	
	public ButtonEntry[] getEntries() {
		return entries;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
}
