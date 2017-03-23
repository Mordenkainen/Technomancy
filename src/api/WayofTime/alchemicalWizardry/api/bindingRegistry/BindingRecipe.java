package WayofTime.alchemicalWizardry.api.bindingRegistry;

import net.minecraft.item.ItemStack;

public class BindingRecipe
{
    public ItemStack requiredItem;
    public ItemStack outputItem;

    public BindingRecipe(ItemStack outputItem, ItemStack requiredItem)
    {
        this.requiredItem = requiredItem;
        this.outputItem = outputItem;
    }

    public boolean doesRequiredItemMatch(ItemStack testStack)
    {
        return !(testStack == null || this.requiredItem == null) && this.requiredItem.isItemEqual(testStack);
    }

    public ItemStack getResult(ItemStack inputItem)
    {
        return this.getResult();
    }

    public ItemStack getResult()
    {
        return this.outputItem;
    }
}
