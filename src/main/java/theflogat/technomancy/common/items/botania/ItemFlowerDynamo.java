package theflogat.technomancy.common.items.botania;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemFlowerDynamo extends ItemBlock{

	public ItemFlowerDynamo(Block block) {
		super(block);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack items, EntityPlayer player, List l, boolean moreInfo) {
		l.add("You can change the redstone behaviour by:");
		l.add("Using redstone: Requires a redstone signal");
		l.add("Using a redstone torch: Will stop if it receives a signal");
		l.add("Using gunpowder: Ignores restone");
	}
}
