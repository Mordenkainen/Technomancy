package theflogat.technomancy.client.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.common.tiles.botania.dynamos.TileFlowerDynamo;
import theflogat.technomancy.lib.RenderIds;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockFlowerDynamoRenderer implements ISimpleBlockRenderingHandler{
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		GL11.glPushMatrix();
		TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileFlowerDynamo(), 0.0D, 0.0D, 0.0D, 0.0F);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIds.idFlowerDynamo;
	}
}
