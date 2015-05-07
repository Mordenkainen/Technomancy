package theflogat.technomancy.client.gui.tome.buttons;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.client.gui.tome.GuiTomeTemplate;

public class ButtonChangePage {
	
	private int x;
	private int y;
	int u;
	int v;
	int u2;
	int v2;
	int id;
	int sizesX = 16;
	int sizesY = 10;
	
	public ButtonChangePage(int x, int y, int u, int v, int id){
		setX(x);
		setY(y);
		this.u = u;
		this.v = v;
		u2 = u;
		v2 = v + sizesY;
		this.id = id;
	}
	
	public void draw(int left, int top, GuiTomeTemplate gui, ResourceLocation loc){
		GL11.glColor3d(1, 1, 1);
		gui.getMinecraft().renderEngine.bindTexture(loc);
		gui.drawTexturedModalRect(left+x, top+y, u, v, sizesX, sizesY);
	}
	
	public void drawHover(int left, int top, GuiTomeTemplate gui, ResourceLocation loc){
		GL11.glColor3d(1, 1, 1);
		gui.getMinecraft().renderEngine.bindTexture(loc);
		gui.drawTexturedModalRect(left+x, top+y, u2, v2, sizesX, sizesY);
	}
	
	public boolean isPointInRegion(int mouseX, int mouseY, int left, int top, GuiTomeTemplate gui){
		int posX=mouseX-left;
		int posY=mouseY-top;
		if(getX()<posX && posX<getX()+sizesX && getY()<posY && posY<getY()+sizesY){
			return true;
		}
		return false;
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

	public int getId() {
		return id;
	}
}

