package theflogat.technomancy.client.tiles;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import thaumcraft.common.blocks.BlockJar;
import thaumcraft.common.tiles.TileJarFillable;
import theflogat.technomancy.client.models.ModelEssentiaContainer;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.lib.Reference;
import theflogat.technomancy.lib.compat.Thaumcraft;

public abstract class TileJarRendererBase extends TileEntitySpecialRenderer {
    
    protected static final ModelEssentiaContainer MODEL = new ModelEssentiaContainer();

    protected static final ResourceLocation MODELTEXTURE = new ResourceLocation(Reference.MODEL_ESS_CONT);

    @Override
    public void renderTileEntityAt(final TileEntity entity, final double x, final double y, final double z, final float t) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glScalef(-1F, -1F, 1f);
        GL11.glTranslatef(-.5F, -1.5F, .5F);
        renderLiquid(entity, x, y, z, t);
        renderLabel(entity, x, y, z, t);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, getColor(), 1.0F, 1.0F);

        bindTexture(MODELTEXTURE);
        MODEL.render();

        GL11.glPopMatrix();
    }

    public void renderLiquid(final TileEntity tile, final double x, final double y, final double z, final float f) {
        final TileJarFillable entity = (TileJarFillable) tile;
    
        if (entity.amount <= 0) {
            return;
        }
    
        GL11.glPushMatrix();
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(0F, -1.5F, 0F);
    
        final RenderBlocks renderBlocks = new RenderBlocks();
    
        GL11.glDisable(2896);
    
        final float level = (MathHelper.abs((entity.amount) / 5) / MathHelper.abs((entity.maxAmount) / 5)) * 0.65F;
    
        final Tessellator t = Tessellator.instance;
    
        renderBlocks.setRenderBounds(0.25D, 0.0625D, 0.25D, 0.75D, 0.0625D + level, 0.75D);
    
        t.startDrawingQuads();
        if (entity.aspect != null) {
            t.setColorOpaque_I(entity.aspect.getColor());
        }
        int bright = 200;
        if (tile.getWorldObj() != null) {
            bright = Math.max(200, Thaumcraft.blockJar.getMixedBrightnessForBlock(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord));
        }
        t.setBrightness(bright);
    
        final IIcon icon = ((BlockJar) Thaumcraft.blockJar).iconLiquid;
    
        bindTexture(TextureMap.locationBlocksTexture);
    
        final Block jar = TMBlocks.creativeJar;
    
        renderBlocks.renderFaceYNeg(jar, -0.5D, 0.0D, -0.5D, icon);
        renderBlocks.renderFaceYPos(jar, -0.5D, 0.0D, -0.5D, icon);
        renderBlocks.renderFaceZNeg(jar, -0.5D, 0.0D, -0.5D, icon);
        renderBlocks.renderFaceZPos(jar, -0.5D, 0.0D, -0.5D, icon);
        renderBlocks.renderFaceXNeg(jar, -0.5D, 0.0D, -0.5D, icon);
        renderBlocks.renderFaceXPos(jar, -0.5D, 0.0D, -0.5D, icon);
    
        t.draw();
    
        GL11.glEnable(2896);
        GL11.glPopMatrix();
    
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }
    
    protected abstract float getColor();
    
    protected abstract void renderLabel(TileEntity tile, double x, double y, double z, float f);

}
