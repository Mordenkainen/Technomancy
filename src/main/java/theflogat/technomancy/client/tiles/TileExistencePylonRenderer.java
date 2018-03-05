package theflogat.technomancy.client.tiles;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import theflogat.technomancy.client.models.ModelExistencePylon;
import theflogat.technomancy.common.tiles.technom.existence.TileExistencePylon;
import theflogat.technomancy.lib.Ref;

public class TileExistencePylonRenderer extends TileEntitySpecialRenderer{
	
	ModelExistencePylon model = new ModelExistencePylon();
	private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_COBBLEM_TEXTURE);
	private static final ResourceLocation cube = new ResourceLocation(Ref.MODEL_WHITE_TEXTURE);

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if(te instanceof TileExistencePylon){
			GL11.glPushMatrix();
			GL11.glTranslatef((float)x, (float)y, (float)z);
			GL11.glTranslatef(.5F, 0, .5F);
			//bindTexture(modelTexture);
			//model.render();
			bindTexture(cube);
			model.renderCube(te.getWorld().getBlockState(te.getPos()).getBlock().getMetaFromState(te.getWorld().getBlockState(te.getPos())));
			GL11.glPopMatrix();
		}
	}
}