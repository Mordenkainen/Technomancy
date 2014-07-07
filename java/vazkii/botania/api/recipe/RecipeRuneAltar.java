package vazkii.botania.api.recipe;

import net.minecraft.item.ItemStack;

public class RecipeRuneAltar
  extends RecipePetals
{
  ItemStack output;
  int mana;
  
  public RecipeRuneAltar(ItemStack output, int mana, Object... inputs)
  {
    super(output, inputs);
    this.output = output;
    this.mana = mana;
  }
  
  public int getManaUsage()
  {
    return this.mana;
  }
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.recipe.RecipeRuneAltar
 * JD-Core Version:    0.7.0.1
 */