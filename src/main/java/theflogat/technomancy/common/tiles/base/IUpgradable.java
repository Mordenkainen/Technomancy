package theflogat.technomancy.common.tiles.base;


public interface IUpgradable {
	public boolean toggleBoost();
	public boolean getBoost();
	public void setBoost(boolean newBoost);
	public String getInfos();
}
