package theflogat.technomancy.lib;

import theflogat.technomancy.Technomancy;

public class Conf {
	public static boolean fancy = true;
	public static int[] blacklist = {};
	public static boolean bonus = true;
	public static boolean debug = false;
	public static boolean	mkfirst;
	
	public static void ex(Exception e) {
		if(debug) {
			Technomancy.logger.error("Exception:", e);
		}
	}	
}
