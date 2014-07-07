package democretes.compat;

import java.lang.reflect.Method;
import java.util.Random;

import net.minecraft.world.World;

public class Botania {

	public static boolean bo = true;
	public static Method drawHUD;
	public static Method sparkle;


	public static void init() {
		try {
			Class CP = Class.forName("vazkii.botania.common.core.proxy.CommonProxy");
			for(Method method : CP.getMethods()){
				if(method.getName().equalsIgnoreCase("sparkleFX")){
					sparkle = method;
				}
			}
		} catch (Exception e) {bo=false;}
	}

	public static void client(){
		try{
			Class HUD = Class.forName("vazkii.botania.client.core.handler.HUDHandler");
			for(Method method : HUD.getMethods()){
				if(method.getName().equalsIgnoreCase("drawSimpleManaHUD")){
					drawHUD = method;
				}
			}
		}catch(Exception e){bo=false;}
	}

	public static void sparkle(World world, double d, double d1, double f, Random r){
		try {
			Class BOM = Class.forName("vazkii.botania.common.Botania");
			sparkle.invoke(BOM.getField("proxy").get(BOM), world, d, d1, f, r.nextFloat(), r.nextFloat(), 1.0F, r.nextFloat() * 4, 10);
		} catch (Exception e) {e.printStackTrace();}
	}

}
