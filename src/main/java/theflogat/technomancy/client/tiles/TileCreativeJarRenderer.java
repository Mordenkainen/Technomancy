package theflogat.technomancy.client.tiles;

import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.common.tiles.thaumcraft.storage.TileCreativeJar;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.config.Config;

public class TileCreativeJarRenderer extends TileJarRendererBase {

    protected void renderLabel(final TileEntity tile, final double x, final double y, final double z, final float f) {
        if (((TileCreativeJar) tile).aspectFilter != null) {
            GL11.glPushMatrix();
            switch (((TileCreativeJar) tile).facing) {
                case 2:
                    GL11.glTranslatef(0.0F, 0.0F, -0.63F);
                    break;
                case 3:
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(0.0F, 0.0F, -0.63F);
                    break;
                case 5:
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(0.0F, 0.0F, -0.63F);
                    break;
                case 4:
                    GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(0.0F, 0.0F, -0.63F);
                    break;
                default:
                    break;
            }
            GL11.glTranslatef(0.0F, 1.5F, 0.0F);
            final float rot = (((TileCreativeJar) tile).aspectFilter.getTag().hashCode() + tile.xCoord + ((TileCreativeJar) tile).facing) % 4 - 2;

            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, -0.4F, 0.315F);
            if (Config.crooked) {
                GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
            }
            UtilsFX.renderQuadCenteredFromTexture("textures/models/label.png", 0.5F, 1.0F, 1.0F, 1.0F, -99, 771, 1.0F);
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, -0.4F, 0.31F);
            if (Config.crooked) {
                GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
            }
            GL11.glScaled(0.021D, 0.021D, 0.021D);

            UtilsFX.drawTag(-8, -8, ((TileCreativeJar) tile).aspectFilter);
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }

    @Override
    protected float getColor() {
        return 0.0F;
    }
    
}
