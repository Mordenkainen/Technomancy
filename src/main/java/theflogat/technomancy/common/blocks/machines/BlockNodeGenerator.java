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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
		blockIcon = iconRegister.registerIcon(Ref.getAsset(Names.nodeGenerator));
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
	public void breakBlock(World w, int x, int y, int z, Block block, int meta) {
		TileNodeGenerator tile = getTE(w, x, y, z);
		if(tile.boost)
				InvHelper.spawnEntItem(w, x, y, z, new ItemStack(TMItems.itemBoost, 1));
		super.breakBlock(w, x, y, z, block, meta);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		if (player.getHeldItem() == null && player.isSneaking()) {
			TileNodeGenerator tile = (TileNodeGenerator)world.getTileEntity(x, y, z);
			if(tile.boost) {
				if(!world.isRemote) {
					InvHelper.spawnEntItem(world, x, y, z, new ItemStack(TMItems.itemBoost, 1));
				}
				tile.setBoost(false);
				return true;
			}
		}
		return false;
	}
	
	private TileNodeGenerator getTE(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileNodeGenerator) {
			return (TileNodeGenerator)tile;
		}
		return null;
	}
}
