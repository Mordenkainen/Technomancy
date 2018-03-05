package theflogat.technomancy.common.blocks.technom.existence;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.common.blocks.base.BlockContainerBase;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceFountain;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;

public class BlockExistenceFountain extends BlockContainerBase {

	public BlockExistenceFountain() {
		setUnlocalizedName(Ref.getId(Names.existenceFountain));
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World w, BlockPos pos, Random rand) {
		super.randomDisplayTick(state, w, pos, rand);
		if(((TileExistenceFountain)w.getTileEntity(pos)).isRunning()){
			for (int l = pos.getX() - 2; l <= pos.getX() + 2; ++l){
				for (int i1 = pos.getZ() - 2; i1 <= pos.getZ() + 2; ++i1){
					if (l > pos.getX() - 2 && l < pos.getX() + 2 && i1 == pos.getZ() - 1){
						i1 = pos.getZ() + 2;
					}
					if (rand.nextInt(16) == 0){
						for (int j1 = pos.getY(); j1 <= pos.getY() + 1; ++j1){
							w.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pos.getX() + 0.5D, pos.getY() + 2.0D, pos.getZ() + 0.5D, l - pos.getX() + rand.nextFloat() - 0.5D, j1 - pos.getY() - rand.nextFloat()
									- 1.0F, i1 - pos.getZ() + rand.nextFloat() - 0.5D);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
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

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileExistenceFountain();
	}
}
