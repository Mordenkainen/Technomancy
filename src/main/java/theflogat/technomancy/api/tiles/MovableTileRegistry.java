package theflogat.technomancy.api.tiles;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;

public final class MovableTileRegistry {
    
    public static List<Class<? extends TileEntity>> allowed = new ArrayList<Class<? extends TileEntity>>();
    public static Map<Class<? extends TileEntity>, IMovingHandler> specialHandler = new LinkedHashMap<Class<? extends TileEntity>, IMovingHandler>();

    private MovableTileRegistry() {}

    public static void addAllowed(final Class<? extends TileEntity> c) {
        if (!allowed.contains(c)) {
            allowed.add(c);
        }
    }

    public static void addWithSpecialHandler(final Class<? extends TileEntity> c, final IMovingHandler h) {
        if (!specialHandler.containsKey(c)) {
            specialHandler.put(c, h);
        }
    }

}
