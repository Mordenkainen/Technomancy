package theflogat.technomancy.client.tiles;

import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.client.models.ModelReconstructor;
import theflogat.technomancy.lib.Ref;

public class TileReconstructorRenderer extends TileEntitySpecialRenderer{

	ModelReconstructor model = new ModelReconstructor();

	public TileReconstructorRenderer(){
	}

	private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_RECONSTRUCTOR_TEXTURE);

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-1F, -1F, 1F);
		GL11.glTranslatef(-.5F, -1.5F, .5F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture(modelTexture);
		model.render();		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
//		TileReconstructor rec = (TileReconstructor)entity;

//		if(rec.getworld() != null && rec.getStackInSlot(0) != null) {
//			GL11.glPushMatrix();
//			EntityItem ghost = new EntityItem(rec.getworld());
//			ghost.setEntityItemStack(rec.getStackInSlot(0));
//			float yOffset = 0.3F;
//			float scale = 0.7F;
//			if (ghost.getEntityItem().getItem() instanceof ItemBlock){
//				yOffset = 0.4F;
//				scale = 0.9F;
//			}
//			GL11.glTranslatef((float) x + 0.5F, (float) y + yOffset, (float) z + 0.5F);
//			GL11.glScalef(scale, scale, scale);
//			itemRenderer.doRender(ghost, 0, 0, 0, 0, 0);
//			GL11.glPopMatrix();
//		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

}