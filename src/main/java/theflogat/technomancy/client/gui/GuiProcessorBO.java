package theflogat.technomancy.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.client.gui.container.ContainerProcessor;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import theflogat.technomancy.common.tiles.botania.machines.TileBOProcessor;
import theflogat.technomancy.lib.Reference;

public class GuiProcessorBO extends GuiContainer {

    TileBOProcessor processor;

    public GuiProcessorBO(InventoryPlayer inventory, TileBOProcessor tileBOProcessor) {
        super(new ContainerProcessor(inventory, (TileProcessorBase) tileBOProcessor, 52, 108, 56, 114));

        this.processor = tileBOProcessor;

        xSize = 176;
        ySize = 137;
    }

    private static final ResourceLocation texture = new ResourceLocation(Reference.GUI_BO_PROCESSOR);

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1, 1, 1, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int k = 0;
        if (this.processor.isActive) {
            k = this.processor.getTimeScaled(22);
        }
        drawTexturedModalRect(guiLeft + 75, guiTop + 24, xSize, 0, k, 22);
    }
}
