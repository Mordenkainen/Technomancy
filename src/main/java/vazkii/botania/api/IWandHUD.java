package vazkii.botania.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.world.World;

public abstract interface IWandHUD
{
  public abstract void renderHUD(Minecraft paramMinecraft, ScaledResolution paramScaledResolution, World paramWorld, int paramInt1, int paramInt2, int paramInt3);
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.IWandHUD
 * JD-Core Version:    0.7.0.1
 */