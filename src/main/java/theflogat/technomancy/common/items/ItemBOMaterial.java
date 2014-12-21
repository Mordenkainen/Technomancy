package theflogat.technomancy.common.items;

import java.util.List;

import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBOMaterial extends ItemBase {

	public ItemBOMaterial() {
		setMaxStackSize(64);
		setHasSubtypes(true);
	}

	
	public IIcon[] itemIcon = new IIcon[2];
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon) {
		itemIcon[0] = icon.registerIcon(Ref.TEXTURE_PREFIX + "manaCoil");
		itemIcon[1] = icon.registerIcon(Ref.TEXTURE_PREFIX + "manaGear");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return itemIcon[meta];
	}
	
	public String getUnlocalizedName(ItemStack stack) {
		return Ref.MOD_PREFIX + Names.itemBO + "." + stack.getItemDamage();
	}
	
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		for (int i = 0; i < itemIcon.length; i++) {
			ItemStack stack  = new ItemStack(id, 1, i);
			list.add(stack);
		}
	}
}
