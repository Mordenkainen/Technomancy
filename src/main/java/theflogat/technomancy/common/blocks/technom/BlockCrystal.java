package theflogat.technomancy.common.blocks.technom;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.BlockContainerBase;
import theflogat.technomancy.common.tiles.technom.TileCrystal;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

import javax.annotation.Nullable;

public class BlockCrystal extends BlockContainer {

	public static PropertyEnum<Types> TYPE = PropertyEnum.create("type", Types.class);

	public BlockCrystal() {
		super(Material.IRON);
		setCreativeTab(Technomancy.tabsTM);
		setHardness(2F);
		setUnlocalizedName(Ref.MOD_PREFIX + Names.crystalBlock);
		setLightLevel(1);
		this.setRegistryName(Names.crystalBlock);
		this.setDefaultState(blockState.getBaseState().withProperty(TYPE, Types.one));
		Technomancy.proxy.registerWithMapper(this);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		AxisAlignedBB bb = null;
		switch(getStage(source, pos.getX(), pos.getY(), pos.getZ())) {
			case 0:
				bb = new AxisAlignedBB(0F, 0F, 0F, 1F, 1F, 1F);
				break;
			case 1:
				bb = new AxisAlignedBB(0.25F, 0F, 0.25F, 0.75F, 1F, 0.75F);
				break;
			case 2:
				bb = new AxisAlignedBB(0.375F, 0F, 0.375F, 0.625F, 1F, 0.625F);
				break;
		}

		return bb;
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

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		getBoundingBox(blockState, worldIn, pos);
		return super.getCollisionBoundingBox(blockState, worldIn, pos);
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> list) {
		for(Types type : Types.values()) {
			list.add(new ItemStack(this, 1, type.ordinal()));
		}
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileCrystal();
	}

	public static int getStage(IBlockAccess w, int x, int y, int z) {
		int i = 0;
		if(w.getBlockState(new BlockPos(x, y-1, z)).getBlock()instanceof BlockCrystal){
			i++;
			if(w.getBlockState(new BlockPos(x, y-2, z)).getBlock() instanceof BlockCrystal){
				i++;
			}
		}
		return i;
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
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(TYPE, Types.get(stack.getMetadata())));
		worldIn.notifyBlockUpdate(pos, state, worldIn.getBlockState(pos), 3);
	}

	public enum Types implements IStringSerializable {
		one,
		two,
		three,
		four,
		five;

		public static Types get(int i) {
			return values()[i];
		}

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}
}