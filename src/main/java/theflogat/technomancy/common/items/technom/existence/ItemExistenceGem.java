package theflogat.technomancy.common.items.technom.existence;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.common.items.base.ItemBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class ItemExistenceGem extends ItemBase{
	
	public ItemExistenceGem() {
		setUnlocalizedName(Ref.getId(Names.exGem));
		setMaxDamage(100);
	}
	
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack){
		return stack.getItemDamage()==0;
    }
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return stack.getItemDamage()==0 ? EnumRarity.EPIC : EnumRarity.UNCOMMON;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> l) {
		l.add(new ItemStack(this, 1, 100));
	}

	/*
	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(Ref.getAsset(Names.exGem));
	}
	*/
}