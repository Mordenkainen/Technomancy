package vazkii.botania.api.lexicon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.api.internal.IGuiLexiconEntry;

public abstract class LexiconPage
{
  public String unlocalizedName;
  
  public LexiconPage(String unlocalizedName)
  {
    this.unlocalizedName = unlocalizedName;
  }
  
  @SideOnly(Side.CLIENT)
  public abstract void renderScreen(IGuiLexiconEntry paramIGuiLexiconEntry, int paramInt1, int paramInt2);
  
  @SideOnly(Side.CLIENT)
  public void updateScreen() {}
  
  public void onPageAdded(LexiconEntry entry, int index) {}
  
  public String getUnlocalizedName()
  {
    return this.unlocalizedName;
  }
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.lexicon.LexiconPage
 * JD-Core Version:    0.7.0.1
 */