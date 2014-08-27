package vazkii.botania.api.mana;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import vazkii.botania.api.internal.IManaBurst;

public abstract interface ILens
{
  @SideOnly(Side.CLIENT)
  public abstract int getLensColor(ItemStack paramItemStack);
  
  public abstract void apply(ItemStack paramItemStack, BurstProperties paramBurstProperties);
  
  public abstract boolean collideBurst(IManaBurst paramIManaBurst, MovingObjectPosition paramMovingObjectPosition, boolean paramBoolean1, boolean paramBoolean2, ItemStack paramItemStack);
  
  public abstract void updateBurst(IManaBurst paramIManaBurst, ItemStack paramItemStack);
  
  public abstract boolean doParticles(IManaBurst paramIManaBurst, ItemStack paramItemStack);
  
  public abstract boolean canCombineLenses(ItemStack paramItemStack1, ItemStack paramItemStack2);
  
  public abstract ItemStack getCompositeLens(ItemStack paramItemStack);
  
  public abstract ItemStack setCompositeLens(ItemStack paramItemStack1, ItemStack paramItemStack2);
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.mana.ILens
 * JD-Core Version:    0.7.0.1
 */