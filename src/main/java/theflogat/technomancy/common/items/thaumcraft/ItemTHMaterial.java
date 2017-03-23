package theflogat.technomancy.common.items.thaumcraft;

import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import theflogat.technomancy.common.items.base.ItemBase;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTHMaterial extends ItemBase {

    public ItemTHMaterial() {
        setMaxStackSize(64);
        setHasSubtypes(true);
    }

    public IIcon[] itemIcon = new IIcon[5];

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister icon) {
        itemIcon[0] = icon.registerIcon(Ref.TEXTURE_PREFIX + "neutronizedMetal");
        itemIcon[1] = icon.registerIcon(Ref.TEXTURE_PREFIX + "enchantedCoil");
        itemIcon[2] = icon.registerIcon(Ref.TEXTURE_PREFIX + "neutronizedGear");
        itemIcon[3] = icon.registerIcon(Ref.TEXTURE_PREFIX + "penCore");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int dmg) {
        return this.itemIcon[dmg];
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return Ref.MOD_PREFIX + Names.itemMaterial + "." + stack.getItemDamage();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(Item id, CreativeTabs tab, List list) {
        for (int i = 0; i < itemIcon.length; i++) {
            ItemStack stack = new ItemStack(id, 1, i);
            list.add(stack);
        }
    }

    @Override
    public void onUpdate(ItemStack items, World w, Entity ent, int slot, boolean held) {
        if (items.getItemDamage() == 4) {
            ((EntityPlayer) ent).inventory.mainInventory[slot] = new ItemStack(TMItems.coilCoupler);
        }
    }
}
