package vazkii.botania.api.internal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.api.lexicon.LexiconPage;

public class DummyPage
  extends LexiconPage
{
  public DummyPage(String unlocalizedName)
  {
    super(unlocalizedName);
  }
  
  @SideOnly(Side.CLIENT)
  public void renderScreen(IGuiLexiconEntry gui, int x, int y) {}
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.internal.DummyPage
 * JD-Core Version:    0.7.0.1
 */