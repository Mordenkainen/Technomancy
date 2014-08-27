package vazkii.botania.api.lexicon;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.item.ItemStack;

public final class LexiconRecipeMappings
{
  private static Map<String, EntryData> mappings = new HashMap();
  
  public static void map(ItemStack stack, LexiconEntry entry, int page, boolean force)
  {
    EntryData data = new EntryData(entry, page);
    String str = stackToString(stack);
    if ((force) || (!mappings.containsKey(str))) {
      mappings.put(str, data);
    }
  }
  
  public static void map(ItemStack stack, LexiconEntry entry, int page)
  {
    map(stack, entry, page, false);
  }
  
  public static EntryData getDataForStack(ItemStack stack)
  {
    return (EntryData)mappings.get(stackToString(stack));
  }
  
  public static String stackToString(ItemStack stack)
  {
    if ((stack.hasTagCompound()) && ((stack.getItem() instanceof IRecipeKeyProvider))) {
      return ((IRecipeKeyProvider)stack.getItem()).getKey(stack);
    }
    return stack.itemID + ":" + stack.getItemDamage();
  }
  
  public static class EntryData
  {
    public final LexiconEntry entry;
    public final int page;
    
    public EntryData(LexiconEntry entry, int page)
    {
      this.entry = entry;
      this.page = page;
    }
  }
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.lexicon.LexiconRecipeMappings
 * JD-Core Version:    0.7.0.1
 */