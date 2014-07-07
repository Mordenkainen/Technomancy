package vazkii.botania.api.internal;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.api.recipe.RecipeRuneAltar;

public class DummyMethodHandler
  implements IInternalMethodHandler
{
  public LexiconPage textPage(String key)
  {
    return dummyPage(key);
  }
  
  public LexiconPage imagePage(String key, String resource)
  {
    return dummyPage(key);
  }
  
  public LexiconPage craftingRecipesPage(String key, List<IRecipe> recipes)
  {
    return dummyPage(key);
  }
  
  public LexiconPage craftingRecipePage(String key, IRecipe recipe)
  {
    return dummyPage(key);
  }
  
  public LexiconPage petalRecipesPage(String key, List<RecipePetals> recipes)
  {
    return dummyPage(key);
  }
  
  public LexiconPage petalRecipePage(String key, RecipePetals recipe)
  {
    return dummyPage(key);
  }
  
  public LexiconPage runeRecipesPage(String key, List<RecipeRuneAltar> recipes)
  {
    return dummyPage(key);
  }
  
  public LexiconPage runeRecipePage(String key, RecipeRuneAltar recipe)
  {
    return dummyPage(key);
  }
  
  public LexiconPage manaInfusionRecipesPage(String key, List<RecipeManaInfusion> recipes)
  {
    return dummyPage(key);
  }
  
  public LexiconPage manaInfusionRecipePage(String key, RecipeManaInfusion recipe)
  {
    return dummyPage(key);
  }
  
  private LexiconPage dummyPage(String key)
  {
    return new DummyPage(key);
  }
  
  public ItemStack getSubTileAsStack(String subTile)
  {
    return new ItemStack(1, 0, 0);
  }
  
  public Icon getSubTileIconForName(String name)
  {
    return Block.plantRed.getIcon(0, 0);
  }
  
  public IManaNetwork getManaNetworkInstance()
  {
    return DummyManaNetwork.instance;
  }
  
  public void drawSimpleManaHUD(int color, int mana, int maxMana, String name, ScaledResolution res) {}
  
  public void sparkleFX(World world, double x, double y, double z, float r, float g, float b, float size, int m) {}
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.internal.DummyMethodHandler
 * JD-Core Version:    0.7.0.1
 */