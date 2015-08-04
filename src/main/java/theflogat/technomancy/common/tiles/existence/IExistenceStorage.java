package theflogat.technomancy.common.tiles.existence;

public interface IExistenceStorage {
	public int getPower();
	public int getPowerCap();
	public int getMaxRate();
	public boolean canInput();
	public boolean canOutput();
}
