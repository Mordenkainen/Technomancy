package theflogat.technomancy.common.blocks.base;

import java.util.ArrayList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theflogat.technomancy.common.tiles.base.TileCoilTransmitter;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

import javax.annotation.Nullable;

public abstract class BlockCoilTransmitter extends BlockContainerAdvanced {

	public AxisAlignedBB bb = new AxisAlignedBB(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);

	public BlockCoilTransmitter() {
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileCoilTransmitter tile = getTE(world, pos);
		AxisAlignedBB bounding = bb;
		if(tile != null) {
			switch(tile.facing) {
				case 0:
				case 1:
					bounding = new AxisAlignedBB(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
					break;
				case 2:
				case 3:
					bounding = new AxisAlignedBB(0.1875F, 0.1875F, 0.0F, 0.8125F, 0.8125F, 1.0F);
					break;
				case 4:
				case 5:
					bounding = new AxisAlignedBB(0.0F, 0.1875F, 0.1875F, 1.0F, 0.8125F, 0.8125F);
					break;
				default:
					bounding = bb;
			}
		}

		return bounding;
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return bb;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		TileCoilTransmitter tile = getTE(blockAccess, pos);
		if(tile!=null) {
			return tile.redstoneState ? 15 : 0;
		}
		return 0;
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		TileCoilTransmitter tile = getTE(blockAccess, pos);
		if(tile!=null) {
			return tile.redstoneState ? 15 : 0;
		}
		return 0;
	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
		TileCoilTransmitter tile = getTE(world, pos);
		if(tile!=null) {
			return tile.getBoost();
		}
		return false;
	}

	@Override
	public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(w, pos, state, placer, stack);
		TileCoilTransmitter tile = getTE(w, pos);
		if(tile!=null) {
			tile.clear();
			w.setBlockState(pos, state);
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	private static TileCoilTransmitter getTE(IBlockAccess world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		return tile instanceof TileCoilTransmitter ? (TileCoilTransmitter)tile : null;
	}
	
	@Override
	public void getNBTInfo(NBTTagCompound comp, ArrayList<String> l, int meta) {
		super.getNBTInfo(comp, l, meta);
	}
}
