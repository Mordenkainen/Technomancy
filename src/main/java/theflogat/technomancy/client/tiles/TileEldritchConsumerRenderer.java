package theflogat.technomancy.client.tiles;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.client.models.ModelEldritchConsumer;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEldritchConsumer;
import theflogat.technomancy.lib.Reference;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEldritchConsumerRenderer extends TileEntitySpecialRenderer {

    public static final ModelEldritchConsumer MODEL = new ModelEldritchConsumer();
    public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODEL_ELD_CONS);

    @Override
    public void renderTileEntityAt(final TileEntity te, final double x, final double y, final double z, final float f) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glScalef(-1F, -1F, 1F);

        bindTexture(TEXTURE);
        MODEL.render(0.0625F, ((TileEldritchConsumer) te).panelRotation, ((TileEldritchConsumer) te).cooldown > 0);

        GL11.glPopMatrix();

    }

}
