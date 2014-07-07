package democretes.blocks.machines;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.blocks.BlockBase;
import democretes.blocks.machines.tiles.TileNodeGenerator;
import democretes.lib.Names;
import democretes.lib.Ref;
import democretes.lib.RenderIds;

public class BlockNodeGenerator extends BlockBase {

	public BlockNodeGenerator(int id) {
		super(id);
		setUnlocalizedName(Ref.MOD_PREFIX + Names.nodeGenerator);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileNodeGenerator();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return RenderIds.idNodeGenerator;
	}
	
	@SideOnly(Side.CLIENT)
	public Icon iconGenerator;
	
	@SideOnly(Side.CLIENT)
	public void RegisterIcons(IconRegister icon) {
		this.iconGenerator = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.condenserBlock);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
		int face = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if ((tile instanceof TileNodeGenerator)) {
			if (face == 0) {
				((TileNodeGenerator)tile).facing = 2;
			}
			if (face == 1) {
				((TileNodeGenerator)tile).facing = 5;
			}
			if (face == 2) {
				((TileNodeGenerator)tile).facing = 3;
			}
			if (face == 3) {
				((TileNodeGenerator)tile).facing = 4;
			}
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)	  {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileNodeGenerator) {
			if(((TileNodeGenerator)tile).facing == 1 || ((TileNodeGenerator)tile).facing == 3) {
				return AxisAlignedBB.getAABBPool().getAABB(x - 1, y, z, x + 2, y + 3, z + 1);
			}else if(((TileNodeGenerator)tile).facing == 2 || ((TileNodeGenerator)tile).facing == 4) {
				return AxisAlignedBB.getAABBPool().getAABB(x, y, z - 1, x + 1, y + 3, z + 2);
			}
		}
	    return null;
	  }
	

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
		TileEntity tile = access.getBlockTileEntity(x, y, z);
		if(((TileNodeGenerator)tile).facing == 1 || ((TileNodeGenerator)tile).facing == 3) {
			setBlockBounds(-1.0F, 0.0F, 0.0F, 2.0F, 3.0F, 1.0F);
		}else if(((TileNodeGenerator)tile).facing == 2 || ((TileNodeGenerator)tile).facing == 4){
			setBlockBounds(0.0F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F);
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);		
		int xx = z;
		int zz = x;	
		if(((TileNodeGenerator)tile).active == true) {
			switch (((TileNodeGenerator)tile).facing) {
			case 2:
				zz -= 6;break;
			case 3: 
				zz += 6;break;
			case 4:
				xx -= 6;break;
			case 5:
				xx += 6;break;
			}
			TileEntity entity = world.getBlockTileEntity(xx, y, zz);
			if( entity instanceof TileNodeGenerator) {
				((TileNodeGenerator)entity).active = false;
			}
		}
		super.breakBlock(world, x, y, z, id, meta);
	}

}
