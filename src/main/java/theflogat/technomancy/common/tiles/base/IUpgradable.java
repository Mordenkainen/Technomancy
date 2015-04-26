package theflogat.technomancy.common.tiles.base;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.util.InvHelper;

public interface IUpgradable {
	public boolean toggleBoost();
	public boolean getBoost();
	public void setBoost(boolean newBoost);
}
