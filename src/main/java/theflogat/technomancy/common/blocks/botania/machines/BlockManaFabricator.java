package theflogat.technomancy.common.blocks.botania.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.botania.machines.TileManaFabricator;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import vazkii.botania.api.wand.IWandHUD;

public class BlockManaFabricator extends BlockContainerAdvanced implements IWandHUD {

	public BlockManaFabricator() {
		setUnlocalizedName(Ref.getId(Names.manaFabricator));
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileManaFabricator();
	}

	@Override
	public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(w, pos, state, placer, stack);
		TileEntity tile = w.getTileEntity(pos);
		if(tile instanceof TileManaFabricator) {
			((TileManaFabricator)tile).facing = w.getBlockState(pos).getBlock().getMetaFromState(w.getBlockState(pos));
			w.notifyNeighborsOfStateChange(pos, state.getBlock(), true);
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		AxisAlignedBB bb = null;
		if(tile instanceof TileManaFabricator) {
			switch(((TileManaFabricator)tile).facing) {
				case 0:
				case 1:
					bb = new AxisAlignedBB(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
					break;
				case 2:
				case 3:
					bb = new AxisAlignedBB(0.1875F, 0.1875F, 0.0F, 0.8125F, 0.8125F, 1.0F);
					break;
				case 4:
				case 5:
					bb = new AxisAlignedBB(0.0F, 0.1875F, 0.1875F, 1.0F, 0.8125F, 0.8125F);
			}
		}

		return bb;
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
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public void renderHUD(Minecraft mc, ScaledResolution res, World world, BlockPos pos) {
		((TileManaFabricator)world.getTileEntity(pos)).renderHUD(mc, res);
	}
}
