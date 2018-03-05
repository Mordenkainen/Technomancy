package theflogat.technomancy.client.gui.tome;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.client.gui.tome.buttons.ButtonChangePage;
import theflogat.technomancy.client.gui.tome.buttons.ButtonEntry;
import theflogat.technomancy.client.gui.tome.buttons.ButtonTab;
import theflogat.technomancy.lib.Ref;

public class GuiTomeTemplate extends GuiScreen{

	private final ResourceLocation background;
	private final ResourceLocation buttons;
	private static final ResourceLocation page = new ResourceLocation(Ref.MOD_ID.toLowerCase(), "textures/gui/nextPrevious.png");
	public static ButtonTab[] tabs;
	private static final ButtonChangePage[] pageCh = new ButtonChangePage[2];
	private final RenderItem itemRenders = this.itemRender;
	private int xSize;
	private int ySize;
	private int activeTab = -1;
	private int activeEntry = -1;
	private int activePage = 0;
	public int startText = 30;
	public int startTextY = 12;
	
	public GuiTomeTemplate(String id) {
		background = new ResourceLocation(Ref.MOD_ID.toLowerCase(), "textures/gui/tomeBack" + id + ".png");
		buttons = new ResourceLocation(Ref.MOD_ID.toLowerCase(), "textures/gui/tomeButtons" + id + ".png");
		
		xSize = 256;
		ySize = 256;
	}
	
	static {
		pageCh[0] = new ButtonChangePage(18, 230, 0, 0, 1);
		pageCh[1] = new ButtonChangePage(220, 230, 16, 0, -1);
	}
	
	@Override
	public void drawScreen(int x, int y, float f) {
		int left = (width - xSize) / 2;
		int top = (height - ySize) / 2;
		
		drawBackground(left, top);
		drawButtons(left, top);
		
		if(activeEntry!=-1){
			ButtonEntry entry = tabs[activeTab].getEntries()[activeEntry];
			GL11.glDisable(GL11.GL_LIGHTING);
			entry.drawPage(this, left, top, activePage);
			
			for(ButtonChangePage change : pageCh){
				if(change.isPointInRegion(x, y, left, top, this)){
					GL11.glDisable(GL11.GL_LIGHTING);
					change.drawHover(left, top, this, page);
				} else {
					GL11.glDisable(GL11.GL_LIGHTING);
					change.draw(left, top, this, page);
				}
			}
		}else{
			if(activeTab!=-1){
				GL11.glDisable(GL11.GL_LIGHTING);
				drawTabs(left, top, activeTab, x, y);
			}
		}
		
		mc.renderEngine.bindTexture(buttons);
		try{
			for(ButtonTab tab: tabs){
				if(tab.isPointInRegion(x, y, left, top, this)){
					drawHoveringButton(left, top, tab.getX(), tab.getY());
					//drawRect(x, y, tab.getName().length() * 5, 6, Color.black.getRGB());
					fontRenderer.drawString(tab.getName(), x, y, Color.white.getRGB());
				}
			}
		}catch(Exception e){}
	}
	
	
	public void drawBackground(int left, int top) {
		mc.renderEngine.bindTexture(background);
		drawTexturedModalRect(left, top, 0, 0, xSize, ySize);
	}
	
	public void drawButtons(int left, int top){
		mc.renderEngine.bindTexture(buttons);
		try{
			for(ButtonTab tab: tabs){
				tab.draw(left, top, this);
			}
		}catch(Exception e){}
	}
	
	public void drawTabs(int left, int top, int tabA, int mouseX, int mouseY){
		mc.renderEngine.bindTexture(buttons);
		for(ButtonEntry entry :tabs[tabA].getEntries()){
			entry.draw(left, top, this, entry.isPointInRegion(mouseX, mouseY, left, top, this));
		}
	}
	
	public void drawHoveringButton(int left, int top, int ml, int mt){
		drawTexturedModalRect(left+ml, top+mt, 16, 0,16,16);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	protected void mouseClicked(int x, int y, int par3) {
		int left = (width - xSize) / 2;
		int top = (height - ySize) / 2;
		for(ButtonTab tab:tabs){
			if(tab.isPointInRegion(x, y, left, top, this)){
				activeTab = tab.getId();
				activeEntry = -1;
				activePage = 0;
			}
		}
		if(activeTab!=-1){
			if(activeEntry==-1){
				for(ButtonEntry entry : tabs[activeTab].getEntries()){
					if(entry.isPointInRegion(x, y, left, top, this)){
						activeEntry = entry.getId();
					}
				}
			} else {
				for(ButtonChangePage change: pageCh){
					if(change.isPointInRegion(x, y, left, top, this)){
						activePage += change.getId();
						if(activePage<0){
							activePage = 0;
						}
//						System.out.println(activePage);
					}
				}
				
				tabs[activeTab].getEntries()[activeEntry].drawPage(this, left, top, activePage);
			}
		}
	}
	
	public FontRenderer getFont(){
		return fontRenderer;
	}
	
	public ResourceLocation getChangePage() {
		return page;
	}
	
	public RenderItem getItemRend(){
		return itemRender;
	}
	
	public Minecraft getMinecraft(){
		return mc;
	}
}
