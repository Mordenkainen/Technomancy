package theflogat.technomancy.util;

import net.minecraftforge.fml.common.FMLCommonHandler;

public class Loc {
	
	public static boolean isClient(){
		return FMLCommonHandler.instance().getEffectiveSide().isClient();
	}

	public static boolean isServer(){
		return FMLCommonHandler.instance().getEffectiveSide().isServer();
	}
	
}
