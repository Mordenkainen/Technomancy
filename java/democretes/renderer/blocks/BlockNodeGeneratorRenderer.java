package democretes.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import democretes.blocks.machines.tiles.TileNodeGenerator;
import democretes.lib.RenderIds;

public class BlockNodeGeneratorRenderer implements ISimpleBlockRenderingHandler{
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.2F, -1.0F, -0.4F);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glRotatef(-90, 0F, 1F, 0F);
		TileEntityRenderer.instance.renderTileEntityAt(new TileNodeGenerator(), 0.0D, 0.0D, 0.0D, 0.0F);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIds.idNodeGenerator;
	}
}
