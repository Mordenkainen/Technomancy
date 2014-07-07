package democretes.renderer.tiles;

import org.lwjgl.opengl.GL11;

import democretes.blocks.machines.tiles.TileEldritchConsumer;
import democretes.lib.Ref;
import democretes.renderer.models.ModelEldritchConsumer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEldritchConsumerRenderer extends TileEntitySpecialRenderer{
	
	public static final ModelEldritchConsumer model = new ModelEldritchConsumer();
	public static final ResourceLocation texture = new ResourceLocation(Ref.MOD_ID.toLowerCase(), "textures/models/eldCons.png");

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-1F,-1F,1F);
		
		bindTexture(texture);
		model.render(0.0625F, ((TileEldritchConsumer)te).panelRotation, ((TileEldritchConsumer)te).cooldown>0);

		GL11.glPopMatrix();

	}

}
