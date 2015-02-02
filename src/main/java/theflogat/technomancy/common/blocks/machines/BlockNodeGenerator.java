package theflogat.technomancy.common.blocks.machines;

import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.util.InvHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNodeGenerator extends BlockBase {

	public BlockNodeGenerator() {
		setBlockName(Ref.MOD_PREFIX + Names.nodeGenerator);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
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
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon(Ref.getAsset(Names.nodeGenerator));
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
		int facing = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
		TileNodeGenerator tile = getTE(world, x, y, z);
		if (tile != null) {
			if (facing == 0) {
				tile.facing = 2;
			} else if (facing == 1) {
				tile.facing = 5;
			} else if (facing == 2) {
				tile.facing = 3;
			} else if (facing == 3) {
				tile.facing = 4;
			}
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)	  {
		//		TileEntity tile = world.getTileEntity(x, y, z);
		//		if(tile instanceof TileNodeGenerator) {
		//			if(((TileNodeGenerator)tile).facing == 2 || ((TileNodeGenerator)tile).facing == 3) {
		//				return AxisAlignedBB.getBoundingBox(x - 1, y, z, x + 2, y + 3, z + 1);
		//			}else if(((TileNodeGenerator)tile).facing == 1 || ((TileNodeGenerator)tile).facing == 4) {
		//				return AxisAlignedBB.getBoundingBox(x, y, z - 1, x + 1, y + 3, z + 2);
		//			}
		//		}
		//	    return null;
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}


	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
//		TileEntity tile = access.getTileEntity(x, y, z);
//		if(((TileNodeGenerator)tile).facing == 1 || ((TileNodeGenerator)tile).facing == 3) {
//			setBlockBounds(-1.0F, 0.0F, 0.0F, 2.0F, 3.0F, 1.0F);
//		}else if(((TileNodeGenerator)tile).facing == 2 || ((TileNodeGenerator)tile).facing == 4){
//			setBlockBounds(0.0F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F);
//		}
	}

	@Override
	public void breakBlock(World w, int x, int y, int z, Block block, int meta) {
		TileNodeGenerator tile = getTE(w, x, y, z);
		if(tile.boost)
				InvHelper.spawnEntItem(w, x, y, z, new ItemStack(TMItems.itemBoost, 1));
		super.breakBlock(w, x, y, z, block, meta);
	}
	
	private TileNodeGenerator getTE(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileNodeGenerator) {
			return (TileNodeGenerator)tile;
		}
		return null;
	}
}
