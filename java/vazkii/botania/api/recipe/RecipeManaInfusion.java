package vazkii.botania.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeManaInfusion
{
  ItemStack output;
  Object input;
  int mana;
  
  public RecipeManaInfusion(ItemStack output, Object input, int mana)
  {
    this.output = output;
    this.input = input;
    this.mana = mana;
  }
  
  public boolean matches(ItemStack stack)
  {
    if ((this.input instanceof ItemStack)) {
      return stack.isItemEqual((ItemStack)this.input);
    }
    String oredict = OreDictionary.getOreName(OreDictionary.getOreID(stack));
    return oredict.equals(this.input);
  }
  
  public Object getInput()
  {
    return this.input;
  }
  
  public ItemStack getOutput()
  {
    return this.output;
  }
  
  public int getManaToConsume()
  {
    return this.mana;
  }
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.recipe.RecipeManaInfusion
 * JD-Core Version:    0.7.0.1
 */