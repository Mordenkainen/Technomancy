package thaumcraft.api.wands;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract interface IWandRodOnUpdate
{
  public abstract void onUpdate(ItemStack paramItemStack, EntityPlayer paramEntityPlayer);
}
