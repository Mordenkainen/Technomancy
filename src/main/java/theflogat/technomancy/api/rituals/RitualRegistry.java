package theflogat.technomancy.api.rituals;

public final class RitualRegistry {

    private static Ritual[] rituals = new Ritual[64];
    private static int current;

    private RitualRegistry() {}
    
    public static int add(final Ritual rit) {
        rituals[current] = rit;
        rit.setId(current);
        return current++;
    }

    public static Ritual getRitual(final int id) {
        return rituals[id];
    }

    public static Ritual[] getRituals() {
        return rituals.clone();
    }

    public static int getLength() {
        return Math.max(0, current - 1);
    }
}
