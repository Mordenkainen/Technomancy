package theflogat.technomancy.common.blocks.bloodmagic.machines;

import java.util.ArrayList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.BlockProcessor;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBMProcessor;

public class BlockBMProcessor extends BlockProcessor {
	
	public BlockBMProcessor() {
		name = "bm";
	}
	
	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(super.onBlockActivated(w, pos, state, player, hand, facing, hitX, hitY, hitZ))
			return true;
		if(player != null) {
			TileEntity te = w.getTileEntity(pos);
			if(te instanceof TileBMProcessor) {
				if(((TileBMProcessor)te).owner.equals("")){
					((TileBMProcessor)te).owner = player.getUniqueID().toString();
				}
				player.openGui(Technomancy.instance, 1, w, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(w, pos, state, placer, stack);
		TileEntity tile = w.getTileEntity(pos);
		if(tile instanceof TileBMProcessor && placer instanceof EntityPlayer) {
			((TileBMProcessor)tile).owner = ((EntityPlayer)placer).getUniqueID().toString();
		}
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

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileBMProcessor();
	}
	
	@Override
	public void getNBTInfo(NBTTagCompound comp, ArrayList<String> l, int meta) {
		super.getNBTInfo(comp, l, meta);
		l.add("Owner:" + comp.getString("owner"));
	}
}