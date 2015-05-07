package theflogat.technomancy.common.items.technom;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCrystal extends ItemBlock{

	public ItemCrystal(Block crystal) {
		super(crystal);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack items, EntityPlayer player, List l, boolean moreInfo) {
		switch(items.getItemDamage()){
		case 0:
			l.add("Used for light rituals. Safe for decoration.");
			break;
		case 1:
			l.add("Used for dark rituals. Safe for decoration.");
			break;
		case 2:
			l.add("Used for fire rituals. Safe for decoration.");
			break;
		case 3:
			l.add("Used for nature rituals. Safe for decoration.");
			break;
		case 4:
			l.add("Used for water rituals. Safe for decoration.");
			break;
		}
	}
}
