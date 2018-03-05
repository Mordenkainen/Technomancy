package theflogat.technomancy.common.items.bloodmagic;

import java.util.List;

import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.items.base.ItemBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBMMaterial extends ItemBase {

	public ItemBMMaterial() {
		setMaxStackSize(64);
		setHasSubtypes(true);
		setUnlocalizedName(Ref.MOD_PREFIX + Names.itemBM);
		setRegistryName("itembm");
		Technomancy.proxy.registerWithMapper(this, getNames());
	}

	public String[] getNames() {
		String[] str = new String[2];
		str[0] = "itembm.0";
		str[1] = "itembm.1";
		return str;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Ref.MOD_PREFIX + Names.itemBM + "." + stack.getItemDamage();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		for (int i = 0; i < 2; i++) {
			ItemStack stack  = new ItemStack(this, 1, i);
			list.add(stack);
		}
	}
}
