package theflogat.technomancy.util;

public class Java {
	public static void getStackTrace() {
		try{
			Class.forName("i.want.a.stack.trace");
		}catch(Exception e){e.printStackTrace();}
	}
}
