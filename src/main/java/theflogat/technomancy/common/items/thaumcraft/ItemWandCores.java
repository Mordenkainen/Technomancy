package theflogat.technomancy.common.items.thaumcraft;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.items.wands.ItemWandCasting;
import theflogat.technomancy.common.items.base.ItemBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.compat.Thaumcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWandCores extends ItemBase{

	public ItemWandCores() {
		this.setHasSubtypes(true);
	}

	public IIcon[] coresIcon = new IIcon[1];

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon) {
		coresIcon[0] = icon.registerIcon(Ref.TEXTURE_PREFIX + "electricWandCore");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int icon) {
		return this.coresIcon[icon];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Ref.MOD_PREFIX + Names.wandCores + "." + stack.getItemDamage();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		for (int i = 0; i < coresIcon.length; i++) {
			ItemStack stack  = new ItemStack(id, 1, i);
			list.add(stack);
		}
		ItemStack electric = new ItemStack(Thaumcraft.itemWandCasting, 1, 72);
		((ItemWandCasting)electric.getItem()).setCap(electric, WandCap.caps.get("thaumium"));
		((ItemWandCasting)electric.getItem()).setRod(electric, WandRod.rods.get("electric"));
		list.add(electric);
	}
}
