package theflogat.technomancy.common.blocks.base;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.util.helpers.InvHelper;

public abstract class BlockProcessor extends BlockContainerAdvanced {
	
	protected String name;
	
	@Override
	public String getUnlocalizedName() {
		return "tile." + Ref.MOD_PREFIX + Names.processor + name;
	}

	/**
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		icons = new IIcon[4];
		icons[0] = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.processor + name + "Side");
		icons[1] = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.processor + name + "Active");
		icons[2] = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.processor + name + "Inactive");
		icons[3] = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.processor + name + "Top");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		if(side == 1) {
			return icons[3];
		}
		if(side > 1) {
			return icons[2];
		}
		return icons[0];
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
		if(side == 1) {
			return icons[3];
		}
		if(side > 1) {
			TileEntity tile = access.getTileEntity(x, y, z);
			if(tile instanceof TileProcessorBase && ((TileProcessorBase)tile).isActive) {
				return icons[1];
			}
			return icons[2];
		}		
		return icons[0];		
	}
	 */


	
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random r) {
		TileEntity te = world.getTileEntity(pos);
	    if (te instanceof TileProcessorBase && ((TileProcessorBase)te).isActive)	    {
	    	float f = pos.getX() + 0.5F;
    		float f1 = pos.getY() + 0.2F + r.nextFloat() * 5.0F / 16.0F;
    		float f2 = pos.getZ() + 0.5F;
    		float f3 = 0.52F;
    		float f4 = r.nextFloat() * 0.5F - 0.25F;	      
    		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
    		world.spawnParticle(EnumParticleTypes.FLAME, f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
        
    		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
    		world.spawnParticle(EnumParticleTypes.FLAME, f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
        
    		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
    		world.spawnParticle(EnumParticleTypes.FLAME, f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
        
    		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
    		world.spawnParticle(EnumParticleTypes.FLAME, f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
	    }
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileProcessorBase && ((TileProcessorBase)tile).isActive) {
			return 12;
		}
		return super.getLightValue(state, world, pos);
	}

	@Override
	public void breakBlock(World w, BlockPos pos, IBlockState state) {
		InvHelper.dropItemsFromTile(w, pos.getX(), pos.getY(), pos.getZ());
		super.breakBlock(w, pos, state);
	}

}
