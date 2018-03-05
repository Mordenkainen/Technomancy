// Much of this code was taken from Another One Bites The Dust:
// https://github.com/ganymedes01/Another-One-Bites-the-Dust
package theflogat.technomancy.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.Sys;
import theflogat.technomancy.Technomancy;

public class Ore {

	public static final ArrayList<Ore> ores = new ArrayList<Ore>();
	public static final ArrayList<String> oreNames = new ArrayList<String>();

	public static Ore newOre(String dictName, ItemStack ingot, String name) {
		if(dictName.equals("oreGold")) {
			oreNames.add(dictName);
			return new Ore(dictName, ingot, name.toLowerCase(), 0);
		}
		oreNames.add(dictName);
		return new Ore(dictName, ingot, name.toLowerCase());
	}
	
	public static void init() {

		if(Loader.isModLoaded("thermalexpansion")) {
			newOre("oreGold", FurnaceRecipes.instance().getSmeltingResult(OreDictionary.getOres("oreGold").get(0)), "oreGold".substring("ore".length()));
			newOre("oreIron", FurnaceRecipes.instance().getSmeltingResult(OreDictionary.getOres("oreIron").get(0)), "oreIron".substring("ore".length()));
			newOre("oreCopper", OreDictionary.getOres("ingotCopper").get(0), "oreCopper".substring("ore".length()));
			newOre("oreLead", OreDictionary.getOres("ingotLead").get(0), "oreLead".substring("ore".length()));
			newOre("oreNickel", OreDictionary.getOres("ingotNickel").get(0), "oreNickel".substring("ore".length()));
			newOre("orePlatinum", OreDictionary.getOres("ingotPlatinum").get(0), "orePlatinum".substring("ore".length()));
			newOre("oreSilver", OreDictionary.getOres("ingotSilver").get(0), "oreSilver".substring("ore".length()));
			newOre("oreTin", OreDictionary.getOres("ingotTin").get(0), "oreTin".substring("ore".length()));
			newOre("oreAluminum", OreDictionary.getOres("ingotAluminum").get(0), "oreAluminum".substring("ore".length()));
			newOre("oreIridium", OreDictionary.getOres("ingotIridium").get(0), "oreIridium".substring("ore".length()));
			newOre("oreMithril", OreDictionary.getOres("ingotMithril").get(0), "oreMithril".substring("ore".length()));
		 }else {
			getMetalsWithPrefixes("ore", "ingot");
		}
	}
	
	private static void getMetalsWithPrefixes(String prefix1, String prefix2) {
		for (String name : OreDictionary.getOreNames()) {
			if (name.startsWith(prefix1) && !OreDictionary.getOres(name).isEmpty()) {
				String oreName = name.substring(prefix1.length());
				foundit:
				for (String n : OreDictionary.getOreNames()) {
					if (n.equals(prefix2 + oreName) && !OreDictionary.getOres(n).isEmpty()) {
						for (ItemStack ore : OreDictionary.getOres(name)) {
							ItemStack testIngot = FurnaceRecipes.instance().getSmeltingResult(ore);
							if (testIngot != null) {
								for (ItemStack ingot: OreDictionary.getOres(n)) {
									if (testIngot.isItemEqual(ingot) && testIngot.getItemDamage() == ingot.getItemDamage()) {
										newOre(name, testIngot, oreName);
										break foundit;
									}
								}
							}
						}
					}
				}
			}
		}
		
		if (oreNames.contains("orealuminum") && oreNames.contains("orealuminium")) {
			Iterator<Ore> oreIter = ores.iterator();
			while (oreIter.hasNext()) {
				Ore ore = oreIter.next();
				if (ore.oreName() == "orealuminium") {
					oreIter.remove();
					oreNames.remove("orealuminium");
					break;
				}
			}
		}
	}

	private static String adjustName(String name) {
		if (name.equals("FzDarkIron".toLowerCase()))
			return "Dark Iron";
		else
			return name;
	}
	
	public static boolean isProcessableOre(ItemStack items) {
		int[] ids = OreDictionary.getOreIDs(items);
		for (int id : ids) {
			if(Ore.oreNames.contains(OreDictionary.getOreName(id))) {
				return true;
			}
		}
		return false;
	}
	
	private final String oreName;
	private String name;
	private final ItemStack ingot;
	private Item pureItem;
	private Color color = Color.WHITE;
	private boolean state;
	private int[] ingotsPerStage = {2,3,4,5,6,7};

	protected Ore(String oreName, ItemStack ingot, String name) {
		this.oreName = oreName;
		this.ingot = ingot;
		this.name = adjustName(name);
		ores.add(this);
	}

	protected Ore(String oreName, ItemStack ingot, String name, int pos) {
		this.oreName = oreName;
		this.ingot = ingot;
		this.name = adjustName(name);
		ores.add(pos, this);
	}

	public static String[] getNames() {
		String[] str = new String[oreNames.size()];
		for (int i = 0; i < str.length; i++) {
			str[i] = oreNames.get(i);
		}
		return str;
	}

	public String oreName() {
		return oreName;
	}
	
	public String name() {
		return name;
	}

	public ItemStack ingot() {
		return ingot;
	}

	public int color() {
		return color.getRGB() & 0x00FFFFFF;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setPure(Item pure) {
		pureItem = pure;
	}
	
	public Item getPure() {
		return pureItem;
	}

	public ItemStack getPureStack() {
		return new ItemStack(pureItem);
	}

	public int getIngotsPerStage(int stage) {
		return ingotsPerStage[stage];
	}
	
	public void setIngotsPerStage(List<String> nums) {
		for (int i = 0 ; i < ingotsPerStage.length ; i++ ) {
			ingotsPerStage[i] = Integer.parseInt(nums.get(i).trim());
		}
	}
	
	@Override
	public int hashCode() {
		return oreName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Ore && oreName.equals(((Ore)obj).oreName);
	}

	@Override
	public String toString() {
		return oreName();
	}

	public void setEnabled(boolean state) {
		this.state = state;
		if (!state) {
			oreNames.remove(oreName);
		}
	}
	
	public boolean getEnabled() {
		return state;
	}

	public void setName(String name) {
		this.name = name;
	}
}
