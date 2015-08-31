package theflogat.technomancy.common.items.technom.existence;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class ItemBlockExistenceBurner extends ItemBlock{

	public ItemBlockExistenceBurner(Block b) {
		super(b);
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List l) {
		l.add(new ItemStack(item, 1, 0));
		l.add(new ItemStack(item, 1, 1));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack items) {
		return Ref.getId(Names.existenceBurner[items.getItemDamage()%2]);
	}
}