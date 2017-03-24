package theflogat.technomancy.common.items.bloodmagic;

import java.util.List;

import theflogat.technomancy.common.items.base.ItemBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBMMaterial extends ItemBase {
    
    public IIcon[] itemIcon = new IIcon[2];

    public ItemBMMaterial() {
        super();
        setMaxStackSize(64);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister icon) {
        itemIcon[0] = icon.registerIcon(Reference.TEXTURE_PREFIX + "sacrificialIngot");
        itemIcon[1] = icon.registerIcon(Reference.TEXTURE_PREFIX + "bloodCoil");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(final int par) {
        return this.itemIcon[par];
    }

    @Override
    public String getUnlocalizedName(final ItemStack stack) {
        return Reference.MOD_PREFIX + Names.ITEMBM + "." + stack.getItemDamage();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item id, final CreativeTabs tab, final List list) {
        for (int i = 0; i < itemIcon.length; i++) {
            final ItemStack stack = new ItemStack(id, 1, i);
            list.add(stack);
        }
    }
}
