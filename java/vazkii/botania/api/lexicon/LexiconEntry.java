package vazkii.botania.api.lexicon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.util.StatCollector;

public class LexiconEntry
  implements Comparable<LexiconEntry>
{
  public final String unlocalizedName;
  public final LexiconCategory category;
  public List<LexiconPage> pages = new ArrayList();
  private boolean priority = false;
  
  public LexiconEntry(String unlocalizedName, LexiconCategory category)
  {
    this.unlocalizedName = unlocalizedName;
    this.category = category;
  }
  
  public LexiconEntry setPriority()
  {
    this.priority = true;
    return this;
  }
  
  public boolean isPriority()
  {
    return this.priority;
  }
  
  public String getUnlocalizedName()
  {
    return this.unlocalizedName;
  }
  
  public LexiconEntry setLexiconPages(LexiconPage... pages)
  {
    this.pages.addAll(Arrays.asList(pages));
    for (int i = 0; i < this.pages.size(); i++) {
      ((LexiconPage)this.pages.get(i)).onPageAdded(this, i);
    }
    return this;
  }
  
  public void addPage(LexiconPage page)
  {
    this.pages.add(page);
  }
  
  public final String getNameForSorting()
  {
    return (this.priority ? 0 : 1) + StatCollector.translateToLocal(getUnlocalizedName());
  }
  
  public int compareTo(LexiconEntry o)
  {
    return getNameForSorting().compareTo(o.getNameForSorting());
  }
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.lexicon.LexiconEntry
 * JD-Core Version:    0.7.0.1
 */