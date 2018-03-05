package theflogat.technomancy.common.blocks.botania.machines;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
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
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import theflogat.technomancy.common.tiles.botania.machines.TileBOProcessor;
import theflogat.technomancy.lib.compat.Botania;
import vazkii.botania.api.wand.IWandHUD;

public class BlockBOProcessor extends BlockProcessor implements IWandHUD {
	
	public BlockBOProcessor() {
		name = "bo";
	}
	
	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(super.onBlockActivated(w, pos, state, player, hand, facing, hitX, hitY, hitZ))
			return true;
		if(player!=null) {
			TileEntity tile = w.getTileEntity(pos);
			if(tile instanceof TileBOProcessor) {
				player.openGui(Technomancy.instance, 2, w, pos.getX(), pos.getY(), pos.getZ());
			}
		}		
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileBOProcessor();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random r) {
		TileEntity te = world.getTileEntity(pos);
	    if (te instanceof TileProcessorBase && ((TileProcessorBase)te).isActive)	    {
	    	float f = pos.getX() + 0.6F;
    		float f1 = (pos.getY() + 0.2F + r.nextFloat() * 5.0F / 16.0F) + 0.1F;
    		float f2 = pos.getZ() + 0.6F;
    		float f3 = 0.52F;
    		float f4 = r.nextFloat() * 0.5F - 0.25F;	
	    	Botania.sparkle(world, (double)f - f3, f1, f2 + f4, r);
	    	Botania.sparkle(world, (double)f + f3, f1, f2 + f4, r);
	    	Botania.sparkle(world, (double)f + f4, f1, f2 - f3, r);
	    	Botania.sparkle(world, (double)f + f4, f1, f2 + f3, r);
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
	public void renderHUD(Minecraft minecraft, ScaledResolution res, World world, BlockPos blockPos) {
		TileEntity tile = world.getTileEntity(blockPos);
		if(tile instanceof TileBOProcessor) {
			((TileBOProcessor)tile).renderHUD(minecraft, res);
		}
	}
}