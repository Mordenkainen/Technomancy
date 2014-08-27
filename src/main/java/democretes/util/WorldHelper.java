package democretes.util;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class WorldHelper {

	public static TileEntity getAdjacentTileEntity(TileEntity tile, byte facing) {
		if(tile==null || tile.getWorldObj()==null)
			return null;
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[facing%ForgeDirection.VALID_DIRECTIONS.length];
		
		return tile.getWorldObj().getBlockTileEntity(tile.xCoord + dir.offsetX, tile.yCoord + dir.offsetY, tile.zCoord + dir.offsetZ);
	}
	
	public static boolean isEnergyHandler(TileEntity tile, byte facing) {
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[facing%ForgeDirection.VALID_DIRECTIONS.length];
		return tile instanceof IEnergyHandler ? ((IEnergyHandler) tile).canInterface(dir) : false;
	}
}
