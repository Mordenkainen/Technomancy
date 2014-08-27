package vazkii.botania.api.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public abstract interface IFlowerComponent
{
  public abstract boolean canFit(ItemStack paramItemStack, IInventory paramIInventory);
  
  public abstract int getParticleColor(ItemStack paramItemStack);
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.recipe.IFlowerComponent
 * JD-Core Version:    0.7.0.1
 */