package theflogat.technomancy.client.renderers.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.common.tiles.thaumcraft.machine.TileReconstructor;
import theflogat.technomancy.lib.RenderIds;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockReconstructorRenderer implements ISimpleBlockRenderingHandler{
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		GL11.glPushMatrix();
		TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileReconstructor(), 0.0D, 0.0D, 0.0D, 0.0F);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIds.idReconstructor;
	}
}
