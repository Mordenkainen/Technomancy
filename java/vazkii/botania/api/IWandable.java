package vazkii.botania.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract interface IWandable
{
  public abstract boolean onUsedByWand(EntityPlayer paramEntityPlayer, ItemStack paramItemStack, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.IWandable
 * JD-Core Version:    0.7.0.1
 */