package democretes.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.compat.Thaumcraft;
import democretes.lib.Names;
import democretes.lib.Ref;

public class ItemWandCores extends ItemBase{

	public ItemWandCores(int id) {
		super(id);
		this.setHasSubtypes(true);
	}

	public Icon[] coresIcon = new Icon[1];

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		coresIcon[0] = icon.registerIcon(Ref.TEXTURE_PREFIX + "electricWandCore");
	}

	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int icon) {
		return this.coresIcon[icon];
	}

	public String getUnlocalizedName(ItemStack stack) {
		return Ref.MOD_PREFIX + Names.wandCores + "." + stack.getItemDamage();
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		try{
			for (int i = 0; i < coresIcon.length; i++) {
				ItemStack stack  = new ItemStack(id, 1, i);
				list.add(stack);
			}
			ItemStack electric = new ItemStack(Thaumcraft.itemWandCasting, 1, 72);
			Thaumcraft.setCap.invoke(electric.getItem(), electric, (WandCap)WandCap.caps.get("thaumium"));
			Thaumcraft.setRod.invoke(electric.getItem(), electric, (WandRod)WandRod.rods.get("electric"));
			list.add(electric);
		}catch(Exception e){}
	}
}
