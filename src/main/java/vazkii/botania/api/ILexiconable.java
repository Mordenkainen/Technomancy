package vazkii.botania.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.LexiconEntry;

public abstract interface ILexiconable
{
  public abstract LexiconEntry getEntry(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer, ItemStack paramItemStack);
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.ILexiconable
 * JD-Core Version:    0.7.0.1
 */