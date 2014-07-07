package vazkii.botania.api.recipe;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipePetals
{
  ItemStack output;
  List<Object> inputs;
  
  public RecipePetals(ItemStack output, Object... inputs)
  {
    this.output = output;
    
    List<Object> inputsToSet = new ArrayList();
    for (Object obj : inputs) {
      if (((obj instanceof String)) || ((obj instanceof ItemStack))) {
        inputsToSet.add(obj);
      } else {
        throw new IllegalArgumentException("Invalid input");
      }
    }
    this.inputs = inputsToSet;
  }
  
  public boolean matches(IInventory inv)
  {
    List<Object> inputsMissing = new ArrayList(this.inputs);
    for (int i = 0; i < inv.getSizeInventory(); i++)
    {
      ItemStack stack = inv.getStackInSlot(i);
      if (stack == null) {
        break;
      }
      String oredict = OreDictionary.getOreName(OreDictionary.getOreID(stack));
      
      int stackIndex = -1;int oredictIndex = -1;
      for (int j = 0; j < inputsMissing.size(); j++)
      {
        Object input = inputsMissing.get(j);
        if (((input instanceof String)) && (input.equals(oredict)))
        {
          oredictIndex = j;
          break;
        }
        if (((input instanceof ItemStack)) && (simpleAreStacksEqual((ItemStack)input, stack)))
        {
          stackIndex = j;
          break;
        }
      }
      if (stackIndex != -1) {
        inputsMissing.remove(stackIndex);
      } else if (oredictIndex != -1) {
        inputsMissing.remove(oredictIndex);
      } else {
        return false;
      }
    }
    return inputsMissing.isEmpty();
  }
  
  boolean simpleAreStacksEqual(ItemStack stack, ItemStack stack2)
  {
    return (stack.itemID == stack2.itemID) && (stack.getItemDamage() == stack2.getItemDamage());
  }
  
  public List<Object> getInputs()
  {
    return new ArrayList(this.inputs);
  }
  
  public ItemStack getOutput()
  {
    return this.output;
  }
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.recipe.RecipePetals
 * JD-Core Version:    0.7.0.1
 */