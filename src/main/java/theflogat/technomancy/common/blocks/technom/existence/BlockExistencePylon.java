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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.BlockContainerBase;
import theflogat.technomancy.common.tiles.technom.existence.TileExistencePylon;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;

public class BlockExistencePylon extends BlockContainerBase {

	public static PropertyEnum<TileExistencePylon.Type> TYPE = PropertyEnum.create("type", TileExistencePylon.Type.class);

	public BlockExistencePylon() {
		setUnlocalizedName(Ref.getId(Names.existencePylon));
		setRegistryName(Names.existencePylon);
		this.setDefaultState(blockState.getBaseState().withProperty(TYPE, TileExistencePylon.Type.BASIC));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(1F/4F, 0, 1F/4F, 3F/4F, 1, 3F/4F);
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
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(this, 1, state.getValue(TYPE).ordinal());
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, TileExistencePylon.Type.getTypeFromId(meta));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(TYPE, TileExistencePylon.Type.getTypeFromId(stack.getMetadata())));
		worldIn.notifyBlockUpdate(pos, state, worldIn.getBlockState(pos), 3);
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileExistencePylon();
	}
}