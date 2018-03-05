package theflogat.technomancy.common.blocks.botania.dynamos;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.common.blocks.base.BlockDynamoBase;
import theflogat.technomancy.common.tiles.botania.dynamos.TileFlowerDynamo;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import vazkii.botania.api.wand.IWandHUD;

public class BlockFlowerDynamo extends BlockDynamoBase implements IWandHUD {

	public BlockFlowerDynamo() {
		setUnlocalizedName(Ref.getId(Names.flowerDynamo));
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileFlowerDynamo();
	}

	@Override
	public void renderHUD(Minecraft minecraft, ScaledResolution scaledResolution, World world, BlockPos blockPos) {
		((TileFlowerDynamo)world.getTileEntity(blockPos)).renderHUD(minecraft, scaledResolution);

	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isTranslucent(IBlockState state) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}


	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
