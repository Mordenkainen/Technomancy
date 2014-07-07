package vazkii.botania.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ChunkCoordinates;

public abstract interface ITileBound
{
  @SideOnly(Side.CLIENT)
  public abstract ChunkCoordinates getBinding();
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.ITileBound
 * JD-Core Version:    0.7.0.1
 */