package theflogat.technomancy.client.tiles;

import java.awt.Color;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.client.models.ModelTeslaCoil;
import theflogat.technomancy.common.tiles.technom.TileItemTransmitter;
import theflogat.technomancy.lib.Ref;

public class TileItemTransmitterRenderer  extends TileEntitySpecialRenderer {
	
	ModelTeslaCoil model = new ModelTeslaCoil();

	private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_ITEM_TRANSMITTER_TEXTURE);

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-1.0F, -1.0F, 1.0f);
		GL11.glTranslatef(-.5F, -1.5F, .5F);		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glTranslatef(0.0F, 2.0F, 0.0F);
		renderFacing(te);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture(modelTexture);
		model.render();

		GL11.glPushMatrix();
		if(((TileItemTransmitter)te).boost) {
			GL11.glColor3d(Color.RED.getRed(), 0, 0);
		}
		model.renderTopRing();
		GL11.glColor3d(1, 1, 1);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		if(((TileItemTransmitter)te).filter != null) {
			GL11.glColor3d(0, Color.GREEN.getGreen(), 0);
		}
		model.renderBottomRing();
		GL11.glColor3d(1, 1, 1);
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();
	}

	public void renderFacing(TileEntity entity) {
		switch (((TileItemTransmitter)entity).facing){
		case 0:
			GL11.glTranslatef(0.0F, -2.0F, 0.0F);
			GL11.glRotatef(180, 0.0F, 1.0F, 0.0F); break;
		case 1:
			GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);break;
		case 3: 
			GL11.glTranslatef(0.0F, -1.0F, -1.0F);
			GL11.glRotatef(90.0F, 1.0F, 0F, 0.0F); break;
		case 2:
			GL11.glTranslatef(0.0F, -1.0F, 1.0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F); 
			GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);break;
		case 5: 
			GL11.glTranslatef(1.0F, -1.0F, 0.0F);
			GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90.0F, 1.0F, 0F, 0.0F);break;
		case 4:
			GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90.0F, 1.0F, 0F, 0.0F);
		}
	}

}
