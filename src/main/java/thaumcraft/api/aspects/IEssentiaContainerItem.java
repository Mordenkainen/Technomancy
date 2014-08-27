package thaumcraft.api.aspects;

import net.minecraft.item.ItemStack;

public abstract interface IEssentiaContainerItem
{
  public abstract AspectList getAspects(ItemStack paramItemStack);
  
  public abstract void setAspects(ItemStack paramItemStack, AspectList paramAspectList);
}
