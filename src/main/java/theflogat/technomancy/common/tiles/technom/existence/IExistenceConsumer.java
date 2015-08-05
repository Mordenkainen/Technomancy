package theflogat.technomancy.common.tiles.technom.existence;

public interface IExistenceConsumer {
	public int getPower();
	public int getPowerCap();
	public int getMaxRate();
	public void addPower(int val);
	public boolean canInput();
}
