package theflogat.technomancy.client.gui;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.client.gui.container.ContainerProcessor;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileTCProcessor;
import theflogat.technomancy.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiProcessorTC extends GuiContainer {

    TileTCProcessor processor;

    public GuiProcessorTC(InventoryPlayer inventory, TileTCProcessor tileTCProcessor) {
        super(new ContainerProcessor(inventory, ((TileProcessorBase) tileTCProcessor), 50, 107, 84, 142));

        this.processor = tileTCProcessor;

        xSize = 175;
        ySize = 167;
    }

    private static final ResourceLocation texture = new ResourceLocation(Reference.GUI_TC_PROCESSOR);

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1, 1, 1, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int k = 0;
        if (this.processor.isActive) {
            k = this.processor.getTimeScaled(20);
        }
        drawTexturedModalRect(guiLeft + 74, guiTop + 25, xSize, 0, k, 20);
    }

}
