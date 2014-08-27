package vazkii.botania.api.internal;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;

public abstract interface IManaBurst
{
  public abstract boolean isFake();
  
  public abstract void setMotion(double paramDouble1, double paramDouble2, double paramDouble3);
  
  public abstract int getColor();
  
  public abstract void setColor(int paramInt);
  
  public abstract int getMana();
  
  public abstract void setMana(int paramInt);
  
  public abstract int getStartingMana();
  
  public abstract void setStartingMana(int paramInt);
  
  public abstract int getMinManaLoss();
  
  public abstract void setMinManaLoss(int paramInt);
  
  public abstract float getManaLossPerTick();
  
  public abstract void setManaLossPerTick(float paramFloat);
  
  public abstract float getGravity();
  
  public abstract void setGravity(float paramFloat);
  
  public abstract ChunkCoordinates getBurstSourceChunkCoordinates();
  
  public abstract void setBurstSourceCoords(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract ItemStack getSourceLens();
  
  public abstract void setSourceLens(ItemStack paramItemStack);
  
  public abstract boolean hasAlreadyCollidedAt(int paramInt1, int paramInt2, int paramInt3);
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.internal.IManaBurst
 * JD-Core Version:    0.7.0.1
 */