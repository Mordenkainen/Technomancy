package theflogat.technomancy.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.util.helpers.WorldHelper;

public class Coords {

    public int x;
    public int y;
    public int z;
    public World w;

    public Coords(final int x, final int y, final int z, final World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        w = world;
    }

    public Coords(final TileEntity te) {
        x = te.xCoord;
        y = te.yCoord;
        z = te.zCoord;
        w = te.getWorldObj();
    }

    @Override
    public boolean equals(final Object o) {
        final Coords c = (Coords) o;
        return x == c.x && y == c.y && z == c.z;
    }

    public TileEntity getTile() {
        return w.getTileEntity(x, y, z);
    }

    public void setAirAndDrop() {
        WorldHelper.destroyAndDrop(w, x, y, z);
    }

    public void print() {
        System.out.println("X:" + x + " Y:" + y + " Z:" + z);
    }
}