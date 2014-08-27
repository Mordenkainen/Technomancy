package vazkii.botania.api.mana;

import vazkii.botania.api.internal.IManaBurst;

public abstract interface IManaCollector
  extends IManaReceiver
{
  public abstract void onClientDisplayTick();
  
  public abstract float getManaYieldMultiplier(IManaBurst paramIManaBurst);
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.mana.IManaCollector
 * JD-Core Version:    0.7.0.1
 */