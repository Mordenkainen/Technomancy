package democretes.blocks.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import democretes.blocks.gui.container.ContainerBOProcessor;
import democretes.blocks.machines.tiles.TileBOProcessor;

public class GuiProcessorBO extends GuiContainer {

	TileBOProcessor processor;
	public GuiProcessorBO(InventoryPlayer inventory, TileBOProcessor tileBOProcessor) {
		super(new ContainerBOProcessor(inventory, tileBOProcessor));
		
		this.processor = tileBOProcessor;
		
		xSize = 176;
		ySize = 137;
	}

	private static final ResourceLocation texture = new ResourceLocation("technom", "textures/gui/processorBO.png");
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1, 1, 1, 1);		
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);	
		
		int k = 0;
		if(this.processor.isActive()) {
			k = this.processor.getTimeScaled(22);
		}
		drawTexturedModalRect(guiLeft+ 75, guiTop + 24, xSize, 0, k, 22);
	}
}
