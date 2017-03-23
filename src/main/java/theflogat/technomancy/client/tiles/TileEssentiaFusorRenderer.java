package theflogat.technomancy.client.tiles;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import thaumcraft.client.lib.UtilsFX;
import theflogat.technomancy.client.models.ModelEssentiaFusor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaFusor;
import theflogat.technomancy.lib.Ref;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEssentiaFusorRenderer extends TileEntitySpecialRenderer {

    private static final ModelEssentiaFusor model = new ModelEssentiaFusor();
    private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_FUSOR_TEXTURE);
    private static final HashMap<ForgeDirection, float[]> offsets = new HashMap<ForgeDirection, float[]>();

    static {
        offsets.put(ForgeDirection.NORTH, new float[] { .5F, .76F, .13F });
        offsets.put(ForgeDirection.SOUTH, new float[] { .5F, .76F, .87F });
        offsets.put(ForgeDirection.EAST, new float[] { .87F, .76F, .5F });
        offsets.put(ForgeDirection.WEST, new float[] { .13F, .76F, .5F });
    }

    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float t) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        bindTexture(modelTexture);
        model.render();
        GL11.glPopMatrix();

        float ticks = Minecraft.getMinecraft().renderViewEntity.ticksExisted + t;
        if (((TileEssentiaFusor) entity).getOutputAspect() != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y + .76F, (float) z + 0.5F);
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(ticks % 360.0F, 0.0F, 0.0F, 1.0F);
            GL11.glScaled(0.024D, 0.024D, 0.024D);
            UtilsFX.drawTag(-8, -8, ((TileEssentiaFusor) entity).getOutputAspect(), 0.0F, 0, 0.0D, 1, 0.8F, false);
            GL11.glPopMatrix();
        }

        for (ForgeDirection dir : offsets.keySet()) {
            ItemStack is = ((TileEssentiaFusor) entity).getItemForSlot(dir);
            if (is != null) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float) x + offsets.get(dir)[0], (float) y + offsets.get(dir)[1], (float) z + offsets.get(dir)[2]);
                GL11.glRotatef(ticks % 360.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScaled(0.65D, 0.65D, 0.65D);
                EntityItem entityitem = new EntityItem(entity.getWorldObj(), 0.0D, 0.0D, 0.0D, is);
                entityitem.hoverStart = 0.0F;
                RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                GL11.glPopMatrix();
            }
        }
    }

}
