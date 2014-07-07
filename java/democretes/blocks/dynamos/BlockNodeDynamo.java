package democretes.blocks.dynamos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.blocks.BlockBase;
import democretes.blocks.dynamos.tiles.TileNodeDynamo;
import democretes.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
public class BlockNodeDynamo extends BlockBase {


	@SideOnly(Side.CLIENT)
	public Icon iconDynamo;

	public BlockNodeDynamo(int id) {
		super(id);
		setUnlocalizedName("techno:nodeDynamo");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileNodeDynamo();
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderIds.idNodeDynamo;
	}

}
