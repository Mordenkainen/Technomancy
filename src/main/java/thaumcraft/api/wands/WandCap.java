package thaumcraft.api.wands;

import java.util.LinkedHashMap;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;

public class WandCap{
  private String tag;
  private int craftCost;
  float baseCostModifier;
  List<Aspect> specialCostModifierAspects;
  float specialCostModifier;
  ResourceLocation texture;
  ItemStack item;
  public static LinkedHashMap<String, WandCap> caps = new LinkedHashMap();
  
  public WandCap(String tag, float discount, ItemStack item, int craftCost) {
    setTag(tag);
    baseCostModifier = discount;
    specialCostModifierAspects = null;
    texture = new ResourceLocation("thaumcraft", "textures/models/wand_cap_" + getTag() + ".png");
    this.item = item;
    setCraftCost(craftCost);
    caps.put(tag, this);
  }
  
  public WandCap(String tag, float discount, List<Aspect> specialAspects, float discountSpecial, ItemStack item, int craftCost) {
    setTag(tag);
    baseCostModifier = discount;
    specialCostModifierAspects = specialAspects;
    specialCostModifier = discountSpecial;
    texture = new ResourceLocation("thaumcraft", "textures/models/wand_cap_" + getTag() + ".png");
    this.item = item;
    setCraftCost(craftCost);
    caps.put(tag, this);
  }
  
  public float getBaseCostModifier() {
    return baseCostModifier;
  }
  
  public List<Aspect> getSpecialCostModifierAspects() {
    return specialCostModifierAspects;
  }
  
  public float getSpecialCostModifier() {
    return specialCostModifier;
  }
  
  public ResourceLocation getTexture() {
    return texture;
  }
  
  public void setTexture(ResourceLocation texture) {
    this.texture = texture;
  }
  
  public String getTag() {
    return tag;
  }
  
  public void setTag(String tag) {
    this.tag = tag;
  }
  
  public ItemStack getItem()
  {
    return item;
  }
  
  public void setItem(ItemStack item) {
    this.item = item;
  }
  
  public int getCraftCost() {
    return craftCost;
  }
  
  public void setCraftCost(int craftCost) {
    this.craftCost = craftCost;
  }
}
