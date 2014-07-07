package vazkii.botania.api;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import vazkii.botania.api.internal.DummyMethodHandler;
import vazkii.botania.api.internal.DummySubTile;
import vazkii.botania.api.internal.IInternalMethodHandler;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.api.subtile.SubTileEntity;

public final class BotaniaAPI
{
  private static List<LexiconCategory> categories = new ArrayList();
  private static List<LexiconEntry> allEntries = new ArrayList();
  public static List<RecipePetals> petalRecipes = new ArrayList();
  public static List<RecipeRuneAltar> runeAltarRecipes = new ArrayList();
  public static List<RecipeManaInfusion> manaInfusionRecipes = new ArrayList();
  private static BiMap<String, Class<? extends SubTileEntity>> subTiles = HashBiMap.create();
  public static Map<String, Integer> oreWeights = new HashMap();
  
  static
  {
    registerSubTile("", DummySubTile.class);
    
    addOreWeight("oreAluminum", 3940);
    addOreWeight("oreAmber", 2075);
    addOreWeight("oreApatite", 1595);
    addOreWeight("oreBlueTopaz", 3195);
    addOreWeight("oreCassiterite", 1634);
    addOreWeight("oreCertusQuartz", 3975);
    addOreWeight("oreChimerite", 3970);
    addOreWeight("oreCinnabar", 2585);
    addOreWeight("oreCoal", 46525);
    addOreWeight("oreCooperite", 5);
    addOreWeight("oreCopper", 8325);
    addOreWeight("oreDarkIron", 1700);
    addOreWeight("oreDiamond", 1265);
    addOreWeight("oreEmerald", 780);
    addOreWeight("oreEmery", 415);
    addOreWeight("oreGalena", 1000);
    addOreWeight("oreGold", 2970);
    addOreWeight("oreInfusedAir", 925);
    addOreWeight("oreInfusedEarth", 925);
    addOreWeight("oreInfusedEntropy", 925);
    addOreWeight("oreInfusedFire", 925);
    addOreWeight("oreInfusedOrder", 925);
    addOreWeight("oreInfusedWater", 925);
    addOreWeight("oreIridium", 30);
    addOreWeight("oreIron", 20665);
    addOreWeight("oreLapis", 1285);
    addOreWeight("oreLead", 7985);
    addOreWeight("oreMCropsEssence", 3085);
    addOreWeight("oreNickel", 2275);
    addOreWeight("oreOlivine", 1100);
    addOreWeight("oreRedstone", 6885);
    addOreWeight("oreRuby", 1100);
    addOreWeight("oreSapphire", 1100);
    addOreWeight("oreSilver", 6300);
    addOreWeight("oreSphalerite", 25);
    addOreWeight("oreSulfur", 1105);
    addOreWeight("oreTetrahedrite", 4040);
    addOreWeight("oreTin", 9450);
    addOreWeight("oreTungstate", 20);
    addOreWeight("oreUranium", 1337);
    addOreWeight("oreVinteum", 5925);
    addOreWeight("oreYellorite", 3520);
  }
  
  public static IInternalMethodHandler internalHandler = new DummyMethodHandler();
  
  public static RecipePetals registerPetalRecipe(ItemStack output, Object... inputs)
  {
    RecipePetals recipe = new RecipePetals(output, inputs);
    petalRecipes.add(recipe);
    return recipe;
  }
  
  public static RecipeRuneAltar registerRuneAltarRecipe(ItemStack output, int mana, Object... inputs)
  {
    RecipeRuneAltar recipe = new RecipeRuneAltar(output, mana, inputs);
    runeAltarRecipes.add(recipe);
    return recipe;
  }
  
  public static RecipeManaInfusion registerManaInfusionRecipe(ItemStack output, Object input, int mana)
  {
    RecipeManaInfusion recipe = new RecipeManaInfusion(output, input, mana);
    manaInfusionRecipes.add(recipe);
    return recipe;
  }
  
  public static void registerSubTile(String key, Class<? extends SubTileEntity> subtileClass)
  {
    subTiles.put(key, subtileClass);
  }
  
  public static void addCategory(LexiconCategory category)
  {
    categories.add(category);
  }
  
  public static List<LexiconCategory> getAllCategories()
  {
    return categories;
  }
  
  public static void addEntry(LexiconEntry entry, LexiconCategory category)
  {
    allEntries.add(entry);
    category.entries.add(entry);
  }
  
  public static void addOreWeight(String ore, int weight)
  {
    oreWeights.put(ore, Integer.valueOf(weight));
  }
  
  public static int getOreWeight(String ore)
  {
    return ((Integer)oreWeights.get(ore)).intValue();
  }
  
  public static IRecipe getLatestAddedRecipe()
  {
    List<IRecipe> list = CraftingManager.getInstance().getRecipeList();
    return (IRecipe)list.get(list.size() - 1);
  }
  
  public static List<IRecipe> getLatestAddedRecipes(int x)
  {
    List<IRecipe> list = CraftingManager.getInstance().getRecipeList();
    List<IRecipe> newList = new ArrayList();
    for (int i = x - 1; i >= 0; i--) {
      newList.add(list.get(list.size() - 1 - i));
    }
    return newList;
  }
  
  public static Class<? extends SubTileEntity> getSubTileMapping(String key)
  {
    if (!subTiles.containsKey(key)) {
      key = "";
    }
    return (Class)subTiles.get(key);
  }
  
  public static String getSubTileStringMapping(Class<? extends SubTileEntity> clazz)
  {
    return (String)subTiles.inverse().get(clazz);
  }
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.BotaniaAPI
 * JD-Core Version:    0.7.0.1
 */