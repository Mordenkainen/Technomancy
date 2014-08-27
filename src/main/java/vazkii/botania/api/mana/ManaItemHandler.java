package vazkii.botania.api.mana;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public final class ManaItemHandler
{
  public static int requestMana(ItemStack stack, EntityPlayer player, int manaToGet, boolean remove)
  {
    if (stack == null) {
      return 0;
    }
    for (ItemStack stackInSlot : player.inventory.mainInventory) {
      if (stackInSlot != stack) {
        if ((stackInSlot != null) && ((stackInSlot.getItem() instanceof IManaItem)))
        {
          IManaItem manaItem = (IManaItem)stackInSlot.getItem();
          if ((manaItem.canExportManaToItem(stackInSlot, stack)) && (manaItem.getMana(stackInSlot) > 0) && (
            (!(stack.getItem() instanceof IManaItem)) || (((IManaItem)stack.getItem()).canReceiveManaFromItem(stack, stackInSlot))))
          {
            int mana = Math.min(manaToGet, manaItem.getMana(stackInSlot));
            if (remove) {
              manaItem.addMana(stackInSlot, -mana);
            }
            return mana;
          }
        }
      }
    }
    return 0;
  }
  
  public static boolean requestManaExact(ItemStack stack, EntityPlayer player, int manaToGet, boolean remove)
  {
    if (stack == null) {
      return false;
    }
    for (ItemStack stackInSlot : player.inventory.mainInventory) {
      if (stackInSlot != stack) {
        if ((stackInSlot != null) && ((stackInSlot.getItem() instanceof IManaItem)))
        {
          IManaItem manaItemSlot = (IManaItem)stackInSlot.getItem();
          if ((manaItemSlot.canExportManaToItem(stackInSlot, stack)) && (manaItemSlot.getMana(stackInSlot) > manaToGet) && (
            (!(stack.getItem() instanceof IManaItem)) || (((IManaItem)stack.getItem()).canReceiveManaFromItem(stack, stackInSlot))))
          {
            if (remove) {
              manaItemSlot.addMana(stackInSlot, -manaToGet);
            }
            return true;
          }
        }
      }
    }
    return false;
  }
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.mana.ManaItemHandler
 * JD-Core Version:    0.7.0.1
 */