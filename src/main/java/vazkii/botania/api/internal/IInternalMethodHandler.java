package vazkii.botania.api.internal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.api.recipe.RecipeRuneAltar;

public abstract interface IInternalMethodHandler
{
  public abstract LexiconPage textPage(String paramString);
  
  public abstract LexiconPage imagePage(String paramString1, String paramString2);
  
  public abstract LexiconPage craftingRecipesPage(String paramString, List<IRecipe> paramList);
  
  public abstract LexiconPage craftingRecipePage(String paramString, IRecipe paramIRecipe);
  
  public abstract LexiconPage petalRecipesPage(String paramString, List<RecipePetals> paramList);
  
  public abstract LexiconPage petalRecipePage(String paramString, RecipePetals paramRecipePetals);
  
  public abstract LexiconPage runeRecipesPage(String paramString, List<RecipeRuneAltar> paramList);
  
  public abstract LexiconPage runeRecipePage(String paramString, RecipeRuneAltar paramRecipeRuneAltar);
  
  public abstract LexiconPage manaInfusionRecipesPage(String paramString, List<RecipeManaInfusion> paramList);
  
  public abstract LexiconPage manaInfusionRecipePage(String paramString, RecipeManaInfusion paramRecipeManaInfusion);
  
  public abstract IManaNetwork getManaNetworkInstance();
  
  public abstract ItemStack getSubTileAsStack(String paramString);
  
  public abstract Icon getSubTileIconForName(String paramString);
  
  @SideOnly(Side.CLIENT)
  public abstract void drawSimpleManaHUD(int paramInt1, int paramInt2, int paramInt3, String paramString, ScaledResolution paramScaledResolution);
  
  public abstract void sparkleFX(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt);
}


/* Location:           C:\Brett\Development\Deobfuscation\Sources\Botania.zip
 * Qualified Name:     vazkii.botania.api.internal.IInternalMethodHandler
 * JD-Core Version:    0.7.0.1
 */