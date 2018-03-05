package theflogat.technomancy.common.items.base;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemColorHandler implements IItemColor {

    public int layer0Color;

    public ItemColorHandler(int layer0Color){
        this.layer0Color = layer0Color;
    }

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        if(tintIndex == 0)
            return this.layer0Color;
        return 0;
    }
}