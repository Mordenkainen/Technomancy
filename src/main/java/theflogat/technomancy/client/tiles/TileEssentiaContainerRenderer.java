package theflogat.technomancy.client.tiles;

import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.common.tiles.thaumcraft.storage.TileEssentiaContainer;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.config.Config;

public class TileEssentiaContainerRenderer extends TileJarRendererBase {

    protected void renderLabel(final TileEntity tile, final double x, final double y, final double z, final float f) {
        if (((TileEssentiaContainer) tile).aspectFilter != null) {
            GL11.glPushMatrix();
            GL11.glTranslated(0, 1.5, 0);
            switch (((TileEssentiaContainer) tile).facing) {
                case 2:
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                    break;
                case 4:
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                    break;
                case 5:
                    GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
                    break;
                default:
                    break;
            }

            final float rot = (((TileEssentiaContainer) tile).aspectFilter.getTag().hashCode() + ((TileEssentiaContainer) tile).xCoord + ((TileEssentiaContainer) tile).facing) % 4 - 2;

            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, -0.4F, 0.315F);
            if (Config.crooked) {
                GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
            }
            GL11.glRotatef(180F, 0F, 1F, 0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            UtilsFX.renderQuadCenteredFromTexture("textures/models/label.png", 0.5F, 1.0F, 1.0F, 1.0F, -99, 771, 1.0F);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glRotatef(180F, 0F, 1F, 0F);
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, -0.4F, 0.316F);
            if (Config.crooked) {
                GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
            }
            GL11.glScaled(0.021D, 0.021D, 0.021D);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);

            UtilsFX.drawTag(-8, -8, ((TileEssentiaContainer) tile).aspectFilter);
            GL11.glPopMatrix();

            GL11.glPopMatrix();
        }
    }
    
    @Override
    protected float getColor() {
        return 1.0F;
    }

}
