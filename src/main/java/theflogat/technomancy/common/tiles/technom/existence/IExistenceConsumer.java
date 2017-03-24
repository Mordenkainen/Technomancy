package theflogat.technomancy.common.tiles.technom.existence;

public interface IExistenceConsumer {

    int getPower();

    int getPowerCap();

    int getMaxRate();

    void addPower(int val);

    boolean canInput();
}
