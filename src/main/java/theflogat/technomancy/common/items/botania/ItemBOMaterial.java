package theflogat.technomancy.common.items.botania;

import java.util.List;

import net.minecraft.util.NonNullList;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.items.base.ItemBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBOMaterial extends ItemBase {

	public ItemBOMaterial() {
		setMaxStackSize(64);
		setHasSubtypes(true);
		this.setUnlocalizedName(Ref.MOD_PREFIX + Names.itemBO);
		setRegistryName("itembo");
		Technomancy.proxy.registerWithMapper(this, getNames());
	}

	public String[] getNames() {
		String[] str = new String[2];
		str[0] = "itembo.0";
		str[1] = "itembo.1";
		return str;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Ref.MOD_PREFIX + Names.itemBO + "." + stack.getItemDamage();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		for (int i = 0; i < 2; i++) {
			ItemStack stack  = new ItemStack(this, 1, i);
			list.add(stack);
		}
	}
}
