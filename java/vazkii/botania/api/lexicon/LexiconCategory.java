package vazkii.botania.api.lexicon;

import java.util.ArrayList;
import java.util.List;

public final class LexiconCategory
{
  public final String unlocalizedName;
  public final List<LexiconEntry> entries = new ArrayList();
  
  public LexiconCategory(String unlocalizedName)
  {
    this.unlocalizedName = unlocalizedName;
  }
  
  public String getUnlocalizedName()
  {
    return this.unlocalizedName;
  }
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.lexicon.LexiconCategory
 * JD-Core Version:    0.7.0.1
 */