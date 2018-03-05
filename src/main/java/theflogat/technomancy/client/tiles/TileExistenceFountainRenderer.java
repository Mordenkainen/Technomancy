package theflogat.technomancy.client.tiles;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import theflogat.technomancy.client.models.ModelExistenceFountain;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceFountain;
import theflogat.technomancy.lib.Ref;

public class TileExistenceFountainRenderer extends TileEntitySpecialRenderer{

	public static ModelExistenceFountain model = new ModelExistenceFountain();

	private static final ResourceLocation textureLocation = new ResourceLocation(Ref.MOD_ID, "textures/models/fountain.png");
	public static final ResourceLocation exTexture = new ResourceLocation(Ref.MODEL_EXISTENCE_TEXTURE);

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-1F, -1F, 1f);
		GL11.glTranslatef(.5F, -.5F, -.5F);
		//bindTexture(textureLocation);
		//model.render();
		bindTexture(exTexture);
		if(te instanceof TileExistenceFountain){
			model.renderLiquid(((TileExistenceFountain)te).getPower(), ((TileExistenceFountain)te).getPowerCap());
		}else{
			model.renderLiquid(0, 1);
		}
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

}
