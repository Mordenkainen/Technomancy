package theflogat.technomancy.client.tiles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import theflogat.technomancy.client.models.ModelAdvDeconTable;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileAdvDeconTable;
import theflogat.technomancy.lib.Ref;

public class TileAdvDeconTableRenderer extends TileEntitySpecialRenderer{
	
	ModelAdvDeconTable model = new ModelAdvDeconTable();
	private final RenderItem itemRenderer;

	public TileAdvDeconTableRenderer(){
		itemRenderer = new RenderItem(){
			@Override
			public boolean shouldBob()
			{
				return false;
			}
		};
		itemRenderer.setRenderManager(RenderManager.instance);
	}

	private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_RECONSTRUCTOR_TEXTURE);

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-1F, -1F, 1F);
		GL11.glTranslatef(-.5F, -1.5F, .5F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture(modelTexture);
		model.render();		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		TileAdvDeconTable rec = (TileAdvDeconTable)entity;
		if(rec.getWorldObj() != null && rec.getStackInSlot(0) != null) {
			float ticks = Minecraft.getMinecraft().renderViewEntity.ticksExisted + f;
			GL11.glPushMatrix();
			ItemStack is = rec.getStackInSlot(0).copy();
			
			EntityItem ghost = new EntityItem(rec.getWorldObj(), 0.0D, 0.0D, 0.0D, is);
			ghost.hoverStart = 0.0F;
			float yOffset = 0.3F;
			float scale = 0.7F;
			if (ghost.getEntityItem().getItem() instanceof ItemBlock){
				yOffset = 0.4F;
				scale = 0.9F;
			}
			GL11.glTranslatef((float) x + 0.5F, (float) y + yOffset, (float) z + 0.5F);
			GL11.glRotatef(ticks % 360.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(scale, scale, scale);
			itemRenderer.doRender(ghost, 0, 0, 0, 0, 0);
			GL11.glPopMatrix();
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}
