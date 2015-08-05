package theflogat.technomancy.client.tiles;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import theflogat.technomancy.client.models.ModelExistenceFountain;
import theflogat.technomancy.lib.Ref;

public class TileExistenceFountainRenderer extends TileEntitySpecialRenderer{

	public static ModelExistenceFountain model = new ModelExistenceFountain();

	public static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_TEST_TEXTURE);

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float t) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-1F, -1F, 1f);
		GL11.glTranslatef(.5F, -.5F, -.5F);
		bindTexture(modelTexture);
		model.render();
		GL11.glPopMatrix();
	}

}
