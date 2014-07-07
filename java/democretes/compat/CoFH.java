package democretes.compat;

import java.lang.reflect.Method;

public class CoFH {

	public static Method getIcon;

	public static void init() {
		
	}
	
	public static void client() {
		try{
			for(Method method : Class.forName("cofh.render.IconRegistry").getMethods()){
				if(method.getName().equalsIgnoreCase("getIcon") && method.getParameterTypes().length==1){getIcon = method;
				}
			}
		}catch(Exception e){e.printStackTrace();}
	}
}