package theflogat.technomancy.common.items.technom;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCatalyst extends ItemBlock {

    public ItemCatalyst(final Block catalyst) {
        super(catalyst);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(final ItemStack items, final EntityPlayer player, final List l, final boolean moreInfo) {
        switch (items.getItemDamage()) {
            case 0:
                l.add("Dark ritual core");
                break;
            case 1:
                l.add("Light ritual core");
                break;
            case 2:
                l.add("Fire ritual core");
                break;
            case 3:
                l.add("Nature ritual core");
                break;
            case 4:
                l.add("Water ritual core");
                break;
            default:
                break;
        }
        l.add("Possible sizes: 3x3, 5x5, 9x9");
    }
}
