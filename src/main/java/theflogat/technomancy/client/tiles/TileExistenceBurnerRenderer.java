package theflogat.technomancy.client.tiles;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import theflogat.technomancy.client.models.ModelExistenceBurner;
import theflogat.technomancy.lib.Ref;

public class TileExistenceBurnerRenderer extends TileEntitySpecialRenderer{
	
	ModelExistenceBurner model = new ModelExistenceBurner();
	private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_ANVIL_TEXTURE);
	private static final ResourceLocation cube = new ResourceLocation(Ref.MODEL_WHITE_TEXTURE);
	private static final ResourceLocation bottom = new ResourceLocation(Ref.MODEL_BRF_TEXTURE);

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glTranslatef(.5F, 0, .5F);
		//bindTexture(modelTexture);
		//model.render();
		//if(te.getWorld().getBlockState(te.getPos()).getBlock().getMetaFromState(te.getWorld().getBlockState(te.getPos()))==1){
		//	bindTexture(bottom);
		//}
		//model.renderBottom();
		bindTexture(cube);
		model.renderCube(te.getWorld().getBlockState(te.getPos()).getBlock().getMetaFromState(te.getWorld().getBlockState(te.getPos()))==1);
		GL11.glPopMatrix();
	}
}
