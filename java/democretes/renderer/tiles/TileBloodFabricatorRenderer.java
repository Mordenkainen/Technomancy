package democretes.renderer.tiles;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import democretes.blocks.TMBlocks;
import democretes.blocks.machines.tiles.TileBloodFabricator;
import democretes.compat.BloodMagic;
import democretes.lib.Ref;
import democretes.renderer.models.ModelBloodFabricator;

public class TileBloodFabricatorRenderer extends TileEntitySpecialRenderer {
	
	ModelBloodFabricator model = new ModelBloodFabricator();
	
	private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_BLOOD_FABRICATOR_TEXTURE);

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float t) {
		if (entity instanceof TileBloodFabricator) {			
			GL11.glPushMatrix();
			GL11.glTranslatef((float)x, (float)y, (float)z);
			GL11.glScalef(-1F, -1F, 1f);
			GL11.glTranslatef(-.5F, -1.5F, .5F);
			renderLiquid(entity, x, y, z, t);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			bindTexture(modelTexture);
			model.render();
		
			GL11.glPopMatrix();
		}
	}
	
	public void renderLiquid(TileEntity entity, double x, double y, double z, float f)  {
	    if (this.tileEntityRenderer.renderEngine == null) {
	      return;
	    }	    
	    TileBloodFabricator fabricator = (TileBloodFabricator)entity;
	    
	    if(fabricator.tank.getFluidAmount() <= 0) {
	    	return;
	    }
	    
	    GL11.glPushMatrix();
	    GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
	    GL11.glTranslatef(0F, -1.5F, 0F);
	    
	    RenderBlocks renderBlocks = new RenderBlocks();
	    
	    GL11.glDisable(2896);
	    
	    float level = ((MathHelper.abs(fabricator.tank.getFluidAmount()/100))/(MathHelper.abs(fabricator.tank.getCapacity()/100))) * 0.75F;
	    
	    Tessellator t = Tessellator.instance;
	    
	    renderBlocks.setRenderBounds(0.26D, 0.24D, 0.26D, 0.76D, 0.24D + level, 0.76D);
	    
	    t.startDrawingQuads();
	    
	    Icon icon = BloodMagic.lifeEssenceFluid.getIcon();
	    
	    this.tileEntityRenderer.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
	    
	    Block tank = TMBlocks.bloodFabricator;

	    renderBlocks.renderFaceYNeg(tank, -0.5D, 0.0D, -0.5D, icon);
	    renderBlocks.renderFaceYPos(tank, -0.5D, 0.0D, -0.5D, icon);
	    renderBlocks.renderFaceZNeg(tank, -0.5D, 0.0D, -0.5D, icon);
	    renderBlocks.renderFaceZPos(tank, -0.5D, 0.0D, -0.5D, icon);
	    renderBlocks.renderFaceXNeg(tank, -0.5D, 0.0D, -0.5D, icon);
	    renderBlocks.renderFaceXPos(tank, -0.5D, 0.0D, -0.5D, icon);
	    
	    t.draw();	    
	    

	    GL11.glEnable(2896);	    
	    GL11.glPopMatrix();
	    
	    GL11.glColor3f(1.0F, 1.0F, 1.0F);
	  }
}
