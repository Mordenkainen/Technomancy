package theflogat.technomancy.common.items.technom;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCrystal extends ItemBlock {

    private static String[] types = new String[] { "nature", "fire", "water", "light", "dark" };

    public ItemCrystal(Block crystal) {
        super(crystal);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack items, EntityPlayer player, List l, boolean moreInfo) {
        l.add("Used for " + types[items.getItemDamage()] + " rituals. Safe for decoration.");
    }
}
