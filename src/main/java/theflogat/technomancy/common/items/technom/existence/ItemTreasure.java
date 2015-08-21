package theflogat.technomancy.common.items.technom.existence;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import theflogat.technomancy.common.items.base.ItemBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class ItemTreasure extends ItemBase{
	
	/**
	 * Out of 100
	 * 100 is 1 in 100
	 */
	public static final int[] rarity = {75};
	
	public String getUnlocalizedName(ItemStack stack){
		return Ref.getId(Names.treasures[stack.getItemDamage()%Names.treasures.length]);
	}
	
	@SideOnly(Side.CLIENT)
	IIcon[] icons = new IIcon[Names.treasures.length];
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		for(int i=0;i<icons.length;i++){
			icons[i] = reg.registerIcon(Ref.getAsset(Names.treasures[i]));
		}
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
		return icons[meta%icons.length];
	}
}