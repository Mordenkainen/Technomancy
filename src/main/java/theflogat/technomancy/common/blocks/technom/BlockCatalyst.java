package theflogat.technomancy.common.blocks.technom;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class BlockCatalyst extends BlockContainerAdvanced {
	static final int maxSubBlocks = 5;

	public static PropertyEnum<Types> TYPE = PropertyEnum.create("type", Types.class);

	public BlockCatalyst() {
		setUnlocalizedName(Ref.getId(Names.catalyst));
		setRegistryName(Names.catalyst);
		setCreativeTab(Technomancy.tabsTM);
		this.setDefaultState(blockState.getBaseState().withProperty(TYPE, Types.one));
		Technomancy.proxy.registerWithMapper(this);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
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
	public float getBlockHardness(IBlockState blockState, World w, BlockPos pos) {
		return ((TileCatalyst)w.getTileEntity(pos)).remCount==-1 ? blockHardness : -1;
	}

	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		super.onBlockActivated(w, pos, state, player, hand, facing, hitX, hitY, hitZ);
		if(player.isSneaking() && w.getTileEntity(pos) instanceof TileCatalyst){
			TileCatalyst te = (TileCatalyst)w.getTileEntity(pos);
			if(te.remCount==-1){
				((TileCatalyst)w.getTileEntity(pos)).activateRitual(player);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int damageDropped(IBlockState meta) {
		return meta.getBlock().getMetaFromState(meta);
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
		return new TileCatalyst();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
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
	public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		w.setBlockState(pos, state.withProperty(TYPE, Types.get(stack.getMetadata())));
		w.notifyBlockUpdate(pos, state, w.getBlockState(pos), 3);

		if(placer instanceof EntityPlayer && w.getTileEntity(pos) instanceof TileCatalyst){
			TileCatalyst te = (TileCatalyst) w.getTileEntity(pos);
			te.userName = ((EntityPlayer)placer).getDisplayName().getFormattedText();
		}
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
