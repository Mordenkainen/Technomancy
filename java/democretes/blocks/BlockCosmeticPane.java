package democretes.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.lib.Ids;
import democretes.lib.Names;
import democretes.lib.Ref;

public class BlockCosmeticPane extends BlockBase {
		
	public BlockCosmeticPane(int id) {
		super(id);
		this.setUnlocalizedName(Ref.MOD_PREFIX + Names.cosmeticPane);		
	}
	
	
	@SideOnly(Side.CLIENT)
	private Icon glassIcon;
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister icon) {
		glassIcon = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.cosmeticOpaque);
	}
	public Icon getIcon(int side, int meta) {
		return glassIcon;
	}

	public boolean isOpaqueCube() {
		return false;
	}
	
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderPass() {
		return 1;
	}
	
	public int getRenderType() {
		return 1;
	}
    
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)    {
        int i1 = par1IBlockAccess.getBlockId(par2, par3, par4);
        return i1 == this.blockID ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }
    
    public final boolean canThisPaneConnectToThisBlockID(int par1)    {
        return Block.opaqueCubeLookup[par1] || par1 == Ids.idCOSMETIC_PANE || par1 == Ids.cosmeticOpaque;
    }
    
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)    {
        boolean flag = this.canPaneConnectTo(par1World,par2, par3, par4,ForgeDirection.NORTH);
        boolean flag1 = this.canPaneConnectTo(par1World,par2, par3, par4,ForgeDirection.SOUTH);
        boolean flag2 = this.canPaneConnectTo(par1World,par2, par3, par4,ForgeDirection.WEST);
        boolean flag3 = this.canPaneConnectTo(par1World,par2, par3, par4,ForgeDirection.EAST);

        if ((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1))        {
            if (flag2 && !flag3)            {
                this.setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
            } else if (!flag2 && flag3) {
                this.setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
            }
        }        else        {
            this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }        if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1))        {
            if (flag && !flag1)            {
                this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
            }            else if (!flag && flag1)            {
                this.setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
                super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
            }
        }        else        {
            this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
    }
    
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)    {
        float f = 0.4375F;
        float f1 = 0.5625F;
        float f2 = 0.4375F;
        float f3 = 0.5625F;
        boolean flag = this.canPaneConnectTo(par1IBlockAccess,par2, par3, par4,ForgeDirection.NORTH);
        boolean flag1 = this.canPaneConnectTo(par1IBlockAccess,par2, par3, par4,ForgeDirection.SOUTH);
        boolean flag2 = this.canPaneConnectTo(par1IBlockAccess,par2, par3, par4,ForgeDirection.WEST);
        boolean flag3 = this.canPaneConnectTo(par1IBlockAccess,par2, par3, par4,ForgeDirection.EAST);

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

        this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}
	
	public boolean canPaneConnectTo(IBlockAccess access, int x, int y, int z, ForgeDirection dir)
    {
        return canThisPaneConnectToThisBlockID(access.getBlockId(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ)) || access.isBlockSolidOnSide(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ, dir.getOpposite(), false);
    }

}
