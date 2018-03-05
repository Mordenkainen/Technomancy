package theflogat.technomancy.client.tiles;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;

import theflogat.technomancy.client.models.ModelBloodFabricator;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBloodFabricator;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.compat.BloodMagic;
import theflogat.technomancy.util.FluidRenderUtils;

public class TileBloodFabricatorRenderer extends TileEntitySpecialRenderer {
	
	ModelBloodFabricator model = new ModelBloodFabricator();
	
	private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_BLOOD_FABRICATOR_TEXTURE);

	@Override
	public void render(TileEntity entity, double x, double y, double z, float t, int destroyStage, float alpha) {
		if (entity instanceof TileBloodFabricator) {
			FluidTank fluid = ((TileBloodFabricator) entity).tank;
			if (fluid != null && fluid.getFluid() != null && fluid.getFluidAmount() > 0) {
				GlStateManager.pushMatrix();
				GlStateManager.enableBlend();

				FluidRenderUtils.translateAgainstPlayer(entity.getPos(), false);
				FluidRenderUtils.renderFluid(fluid.getFluid(), entity.getPos(), 0.30d, 0.2d, 0.3d, 0.01d, 0.0d, 0.01d, 0.4d, (double) fluid.getFluidAmount() / (double) fluid.getCapacity() * 0.79d, 0.4d);

				GlStateManager.disableBlend();
				GlStateManager.popMatrix();
			}

			GL11.glPushMatrix();
			GL11.glTranslatef((float)x, (float)y, (float)z);
			GL11.glScalef(-1F, -1F, 1f);
			GL11.glTranslatef(-.5F, -1.5F, .5F);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			bindTexture(modelTexture);
			model.render();
		
			GL11.glPopMatrix();
		}
	}
}
