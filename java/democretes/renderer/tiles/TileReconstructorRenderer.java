package democretes.renderer.tiles;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import democretes.blocks.machines.tiles.TileReconstructor;
import democretes.lib.Ref;
import democretes.renderer.models.ModelReconstructor;

public class TileReconstructorRenderer extends TileEntitySpecialRenderer{
	
	ModelReconstructor model = new ModelReconstructor();
	
	private static final ResourceLocation modelTexture = new ResourceLocation(Ref.MODEL_RECONSTRUCTOR_TEXTURE);

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
		
		TileReconstructor rec = (TileReconstructor)entity;
		if(rec.worldObj != null && rec.getStackInSlot(0) != null) {
			EntityItem entityitem = null;
		    GL11.glPushMatrix();
		    GL11.glTranslatef((float)x + 0.5F, (float)y + 1.15F, (float)z + 0.5F);
		    float ticks = Minecraft.getMinecraft().renderViewEntity.ticksExisted + f;
		    GL11.glRotatef(ticks % 360.0F, 0.0F, 1.0F, 0.0F);
		    if (((rec.getStackInSlot(0).getItem() instanceof ItemBlock)) && (rec.getStackInSlot(0).itemID < Block.blocksList.length)) {
		    	GL11.glScalef(2.0F, 2.0F, 2.0F);
		    } else {
		    	GL11.glScalef(1.0F, 1.0F, 1.0F);
		    }
			ItemStack is = rec.getStackInSlot(0).copy();
		    is.stackSize = 1;
		    entityitem = new EntityItem(rec.worldObj, 0.0D, 0.0D, 0.0D, is);
		    entityitem.hoverStart = 0.0F;
		    
		    RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		    if (!Minecraft.isFancyGraphicsEnabled()) {
		        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		        RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		    }
		    GL11.glPopMatrix();
		    //System.out.println("Is this even working?");
		}else{
		    //System.out.println("This isn't even working.");
		}
		
		GL11.glPopMatrix();
		
	
	}
	
}
