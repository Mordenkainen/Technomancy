package theflogat.technomancy.common.items.technom;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCrystal extends ItemBlock {

    private static String[] types = new String[] { "nature", "fire", "water", "light", "dark" };

    public ItemCrystal(final Block crystal) {
        super(crystal);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(final ItemStack items, final EntityPlayer player, final List l, final boolean moreInfo) {
        l.add("Used for " + types[items.getItemDamage()] + " rituals. Safe for decoration.");
    }
}
