package theflogat.technomancy.util;

import net.minecraft.nbt.NBTTagCompound;

public final class Java {
    
    private Java() {}

    public static void getStackTrace() {
        try {
            Class.forName("i.want.a.stack.trace");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveClassToNBT(final NBTTagCompound comp, final String key, final Object o) {
        if (o != null) {
            comp.setString(key, o.getClass().getName());
        }
    }

    public static Object getInstanceFromNBT(final NBTTagCompound comp, final String key) throws Exception {
        if (comp.hasKey(key)) {
            final Class<?> c = Class.forName(comp.getString(key));
            return c.newInstance();
        }
        return null;
    }
}
