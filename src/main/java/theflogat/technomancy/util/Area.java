package theflogat.technomancy.util;

public class Area {

    public int lengthX;
    public int lengthY;
    public int lengthZ;
    public int currX;
    public int currY;
    public int currZ;
    public boolean end;

    public Area(final int lengthX, final int lengthY, final int lengthZ) {
        this.lengthX = lengthX;
        this.lengthY = lengthY;
        this.lengthZ = lengthZ;
    }

    public boolean hasNext() {
        return !end;
    }

    public Coords next(final Coords core) {
        final Coords c = new Coords(currX, currY, currZ, null);
        if (currX == lengthX) {
            if (currZ == lengthZ) {
                if (currY == lengthY) {
                    currX = 0;
                    currY = 0;
                    currZ = 0;
                    end = true;
                } else {
                    currX = 0;
                    currZ = 0;
                    currY++;
                }
            } else {
                currX = 0;
                currZ++;
            }
        } else {
            currX++;
        }
        if (!core.equals(c)) {
            return c;
        }
        return null;
    }

    public void reiterate() {
        currX = 0;
        currY = 0;
        currZ = 0;
        end = false;
    }

    public int getIterationsLeft() {
        return (lengthX - currX) * (lengthY - currY) * (lengthZ - currZ);
    }
}