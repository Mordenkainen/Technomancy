package theflogat.technomancy.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.client.gui.container.ContainerBOProcessor;
import theflogat.technomancy.common.tiles.botania.machines.TileBOProcessor;
import theflogat.technomancy.lib.Ref;

public class GuiProcessorBO extends GuiContainer {

	TileBOProcessor processor;
	public GuiProcessorBO(InventoryPlayer inventory, TileBOProcessor tileBOProcessor) {
		super(new ContainerBOProcessor(inventory, tileBOProcessor));
		
		this.processor = tileBOProcessor;
		
		xSize = 176;
		ySize = 137;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	private static final ResourceLocation texture = new ResourceLocation(Ref.GUI_BO_PROCESSOR_TEXTURE);
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1, 1, 1, 1);		
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);	
		
		int k = 0;
		if(this.processor.isActive) {
			k = this.processor.getTimeScaled(22);
		}
		drawTexturedModalRect(guiLeft+ 75, guiTop + 24, xSize, 0, k, 22);
	}
}
