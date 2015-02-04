package theflogat.technomancy.client.renderers.tiles;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.client.renderers.models.ModelNodeDynamo;
import theflogat.technomancy.common.tiles.dynamos.TileNodeDynamo;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.compat.Thaumcraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileNodeDynamoRenderer extends TileEntitySpecialRenderer {
	
	ModelNodeDynamo model = new ModelNodeDynamo();
	
	private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_NODE_DYNAMO_TEXTURE);

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-1F, -1F, 1f);
		GL11.glTranslatef(-.5F, -1.5F, .5F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		bindTexture(modelTexture);
		model.render();
		
		GL11.glPopMatrix();
		if (((TileNodeDynamo)tileentity).draining)
		{
			GL11.glPushMatrix();
			float ticks = Minecraft.getMinecraft().renderViewEntity.ticksExisted + partialTicks;
			try {
				Thaumcraft.drawFloatyLine.invoke(null, tileentity.xCoord + 0.5D, tileentity.yCoord + .9D, tileentity.zCoord + 0.5D, ((TileNodeDynamo)tileentity).sourceX + 0.5D, ((TileNodeDynamo)tileentity).sourceY + 0.5D, ((TileNodeDynamo)tileentity).sourceZ + 0.5D, partialTicks, ((TileNodeDynamo)tileentity).color, "textures/misc/wispy.png", -0.02F, Math.min(ticks, 10.0F) / 10.0F);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			GL11.glPopMatrix();
		}
	}
	
	
	
	

}
