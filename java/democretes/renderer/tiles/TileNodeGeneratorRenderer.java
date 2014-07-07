package democretes.renderer.tiles;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import democretes.blocks.machines.tiles.TileNodeGenerator;
import democretes.lib.Ref;
import democretes.renderer.models.ModelNodeGenerator;

public class TileNodeGeneratorRenderer extends TileEntitySpecialRenderer {
	
	ModelNodeGenerator model = new ModelNodeGenerator();
	
	private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_NODE_GENERATOR_TEXTURE);

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float t) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-1F, -1F, 1f);
		GL11.glTranslatef(-.5F, -1.5F, .5F);
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		switch (((TileNodeGenerator)entity).facing) {
		case 3: 
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F); break;
		case 5: 
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F); break;
		case 4: 
				GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
		}
		bindTexture(modelTexture);

		GL11.glPushMatrix();
		GL11.glRotatef(((TileNodeGenerator)entity).rotation, 1.0F, 0.0F, 0.0F);
		model.renderCore();
		GL11.glPopMatrix();				
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		bindTexture(modelTexture);
		model.render();
		GL11.glPopMatrix();
	}
	
}
