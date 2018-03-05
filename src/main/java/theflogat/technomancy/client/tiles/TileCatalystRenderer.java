package theflogat.technomancy.client.tiles;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import theflogat.technomancy.client.models.ModelCatalyst;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.lib.Ref;

public class TileCatalystRenderer extends TileEntitySpecialRenderer{

	public static ModelCatalyst model = new ModelCatalyst();

	public static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_CATALYST_TEXTURE);

	@Override
	public void render(TileEntity entity, double x, double y, double z, float t, int destroyStage, float alpha) {
		if (entity instanceof TileCatalyst) {
			TileCatalyst te =(TileCatalyst)entity;
			if(te.specialRender!=null && te.textLoc!=null){
				GL11.glPushMatrix();
				GL11.glTranslatef((float)x, (float)y, (float)z);
				GL11.glScalef(-1F, -1F, 1f);
				GL11.glTranslatef(.5F, -.5F, -.5F);
				bindTexture(te.textLoc);
				te.specialRender.render();
				GL11.glPopMatrix();
			}else{
				GL11.glPushMatrix();
				GL11.glTranslatef((float)x, (float)y, (float)z);
				GL11.glScalef(-1F, -1F, 1f);
				GL11.glTranslatef(-.5F, -1.5F, .5F);
				bindTexture(modelTexture);
				model.render(te.getWorld().getBlockState(te.getPos()).getBlock().getMetaFromState(te.getWorld().getBlockState(te.getPos())));
				GL11.glPopMatrix();
			}
		}
	}

}