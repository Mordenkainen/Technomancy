package theflogat.technomancy.common.blocks.technom;

import java.util.ArrayList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
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
import theflogat.technomancy.common.blocks.base.BlockCoilTransmitter;
import theflogat.technomancy.common.items.technom.ItemBoost;
import theflogat.technomancy.common.items.technom.ItemCoilCoupler;
import theflogat.technomancy.common.tiles.technom.TileItemTransmitter;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;

public class BlockItemTransmitter extends BlockCoilTransmitter{

	public BlockItemTransmitter() {
		super();
		setUnlocalizedName(Ref.getId(Names.itemTransmitter));
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
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileItemTransmitter();
	}

	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack items = player.inventory.mainInventory.get(player.inventory.currentItem);
		if(!items.isEmpty() && (items.getItem() instanceof ItemCoilCoupler || items.getItem() instanceof ItemBoost)) {
			return false;
		}
		boolean flag = super.onBlockActivated(w, pos, state, player, hand, facing, hitX, hitY, hitZ);
		TileItemTransmitter tile = getTE(w, pos.getX(), pos.getY(), pos.getZ());
		if(tile != null) {
			if(player.isSneaking() && !flag) {
				if(!tile.filter.isEmpty()) {
					if(!w.isRemote) {
						tile.filter = ItemStack.EMPTY;
						w.notifyBlockUpdate(pos, state, w.getBlockState(pos), 3);
					}
					return true;
				}
			} else if(!items.isEmpty() && !tile.filter.isEmpty()) {
				if(!w.isRemote) {
					tile.addFilter(player.inventory.mainInventory.get(player.inventory.currentItem));
					w.notifyBlockUpdate(pos, state, w.getBlockState(pos), 3);
				}
				return true;
			}
		}
		return flag;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void getNBTInfo(NBTTagCompound comp, ArrayList<String> l, int meta) {
		super.getNBTInfo(comp, l, meta);
		if(comp.hasKey("filter")){
			NBTTagCompound item = comp.getCompoundTag("filter");
			ItemStack filter = new ItemStack(item);
			l.add("Filter: " + I18n.format(filter.getUnlocalizedName() + ".name"));
		}
	}
	
	private static TileItemTransmitter getTE(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		return tile instanceof TileItemTransmitter ? (TileItemTransmitter)tile : null;
	}
}
