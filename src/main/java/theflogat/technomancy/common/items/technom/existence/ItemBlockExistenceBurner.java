package theflogat.technomancy.common.items.technom.existence;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class ItemBlockExistenceBurner extends ItemBlock {

	public ItemBlockExistenceBurner(Block b) {
		super(b);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> l) {
		l.add(new ItemStack(this, 1, 0));
		l.add(new ItemStack(this, 1, 1));
	}

	@Override
	public String getUnlocalizedName(ItemStack items) {
		return Ref.getId(Names.existenceBurner[items.getItemDamage()%2]);
	}
}