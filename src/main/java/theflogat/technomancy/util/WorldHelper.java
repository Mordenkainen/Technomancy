package theflogat.technomancy.util;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import theflogat.technomancy.common.items.base.TMItems;
import cofh.api.energy.IEnergyHandler;

public class WorldHelper {

	public static boolean destroyAndDrop(World w, int x, int y, int z){
		Block block = w.getBlock(x, y, z);
		if(block!=null && block.getBlockHardness(w, x, y, z)>=0 && !w.isRemote){
			ArrayList<ItemStack> d = block.getDrops(w, x, y, z, w.getBlockMetadata(x, y, z), 0);
			for(ItemStack s : d){
				spawnEntItem(w, x, y, z, s);
			}
			w.setBlockToAir(x, y, z);
			return true;
		}
		return false;
	}
	
	public static void spawnEntItem(World w, double x, double y, double z, ItemStack items){
		if ((items != null) && (items.stackSize > 0)) {
			float rx = w.rand.nextFloat() * 0.8F + 0.1F;
			float ry = w.rand.nextFloat() * 0.8F + 0.1F;
			float rz = w.rand.nextFloat() * 0.8F + 0.1F;

			EntityItem entityItem = new EntityItem(w, x + rx, y + ry, z + rz, new ItemStack(items.getItem(), items.stackSize, items.getItemDamage()));

			if (items.hasTagCompound()) {
				entityItem.getEntityItem().setTagCompound((NBTTagCompound)items.getTagCompound().copy());
			}

			float factor = 0.05F;
			entityItem.motionX = (w.rand.nextGaussian() * factor);
			entityItem.motionY = (w.rand.nextGaussian() * factor + 0.2000000029802322D);
			entityItem.motionZ = (w.rand.nextGaussian() * factor);
			w.spawnEntityInWorld(entityItem);
		}
	}

	public static TileEntity getAdjacentTileEntity(TileEntity tile, byte facing) {
		if(tile==null || tile.getWorldObj()==null)
			return null;
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[facing%ForgeDirection.VALID_DIRECTIONS.length];
		return tile.getWorldObj().getTileEntity(tile.xCoord + dir.offsetX, tile.yCoord + dir.offsetY, tile.zCoord + dir.offsetZ);
	}

	public static boolean isEnergyHandlerFromOppFacing(TileEntity tile, byte facing) {
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[facing%ForgeDirection.VALID_DIRECTIONS.length];
		return tile instanceof IEnergyHandler ? ((IEnergyHandler) tile).canConnectEnergy(dir.getOpposite()) : false;
	}

	public static int insertFluidIntoAdjacentFluidHandler(TileEntity tile, int side, FluidStack fluid, boolean doFill) {
		TileEntity handler = getAdjacentTileEntity(tile, (byte) side);
		return handler instanceof IFluidHandler ? ((IFluidHandler) handler).fill(ForgeDirection.VALID_DIRECTIONS[side ^ 1], fluid, doFill) : 0;
	}

	public static boolean isAdjacentFluidHandler(TileEntity entity, byte i) {
		return getAdjacentTileEntity(entity, i)!=null && getAdjacentTileEntity(entity, i) instanceof IFluidHandler;
	}
	
	public static void dropBoost(World w, int x, int y, int z, EntityPlayer player){
		spawnEntItem(w, x, y, z, new ItemStack(TMItems.itemBoost, 1));
	}
	
	public static boolean isChunkLoaded(World w, int x, int z){
		return w.getChunkFromBlockCoords(x, z).isChunkLoaded;
	}
}
