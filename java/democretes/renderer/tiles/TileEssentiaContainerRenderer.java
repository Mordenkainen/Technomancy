package democretes.renderer.tiles;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import democretes.blocks.TMBlocks;
import democretes.blocks.storage.TileEssentiaContainer;
import democretes.compat.Thaumcraft;
import democretes.lib.Ref;
import democretes.renderer.models.ModelEssentiaContainer;

public class TileEssentiaContainerRenderer extends TileEntitySpecialRenderer{

	ModelEssentiaContainer model = new ModelEssentiaContainer();

	private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_ESSENTIA_CONTAINER_TEXTURE);

	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float t) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-1F, -1F, 1f);
		GL11.glTranslatef(-.5F, -1.5F, .5F);
		renderLiquid(entity, x, y, z, t);	
		renderLabel(entity, x, y, z, t);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		bindTexture(modelTexture);
		model.render();

		GL11.glPopMatrix();
	}

	public void renderLabel(TileEntity tile, double x, double y, double z, float f) {
		try{
			if (this.tileEntityRenderer.renderEngine == null) {
				return;
			}
			if (((TileEssentiaContainer)tile).aspectFilter != null) {
				GL11.glPushMatrix();
				switch (((TileEssentiaContainer)tile).facing) {
				case 2:
					GL11.glTranslatef(0.0F, 0.0F, -0.63F);break;	
				case 3: 
					GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F); 
					GL11.glTranslatef(0.0F, 0.0F, -0.63F);break;					
				case 5: 
					GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F); 
					GL11.glTranslatef(0.0F, 0.0F, -0.63F);break;
				case 4: 
					GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
					GL11.glTranslatef(0.0F, 0.0F, -0.63F);
				}
				GL11.glTranslatef(0.0F, 1.5F, 0.0F);
				float rot = (((TileEssentiaContainer)tile).aspectFilter.getTag().hashCode() + tile.xCoord + ((TileEssentiaContainer)tile).facing) % 4 - 2;

				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, -0.4F, 0.315F);
				if (Thaumcraft.crooked) {
					GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
				}
				Thaumcraft.renderQuadCenteredFromTexture.invoke(null, "textures/models/label.png", 0.5F, 1.0F, 1.0F, 1.0F, -99, 771, 1.0F);
				GL11.glPopMatrix();

				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, -0.4F, 0.31F);
				if (Thaumcraft.crooked) {
					GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
				}
				GL11.glScaled(0.021D, 0.021D, 0.021D);

				Thaumcraft.drawTag.invoke(null, -8, -8, ((TileEssentiaContainer)tile).aspectFilter);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			}		
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void renderLiquid(TileEntity tile, double x, double y, double z, float f) {
		if (this.tileEntityRenderer.renderEngine == null) {
			return;
		}	
		TileEssentiaContainer entity = (TileEssentiaContainer)tile;

		if(entity.amount <= 0) {
			return;
		}

		GL11.glPushMatrix();
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0F, -1.5F, 0F);

		RenderBlocks renderBlocks = new RenderBlocks();

		GL11.glDisable(2896);

		float level = (MathHelper.abs((entity.amount)/5)/MathHelper.abs((entity.maxAmount)/5)) * 0.65F;

		Tessellator t = Tessellator.instance;

		renderBlocks.setRenderBounds(0.25D, 0.0625D, 0.25D, 0.75D, 0.0625D + level, 0.75D);

		t.startDrawingQuads();
		if (entity.aspect != null) {
			t.setColorOpaque_I((entity).aspect.getColor());
		}
		int bright = 200;
		if (tile.getWorldObj() != null) {
			bright = Math.max(200, Block.blocksList[Thaumcraft.blockJar.blockID].getMixedBrightnessForBlock(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord));
		}
		t.setBrightness(bright);

		Icon icon = Thaumcraft.iconLiquid;

		bindTexture(TextureMap.locationBlocksTexture);

		Block jar = TMBlocks.creativeJar;

		renderBlocks.renderFaceYNeg(jar, -0.5D, 0.0D, -0.5D, icon);
		renderBlocks.renderFaceYPos(jar, -0.5D, 0.0D, -0.5D, icon);
		renderBlocks.renderFaceZNeg(jar, -0.5D, 0.0D, -0.5D, icon);
		renderBlocks.renderFaceZPos(jar, -0.5D, 0.0D, -0.5D, icon);
		renderBlocks.renderFaceXNeg(jar, -0.5D, 0.0D, -0.5D, icon);
		renderBlocks.renderFaceXPos(jar, -0.5D, 0.0D, -0.5D, icon);

		t.draw();	


		GL11.glEnable(2896);	
		GL11.glPopMatrix();

		GL11.glColor3f(1.0F, 1.0F, 1.0F);
	}

}
