package theflogat.technomancy.client.tiles;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import thaumcraft.common.tiles.TileTube;
import theflogat.technomancy.client.models.ModelFluxLamp;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileFluxLamp;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.util.helpers.WorldHelper;

public class TileFluxLampRenderer extends TileEntitySpecialRenderer {

    ModelFluxLamp model = new ModelFluxLamp();

    private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_FLUX_LAMP_TEXTURE);

    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glScalef(-1F, -1F, 1f);
        GL11.glTranslated(-0.5F, -1.5F, 0.5F);
        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        bindTexture(modelTexture);

        if (((TileFluxLamp) entity).placed) {
            renderNozzles(entity);
        }

        GL11.glPushMatrix();
        if (((TileFluxLamp) entity).tank.getFluidAmount() > 0) {
            GL11.glColor3f(((TileFluxLamp) entity).tank.getFluidAmount(), 0.0F, ((TileFluxLamp) entity).tank.getFluidAmount());
        }
        model.renderCore();
        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }

    public void renderNozzles(TileEntity entity) {
        if (entity.getWorldObj().getTileEntity(entity.xCoord, entity.yCoord + 1, entity.zCoord) instanceof TileTube) {
            model.renderTop();
        }
        for (int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
            if (WorldHelper.isAdjacentFluidHandler(entity, (byte) i)) {
                switch (i) {
                    case 0:
                        model.renderBottom();
                        break;
                    case 2:
                        model.renderLeft();
                        break;
                    case 3:
                        model.renderRight();
                        break;
                    case 4:
                        model.renderFront();
                        break;
                    case 5:
                        model.renderBack();
                }
            }
        }
    }

}
