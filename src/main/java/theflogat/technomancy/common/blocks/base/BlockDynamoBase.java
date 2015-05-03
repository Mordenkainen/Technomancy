package theflogat.technomancy.common.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive.RedstoneSet;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.util.InvHelper;

public abstract class BlockDynamoBase extends BlockBase {

	public BlockDynamoBase() {
		super();
	}

	@Override
	public void breakBlock(World w, int x, int y, int z, Block block, int meta) {
		TileDynamoBase tile = (TileDynamoBase) w.getTileEntity(x, y, z);
		if(tile.boost) {
			InvHelper.spawnEntItem(w, x, y, z, new ItemStack(TMItems.itemBoost, 1));
		}
		super.breakBlock(w, x, y, z, block, meta);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		TileDynamoBase tile = (TileDynamoBase)world.getTileEntity(x, y, z);
		if (player.getHeldItem()==null && player.isSneaking()) {
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

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

}