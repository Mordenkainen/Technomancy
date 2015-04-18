package theflogat.technomancy.api.tiles;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import net.minecraft.tileentity.TileEntity;

public class MovableTileRegistry {
	
	public static ArrayList<Class<? extends TileEntity>> allowed = new ArrayList<Class<? extends TileEntity>>();
	public static LinkedHashMap<Class<? extends TileEntity>, IMovingHandler> specialHandler = new LinkedHashMap<Class<? extends TileEntity>, IMovingHandler>();
	
	public static void addAllowed(Class<? extends TileEntity> c) {
		if(!allowed.contains(c))
			allowed.add(c);
	}
	
	public static void addWithSpecialHandler(Class<? extends TileEntity> c, IMovingHandler h) {
		if(!specialHandler.containsKey(c))
			specialHandler.put(c, h);
	}
	
}
