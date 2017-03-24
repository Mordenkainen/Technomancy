package theflogat.technomancy.common.items.technom.existence;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;

public class ItemBlockExistenceUser extends ItemBlock {

    public ItemBlockExistenceUser(final Block b) {
        super(b);
    }

    @Override
    public String getUnlocalizedName(final ItemStack items) {
        return Reference.getId(Names.EXISTENCEUSER[items.getItemDamage() % Names.EXISTENCEUSER.length]);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(final Item item, final CreativeTabs tab, final List l) {
        for (int i = 0; i < Names.EXISTENCEUSER.length; i++) {
            l.add(new ItemStack(item, 1, i));
        }
    }
}
