package democretes.lib;

public class Conf {
	public static boolean fancy = true;
	public static int[] blacklist = {};
	public static boolean bonus = true;
	public static boolean debug = false;
	
	public static void ex(Exception e) {
		if(debug)
			e.printStackTrace();
	}
}
