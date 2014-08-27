package vazkii.botania.api.mana;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public abstract interface IManaItem
{
  public abstract int getMana(ItemStack paramItemStack);
  
  public abstract int getMaxMana(ItemStack paramItemStack);
  
  public abstract void addMana(ItemStack paramItemStack, int paramInt);
  
  public abstract boolean canReceiveManaFromPool(ItemStack paramItemStack, TileEntity paramTileEntity);
  
  public abstract boolean canReceiveManaFromItem(ItemStack paramItemStack1, ItemStack paramItemStack2);
  
  public abstract boolean canExportManaToPool(ItemStack paramItemStack, TileEntity paramTileEntity);
  
  public abstract boolean canExportManaToItem(ItemStack paramItemStack1, ItemStack paramItemStack2);
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.mana.IManaItem
 * JD-Core Version:    0.7.0.1
 */