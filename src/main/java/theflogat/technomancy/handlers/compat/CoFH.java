package theflogat.technomancy.handlers.compat;

import java.lang.reflect.Method;

import theflogat.technomancy.lib.Conf;

public class CoFH {

	public static boolean co = true;
	public static Method getIcon;

	public static void init() {
		
	}
	
	public static void client() {
		try{
			for(Method method : Class.forName("cofh.core.render.IconRegistry").getMethods()){
				if(method.getName().equalsIgnoreCase("getIcon") && method.getParameterTypes().length==1){getIcon = method;
				}
			}
		}catch(Exception e){System.out.println("Technomancy: Failed to load CoFH Module");Conf.ex(e);co = false;}
	}
}