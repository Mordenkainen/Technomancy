package theflogat.technomancy.common.blocks.base;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

import javax.annotation.Nullable;

public class BlockCosmeticPane extends BlockBase {
		
	public BlockCosmeticPane() {
		super();
		this.setUnlocalizedName(Ref.MOD_PREFIX + Names.cosmeticPane);
	}

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    /**
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.cosmeticOpaque);
	}
     */

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess w, BlockPos pos, EnumFacing side) {
        return w.getBlockState(pos) == this ? false : super.shouldSideBeRendered(blockState, w, pos, side);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void addCollisionBoxToList(IBlockState state, World par1World, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        int par2 = pos.getX();
        int par3 = pos.getY();
        int par4 = pos.getZ();
        boolean flag = this.canPaneConnectTo(par1World,par2, par3, par4,EnumFacing.NORTH);
        boolean flag1 = this.canPaneConnectTo(par1World,par2, par3, par4,EnumFacing.SOUTH);
        boolean flag2 = this.canPaneConnectTo(par1World,par2, par3, par4,EnumFacing.WEST);
        boolean flag3 = this.canPaneConnectTo(par1World,par2, par3, par4,EnumFacing.EAST);

        if ((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1))        {
            if (flag2 && !flag3)            {
                collidingBoxes.add(new AxisAlignedBB(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F));
                super.addCollisionBoxToList(state, par1World, pos, entityBox, collidingBoxes, entityIn, isActualState);
            } else if (!flag2 && flag3) {
                collidingBoxes.add(new AxisAlignedBB(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F));
                super.addCollisionBoxToList(state, par1World, pos, entityBox, collidingBoxes, entityIn, isActualState);
            }
        }        else        {
            collidingBoxes.add(new AxisAlignedBB(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F));
            super.addCollisionBoxToList(state, par1World, pos, entityBox, collidingBoxes, entityIn, isActualState);
        }        if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1))        {
            if (flag && !flag1)            {
                collidingBoxes.add(new AxisAlignedBB(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F));
                super.addCollisionBoxToList(state, par1World, pos, entityBox, collidingBoxes, entityIn, isActualState);
            }            else if (!flag && flag1)            {
                collidingBoxes.add(new AxisAlignedBB(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F));
                super.addCollisionBoxToList(state, par1World, pos, entityBox, collidingBoxes, entityIn, isActualState);
            }
        }        else        {
            collidingBoxes.add(new AxisAlignedBB(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F));
            super.addCollisionBoxToList(state, par1World, pos, entityBox, collidingBoxes, entityIn, isActualState);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        float f = 0.4375F;
        float f1 = 0.5625F;
        float f2 = 0.4375F;
        float f3 = 0.5625F;
        boolean flag = this.canPaneConnectTo(source,pos.getX(), pos.getY(), pos.getZ(),EnumFacing.NORTH);
        boolean flag1 = this.canPaneConnectTo(source,pos.getX(), pos.getY(), pos.getZ(),EnumFacing.SOUTH);
        boolean flag2 = this.canPaneConnectTo(source,pos.getX(), pos.getY(), pos.getZ(),EnumFacing.WEST);
        boolean flag3 = this.canPaneConnectTo(source,pos.getX(), pos.getY(), pos.getZ(),EnumFacing.EAST);

        if ((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1))        {
            if (flag2 && !flag3)            {
                f = 0.0F;
            }            else if (!flag2 && flag3)            {
                f1 = 1.0F;
            }
        }        else        {
            f = 0.0F;
            f1 = 1.0F;
        }
        if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1))        {
            if (flag && !flag1)            {
                f2 = 0.0F;
            }            else if (!flag && flag1)            {
                f3 = 1.0F;
            }
        }        else        {
            f2 = 0.0F;
            f3 = 1.0F;
        }

        return  new AxisAlignedBB(f, 0.0F, f2, f1, 1.0F, f3);
    }

	
	public boolean canPaneConnectTo(IBlockAccess access, int x, int y, int z, EnumFacing dir)
    {
        return access.getBlockState(new BlockPos(x+dir.getFrontOffsetX(), y+dir.getFrontOffsetY(), z+dir.getFrontOffsetZ())).isSideSolid(access, new BlockPos(x, y, z), dir);
    }

}
