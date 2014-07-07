package democretes.renderer.tiles;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import democretes.lib.Ref;
import democretes.renderer.models.ModelNodeDynamo;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileNodeDynamoRenderer extends TileEntitySpecialRenderer {
	
	ModelNodeDynamo model = new ModelNodeDynamo();
	
	private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_NODE_DYNAMO_TEXTURE);

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float t) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-1F, -1F, 1f);
		GL11.glTranslatef(-.5F, -1.5F, .5F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		bindTexture(modelTexture);
		model.render();
		
		GL11.glPopMatrix();
	}
	
	
	
	

}
