package vazkii.botania.api.mana;

public abstract interface IManaReceiver
  extends IManaBlock
{
  public abstract boolean isFull();
  
  public abstract void recieveMana(int paramInt);
  
  public abstract boolean canRecieveManaFromBursts();
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.mana.IManaReceiver
 * JD-Core Version:    0.7.0.1
 */