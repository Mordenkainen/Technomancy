package theflogat.technomancy.util;

import net.minecraft.nbt.NBTTagCompound;

public class Java {
	public static void getStackTrace() {
		try{
			Class.forName("i.want.a.stack.trace");
		}catch(Exception e){e.printStackTrace();}
	}
	
	public static void saveClassToNBT(NBTTagCompound comp, String key, Object o){
		if(o!=null){
			comp.setString(key, o.getClass().getName());
		}
	}
	
	public static Object getInstanceFromNBT(NBTTagCompound comp, String key) throws Exception{
		if(comp.hasKey(key)){
			Class<?> c = Class.forName(comp.getString(key));
			Object o = c.newInstance();
			return o;
		}
		return null;
	}
}
