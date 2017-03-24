package theflogat.technomancy.common.tiles.technom.existence;

import net.minecraft.nbt.NBTTagCompound;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;

public class TileExistenceFountain extends TileTechnomancy implements IExistenceProducer {

    public int power;
    private static final int POWERCAP = 1000000;
    private static final int PRODRATE = 500;

    @Override
    public void updateEntity() {
        power += Math.min(PRODRATE, POWERCAP - power);
    }

    @Override
    public void readCustomNBT(final NBTTagCompound comp) {
        power = comp.getInteger("power");
    }

    @Override
    public void writeCustomNBT(final NBTTagCompound comp) {
        comp.setInteger("power", power);
    }

    @Override
    public void writeSyncData(final NBTTagCompound compound) {
        writeCustomNBT(compound);
    }

    @Override
    public void readSyncData(final NBTTagCompound compound) {
        readCustomNBT(compound);
    }

    @Override
    public int getPower() {
        return power;
    }

    @Override
    public int getPowerCap() {
        return POWERCAP;
    }

    @Override
    public int getMaxRate() {
        return PRODRATE * 4;
    }

    @Override
    public boolean canInput() {
        return false;
    }

    @Override
    public boolean canOutput() {
        return true;
    }

    @Override
    public void addPower(final int val) {
        power += val;
    }

    public boolean isRunning() {
        return power < POWERCAP;
    }
}