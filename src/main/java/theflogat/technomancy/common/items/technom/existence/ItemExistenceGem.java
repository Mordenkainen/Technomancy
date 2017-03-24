package theflogat.technomancy.common.items.technom.existence;

import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import theflogat.technomancy.common.items.base.ItemBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemExistenceGem extends ItemBase {

    public ItemExistenceGem() {
        super();
        setUnlocalizedName(Reference.getId(Names.EXGEM));
        setMaxDamage(100);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(final ItemStack stack) {
        return stack.getItemDamage() == 0;
    }

    @Override
    public EnumRarity getRarity(final ItemStack stack) {
        return stack.getItemDamage() == 0 ? EnumRarity.epic : EnumRarity.uncommon;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(final Item item, final CreativeTabs tab, final List l) {
        l.add(new ItemStack(item, 1, 100));
    }

    @Override
    public void registerIcons(final IIconRegister reg) {
        itemIcon = reg.registerIcon(Reference.getAsset(Names.EXGEM));
    }
}