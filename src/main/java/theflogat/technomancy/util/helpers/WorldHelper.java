package theflogat.technomancy.util.helpers;

import java.util.List;

import cofh.redstoneflux.api.IEnergyHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import theflogat.technomancy.common.items.base.TMItems;

public class WorldHelper {

	public static boolean destroyAndDrop(World w, int x, int y, int z){
		Block block = w.getBlockState(new BlockPos(x, y, z)).getBlock();
		if(block!=null && block.getBlockHardness(block.getDefaultState(), w, new BlockPos(x, y, z))>=0) {
			if(!w.isRemote){
				block.breakBlock(w, new BlockPos(x, y, z), w.getBlockState(new BlockPos(x, y, z)));
				List<ItemStack> d = block.getDrops(w, new BlockPos(x, y, z), w.getBlockState(new BlockPos(x, y, z)), 0);
				for(ItemStack s : d){
					spawnEntItem(w, x, y, z, s);
				}
			}
			w.setBlockToAir(new BlockPos(x, y, z));
				
			return true;
		}
		return false;
	}
	
	public static void spawnEntItem(World w, double x, double y, double z, ItemStack items){
		if ((items != null) && (items.getCount() > 0)) {
			float rx = w.rand.nextFloat() * 0.8F + 0.1F;
			float ry = w.rand.nextFloat() * 0.8F + 0.1F;
			float rz = w.rand.nextFloat() * 0.8F + 0.1F;

			EntityItem entityItem = new EntityItem(w, x + rx, y + ry, z + rz, new ItemStack(items.getItem(), items.getCount(), items.getItemDamage()));

			if (items.hasTagCompound()) {
				entityItem.getItem().setTagCompound((NBTTagCompound)items.getTagCompound().copy());
			}

			float factor = 0.05F;
			entityItem.motionX = (w.rand.nextGaussian() * factor);
			entityItem.motionY = (w.rand.nextGaussian() * factor + 0.2000000029802322D);
			entityItem.motionZ = (w.rand.nextGaussian() * factor);
			w.spawnEntity(entityItem);
		}
	}

	public static TileEntity getAdjacentTileEntity(TileEntity tile, byte facing) {
		if(tile==null || tile.getPos()==null)
			return null;
		EnumFacing dir = EnumFacing.VALUES[facing%EnumFacing.VALUES.length];
		return tile.getWorld().getTileEntity(new BlockPos(tile.getPos().getX() + dir.getFrontOffsetX(), tile.getPos().getY() + dir.getFrontOffsetY(), tile.getPos().getZ() + dir.getFrontOffsetZ()));
	}
	
	public static TileEntity getAdjacentTileEntity(TileEntity tile, EnumFacing dir) {
		if(tile==null || tile.getWorld()==null && dir==null)
			return null;
		return tile.getWorld().getTileEntity(new BlockPos(tile.getPos().getX() + dir.getFrontOffsetX(), tile.getPos().getY() + dir.getFrontOffsetY(), tile.getPos().getZ() + dir.getFrontOffsetZ()));
	}

	public static boolean isEnergyHandlerFromOppFacing(TileEntity tile, byte facing) {
		EnumFacing dir = EnumFacing.VALUES[facing%EnumFacing.VALUES.length];
		return tile instanceof IEnergyHandler ? ((IEnergyHandler) tile).canConnectEnergy(dir.getOpposite()) : false;
	}

	public static int insertFluidIntoAdjacentFluidHandler(TileEntity tile, int side, FluidStack fluid, boolean doFill) {
		TileEntity handler = getAdjacentTileEntity(tile, (byte) side);
		return handler instanceof IFluidHandler ? ((IFluidHandler) handler).fill(fluid, doFill) : 0;
	}

	public static boolean isAdjacentFluidHandler(TileEntity entity, byte i) {
		return getAdjacentTileEntity(entity, i)!=null && getAdjacentTileEntity(entity, i) instanceof IFluidHandler;
	}
	
	public static void dropBoost(World w, int x, int y, int z){
		spawnEntItem(w, x, y, z, new ItemStack(TMItems.itemBoost, 1));
	}
	
	public static boolean isChunkLoaded(World w, int x, int z){
		return w.getChunkFromBlockCoords(new BlockPos(x, 0, z)).isLoaded();
	}
	
	public static boolean isPlayerProbablyCollidingWithBlock(EntityPlayer player, int x, int y, int z) {
		return Math.floor(player.posX)-1>x && Math.ceil(player.posX)<x &&
				Math.floor(player.posY)-1>y && Math.ceil(player.posY)<y &&
				Math.floor(player.posZ)-1>z && Math.ceil(player.posZ)<z;
	}
}
