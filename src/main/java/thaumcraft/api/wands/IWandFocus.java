package thaumcraft.api.wands;

import net.minecraft.item.ItemStack;

public abstract interface IWandFocus
{
  public abstract int getFocusColor();
  
  public abstract net.minecraft.util.Icon getFocusDepthLayerIcon();
  
  public abstract net.minecraft.util.Icon getOrnament();
  
  public static enum WandFocusAnimation {
    WAVE,  CHARGE;
    
    private WandFocusAnimation() {}
  }
  
  public abstract WandFocusAnimation getAnimation();
  
  public abstract thaumcraft.api.aspects.AspectList getVisCost();
  
  public abstract boolean isVisCostPerTick();
  
  public abstract ItemStack onFocusRightClick(ItemStack paramItemStack, net.minecraft.world.World paramWorld, net.minecraft.entity.player.EntityPlayer paramEntityPlayer, net.minecraft.util.MovingObjectPosition paramMovingObjectPosition);
  
  public abstract void onUsingFocusTick(ItemStack paramItemStack, net.minecraft.entity.player.EntityPlayer paramEntityPlayer, int paramInt);
  
  public abstract void onPlayerStoppedUsingFocus(ItemStack paramItemStack, net.minecraft.world.World paramWorld, net.minecraft.entity.player.EntityPlayer paramEntityPlayer, int paramInt);
  
  public abstract String getSortingHelper(ItemStack paramItemStack);
  
  public abstract boolean onFocusBlockStartBreak(ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3, net.minecraft.entity.player.EntityPlayer paramEntityPlayer);
  
  public abstract boolean acceptsEnchant(int paramInt);
}
