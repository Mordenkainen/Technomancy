package theflogat.technomancy.handlers.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import cofh.api.energy.IEnergyHandler;

public class WorldHelper {

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
}
