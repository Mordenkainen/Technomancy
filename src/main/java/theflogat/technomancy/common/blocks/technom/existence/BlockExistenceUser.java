package theflogat.technomancy.common.blocks.technom.existence;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.BlockContainerMultiTiles;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceSealingDevice;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceCropAccelerator;
import theflogat.technomancy.common.tiles.technom.existence.TileExistenceHarvester;
import theflogat.technomancy.lib.Ref;

import javax.annotation.Nullable;

public class BlockExistenceUser extends BlockContainer {

	public static PropertyEnum<Types> TYPE = PropertyEnum.create("type", Types.class);

	public BlockExistenceUser() {
		super(Material.IRON);
		setCreativeTab(Technomancy.tabsTM);
		setHardness(2F);
		setUnlocalizedName(Ref.getId("existenceUser"));
		setRegistryName("existenceuser");
		this.setDefaultState(blockState.getBaseState().withProperty(TYPE, Types.harvester));
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
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
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

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		switch(meta){
			case 0:
				return new TileExistenceCropAccelerator();
			case 1:
				return new TileExistenceHarvester();
			case 2:
				return new TileExistenceSealingDevice();
		}
		return null;
	}

	public enum Types implements IStringSerializable {
		cropacc,
		harvester,
		sealer;

		public static Types get(int i) {
			return values()[i];
		}

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}
}
