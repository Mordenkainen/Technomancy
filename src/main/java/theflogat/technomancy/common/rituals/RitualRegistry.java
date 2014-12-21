package theflogat.technomancy.common.rituals;

public class RitualRegistry {
	
	private Ritual[] rituals = new Ritual[64];
	private int current = 0;
	
	public void add(Ritual rit) {
		rituals[current] = rit;
		rit.setId(current);
		current++;
	}
	
	public Ritual getRitual(int id){
		return rituals[id];
	}
	
	public Ritual[] getRituals(){
		return rituals;
	}
	
	public int getLength(){
		return Math.max(0, current-1);
	}
}
