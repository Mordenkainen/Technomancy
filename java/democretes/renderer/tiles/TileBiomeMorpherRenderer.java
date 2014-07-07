package democretes.renderer.tiles;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import democretes.blocks.machines.tiles.TileBiomeMorpher;
import democretes.compat.Thaumcraft;
import democretes.lib.Ref;
import democretes.renderer.models.ModelBiomeMorpher;

public class TileBiomeMorpherRenderer extends TileEntitySpecialRenderer{
	
	ModelBiomeMorpher model = new ModelBiomeMorpher();
	
	private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_BIOME_MODIFIER_TEXTURE);

	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-1F, -1F, 1f);
		GL11.glTranslatef(-.5F, -1.5F, .5F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture(modelTexture);
		model.render();
		
		GL11.glPopMatrix();
	}

}
