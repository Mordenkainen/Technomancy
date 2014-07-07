package democretes.lib;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.FMLCommonHandler;

public class Platform {

	public static boolean isClient(){
		return FMLCommonHandler.instance().getEffectiveSide().isClient();
	}
	
	public static boolean isServer(){
		return FMLCommonHandler.instance().getEffectiveSide().isServer();
	}

//	public static TileEntity getAdjacentTileEntity(World w, TileEntity tile, int facing) {
//		if(tile!=null && facing<=6 && facing>=0){
//			return w.getBlockTileEntity(ForgeDirection.VALID_DIRECTIONS[facing].offsetX, ForgeDirection.VALID_DIRECTIONS[facing].offsetY,
//										ForgeDirection.VALID_DIRECTIONS[facing].offsetZ);
//		}
//		
//		return null;
//	}

}