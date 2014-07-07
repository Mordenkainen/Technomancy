package thaumcraft.api.wands;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract interface IWandable
{
  public abstract int onWandRightClick(World paramWorld, ItemStack paramItemStack, EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  public abstract ItemStack onWandRightClick(World paramWorld, ItemStack paramItemStack, EntityPlayer paramEntityPlayer);
  
  public abstract void onUsingWandTick(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, int paramInt);
  
  public abstract void onWandStoppedUsing(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer, int paramInt);
}
