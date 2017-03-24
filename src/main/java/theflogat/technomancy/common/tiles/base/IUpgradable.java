package theflogat.technomancy.common.tiles.base;

public interface IUpgradable {

    boolean toggleBoost();

    boolean isBoosted();

    void setBoost(boolean newBoost);

    String getInfo();
}
