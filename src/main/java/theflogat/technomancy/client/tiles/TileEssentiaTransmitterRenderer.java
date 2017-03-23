package theflogat.technomancy.client.tiles;

import java.awt.Color;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import thaumcraft.client.renderers.models.ModelBoreBase;
import thaumcraft.common.tiles.TileArcaneBoreBase;
import theflogat.technomancy.client.models.ModelTeslaCoil;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaTransmitter;
import theflogat.technomancy.lib.Ref;

public class TileEssentiaTransmitterRenderer extends TileEntitySpecialRenderer {

    ModelTeslaCoil model = new ModelTeslaCoil();
    ModelBoreBase boreModel = new ModelBoreBase();

    private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_ESSENTIA_TRANSMITTER_TEXTURE);
    private static final ResourceLocation boreTexture = new ResourceLocation("thaumcraft", "textures/models/Bore.png");

    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glScalef(-1.0F, -1.0F, 1.0f);
        GL11.glTranslatef(-.5F, -1.5F, .5F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glTranslatef(0.0F, 2.0F, 0.0F);
        renderFacing(entity);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(modelTexture);
        model.render();

        GL11.glPushMatrix();
        if (((TileEssentiaTransmitter) entity).boost) {
            GL11.glColor3d(Color.RED.getRed(), 0, 0);
        }
        model.renderTopRing();
        GL11.glColor3d(1, 1, 1);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        if (((TileEssentiaTransmitter) entity).aspectFilter != null) {
            Color color = new Color(((TileEssentiaTransmitter) entity).aspectFilter.getColor());
            GL11.glColor3d(color.getRed() / 255.0F * 0.2F, color.getGreen() / 255.0F * 0.2F, color.getBlue() / 255.0F * 0.2F);
        }
        model.renderBottomRing();
        GL11.glColor3d(1, 1, 1);
        GL11.glPopMatrix();

        GL11.glPopMatrix();

        ForgeDirection dir = ForgeDirection.getOrientation(((TileEssentiaTransmitter) entity).facing);
        if (entity.getWorldObj() != null && entity.getWorldObj().getTileEntity(entity.xCoord + dir.offsetX, entity.yCoord + dir.offsetY, entity.zCoord + dir.offsetZ) instanceof TileArcaneBoreBase) {
            GL11.glPushMatrix();
            bindTexture(boreTexture);
            GL11.glTranslatef((float) x + 0.5F + dir.offsetX, (float) y + dir.offsetY, (float) z + 0.5F + dir.offsetZ);

            switch (dir.getOpposite().ordinal()) {
                case 0:
                    GL11.glTranslatef(-0.5F, 0.5F, 0.0F);
                    GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);
                    break;
                case 1:
                    GL11.glTranslatef(0.5F, 0.5F, 0.0F);
                    GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
                    break;
                case 2:
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                    break;
                case 3:
                    GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
                    break;
                case 4:
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                    break;
                case 5:
                    GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
            }
            this.boreModel.renderNozzle();
            GL11.glPopMatrix();
        }
    }

    public void renderFacing(TileEntity entity) {
        switch (((TileEssentiaTransmitter) entity).facing) {
            case 0:
                GL11.glTranslatef(0.0F, -2.0F, 0.0F);
                GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
                break;
            case 1:
                GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
                break;
            case 3:
                GL11.glTranslatef(0.0F, -1.0F, -1.0F);
                GL11.glRotatef(90.0F, 1.0F, 0F, 0.0F);
                break;
            case 2:
                GL11.glTranslatef(0.0F, -1.0F, 1.0F);
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                break;
            case 5:
                GL11.glTranslatef(1.0F, -1.0F, 0.0F);
                GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(90.0F, 1.0F, 0F, 0.0F);
                break;
            case 4:
                GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(90.0F, 1.0F, 0F, 0.0F);
        }
    }

}
