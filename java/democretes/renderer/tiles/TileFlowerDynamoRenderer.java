package democretes.renderer.tiles;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import democretes.blocks.dynamos.tiles.TileDynamoBase;
import democretes.lib.Ref;
import democretes.renderer.models.ModelFlowerDynamo;
import democretes.renderer.models.ModelManaFabricator;

public class TileFlowerDynamoRenderer extends TileEntitySpecialRenderer{
	
	ModelFlowerDynamo model = new ModelFlowerDynamo();
	
	private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_FLOWER_DYANMO_TEXTURE);

	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-1F, -1F, 1f);
		GL11.glTranslatef(-.5F, 0.5F, .5F);
		GL11.glEnable(GL11.GL_BLEND);
		renderFacing(entity);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture(modelTexture);
		model.render();
		
		GL11.glPopMatrix();
	}
	
	public void renderFacing(TileEntity entity) {
		switch (((TileDynamoBase)entity).getFacing()){
		case 0:
			GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);break;
		case 1:
			GL11.glTranslatef(0.0F, -2.0F, 0.0F);
			GL11.glRotatef(180, 0.0F, 1.0F, 0.0F); break;
		case 2:
			GL11.glTranslatef(0.0F, -1.0F, -1.0F);
			GL11.glRotatef(90.0F, 1.0F, 0F, 0.0F); break;
		case 3: 
			GL11.glTranslatef(0.0F, -1.0F, 1.0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F); 
			GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);break;
		case 4: 
			GL11.glTranslatef(1.0F, -1.0F, 0.0F);
			GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90.0F, 1.0F, 0F, 0.0F);break;
		case 5:
			GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90.0F, 1.0F, 0F, 0.0F); 
		}
	}
}
