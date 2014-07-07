package vazkii.botania.api.internal;

import vazkii.botania.api.lexicon.LexiconEntry;

public abstract interface IGuiLexiconEntry
{
  public abstract LexiconEntry getEntry();
  
  public abstract int getPageOn();
  
  public abstract int getLeft();
  
  public abstract int getTop();
  
  public abstract int getWidth();
  
  public abstract int getHeight();
  
  public abstract float getZLevel();
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.internal.IGuiLexiconEntry
 * JD-Core Version:    0.7.0.1
 */