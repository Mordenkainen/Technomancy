package theflogat.technomancy.common.items.technom.existence;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;

public class ItemBlockExistenceBurner extends ItemBlock {

    public ItemBlockExistenceBurner(final Block b) {
        super(b);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(final Item item, final CreativeTabs tab, final List l) {
        l.add(new ItemStack(item, 1, 0));
        l.add(new ItemStack(item, 1, 1));
    }

    @Override
    public String getUnlocalizedName(final ItemStack items) {
        return Reference.getId(Names.EXISTENCEBURNER[items.getItemDamage() % 2]);
    }
}