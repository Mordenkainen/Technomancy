package theflogat.technomancy.common.blocks.technom.existence;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.common.blocks.base.BlockContainerMultiTiles;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceBurner;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceDynamicBurner;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.RenderIds;

public class BlockExistenceBurner extends BlockContainerMultiTiles{

	public static PropertyEnum<Types> TYPE = PropertyEnum.create("type", Types.class);

	public BlockExistenceBurner() {
		setRegistryName(Names.existenceBurner[0]);
		this.setDefaultState(blockState.getBaseState().withProperty(TYPE, Types.burner));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0.25F, 0, 0.25F, 0.75F, 1, 0.75F);
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
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
	public TileEntity getTile(IBlockState state) {
		return state.getBlock().getMetaFromState(state)==0 ? new TileExistenceBurner() : new TileExistenceDynamicBurner();
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(this, 1, state.getValue(TYPE).ordinal());
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, Types.get(meta));
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockDestroyedByPlayer(worldIn, pos, state);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(TYPE, Types.get(stack.getMetadata())));
	}

	public enum Types implements IStringSerializable {
		burner,
		dynamicburner;

		public static Types get(int i) {
			return values()[i];
		}

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}
}