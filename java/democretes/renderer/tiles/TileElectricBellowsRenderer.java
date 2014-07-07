package democretes.renderer.tiles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import democretes.blocks.machines.tiles.TileElectricBellows;
import democretes.lib.Ref;
import democretes.renderer.models.ModelElectricBellows;

public class TileElectricBellowsRenderer extends TileEntitySpecialRenderer{
	
	ModelElectricBellows model = new ModelElectricBellows();
	private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_ELECTRIC_BELLOWS_TEXTURE);

	private void translateFromOrientation(double x, double y, double z, int facing) {
		GL11.glTranslatef((float)x + 0.5F, (float)y - 0.5F, (float)z + 0.5F);
		if (facing == 2) {
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		} else if (facing == 4) {
			GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
		} else if (facing == 5) {
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float fq) {
		TileElectricBellows bellows = (TileElectricBellows)entity;
		
		float scale = 0.0F;
		if (bellows.worldObj == null) {
			EntityPlayer p = Minecraft.getMinecraft().thePlayer;
			scale = MathHelper.sin(p.ticksExisted / 8.0F) * 0.3F + 0.7F;
			bellows.facing = 2;
		}else{
			scale = bellows.inflation;
		}
		float tscale = 0.125F + scale * 0.875F;

		Minecraft mc = FMLClientHandler.instance().getClient();

		bindTexture(modelTexture);
		GL11.glPushMatrix();
		GL11.glEnable(2977);
		GL11.glEnable(3042);
		GL11.glEnable(32826);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		translateFromOrientation((float)x, (float)y, (float)z, bellows.facing);
		GL11.glTranslatef(0.0F, 1.0F, 0.0F);
		
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, (scale + 0.1F) / 2.0F, 0.5F);
		model.Bag.setRotationPoint(0.0F, 0.5F, 0.0F);
		model.Bag.render(0.0625F);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		GL11.glTranslatef(0.0F, -1.0F, 0.0F);
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, -tscale / 2.0F + 0.5F, 0.0F);
		model.TopPlank.render(0.0625F);
		GL11.glTranslatef(0.0F, tscale / 2.0F - 0.5F, 0.0F);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, tscale / 2.0F - 0.5F, 0.0F);
		model.BottomPlank.render(0.0625F);
		GL11.glTranslatef(0.0F, -tscale / 2.0F + 0.5F, 0.0F);
		GL11.glPopMatrix();
		model.render();
		GL11.glDisable(32826);
		GL11.glDisable(3042);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
	
}

		
	
