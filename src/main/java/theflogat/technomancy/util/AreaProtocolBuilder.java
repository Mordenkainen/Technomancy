package theflogat.technomancy.util;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public abstract class AreaProtocolBuilder {

    public static final Random RAND = new Random();
    protected Coords start;
    protected Area area;

    public AreaProtocolBuilder(final Coords start, final Area area) {
        this.start = start;
        this.area = area;
    }

    public boolean buildNext(final World w, final Block b, final Coords core) {
        while (area.hasNext()) {
            final Coords c = area.next(core);
            if (c != null && isPosValid(c)) {
                w.setBlock(start.x + c.x, start.y + c.y, start.z + c.z, b);
                return true;
            }
        }
        return false;
    }

    public abstract boolean isPosValid(Coords c);

    public int remainingIterations() {
        return area.getIterationsLeft();
    }
}
