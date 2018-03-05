package theflogat.technomancy.common.blocks.botania.machines;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.botania.machines.TileManaExchanger;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import vazkii.botania.api.mana.IPoolOverlayProvider;

public class BlockManaExchanger extends BlockContainerAdvanced implements IPoolOverlayProvider {
	
	public BlockManaExchanger() {
		setHardness(2.0F);
		setResistance(10.0F);
		setSoundType(SoundType.STONE);
		setCreativeTab(Technomancy.tabsTM);
		setUnlocalizedName(Ref.MOD_PREFIX + Names.manaExchanger);
	}
	/*
	@Override
	public IIcon getIcon(World world, int x, int y, int z) {
		TileManaExchanger tile = (TileManaExchanger) world.getTileEntity(x, y, z);
		if(tile.active) {
			return icons[3];
		} else {
			return icons[4];
		}
	}
	*/

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

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileManaExchanger();
	}

	@Override
	public TextureAtlasSprite getIcon(World world, BlockPos blockPos) {
		return null;
	}
}