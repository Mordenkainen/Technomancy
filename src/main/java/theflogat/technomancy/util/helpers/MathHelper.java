package theflogat.technomancy.util.helpers;

public class MathHelper {
	
	public static int round(double d) {
		return (int) (d+0.5D);
	}
	
	public static boolean within(double s, double e, double i){
		return i>=s && i<=e;
	}
	
	public static boolean withinStrict(double s, double e, double i){
		return i>s && i<e;
	}
}
