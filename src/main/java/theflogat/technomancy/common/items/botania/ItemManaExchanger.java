package theflogat.technomancy.common.items.botania;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import theflogat.technomancy.common.items.base.ItemAdvancedBase;

public class ItemManaExchanger extends ItemAdvancedBase {

    public ItemManaExchanger(Block block) {
        super(block);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack items, EntityPlayer player, List l, boolean moreInfo) {
        super.addInformation(items, player, l, moreInfo);
        l.add("Right-Click With A Wrench To Switch");
        l.add("Between Importing And Exporting Mana");
    }
}
