package theflogat.technomancy.api.rituals;


public class RitualRegistry {
	
	private static Ritual[] rituals = new Ritual[64];
	private static int current = 0;
	
	public static int add(Ritual rit) {
		rituals[current] = rit;
		rit.setId(current);
		return current++;
	}
	
	public static Ritual getRitual(int id){
		return rituals[id];
	}
	
	public static Ritual[] getRituals(){
		return rituals;
	}
	
	public static int getLength(){
		return Math.max(0, current-1);
	}
}
