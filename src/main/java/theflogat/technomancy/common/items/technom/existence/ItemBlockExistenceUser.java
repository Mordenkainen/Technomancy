package theflogat.technomancy.common.items.technom.existence;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class ItemBlockExistenceUser extends ItemBlock{

	public ItemBlockExistenceUser(Block b) {
		super(b);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack items) {
		return Ref.getId(Names.existenceUser[items.getItemDamage()%Names.existenceUser.length]);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> l) {
		for(int i=0;i<Names.existenceUser.length;i++){
			l.add(new ItemStack(this, 1, i));
		}
	}
}
