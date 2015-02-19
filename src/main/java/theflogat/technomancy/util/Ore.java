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

import cpw.mods.fml.common.Loader;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class Ore {

	public static final ArrayList<Ore> ores = new ArrayList<Ore>();
	public static final ArrayList<String> oreNames = new ArrayList<String>();

	public static Ore newOre(String dictName, ItemStack ingot, String name) {
		oreNames.add(dictName);
		return new Ore(dictName, ingot, name);
	}
	
	public static void init() {
		getMetalsWithPrefixes("ore", "ingot");
	}
	
	private static void getMetalsWithPrefixes(String prefix1, String prefix2) {
		for (String name : OreDictionary.getOreNames()) {
			if (name.startsWith(prefix1) && !OreDictionary.getOres(name).isEmpty()) {
				String oreName = name.substring(prefix1.length());
				foundit:
				for (String n : OreDictionary.getOreNames()) {
					if (n.equals(prefix2 + oreName) && !OreDictionary.getOres(n).isEmpty()) {
						for (ItemStack ore : OreDictionary.getOres(name)) {
							ItemStack testIngot = FurnaceRecipes.smelting().getSmeltingResult(ore);
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
		
		if (oreNames.contains("oreAluminum") && oreNames.contains("oreAluminium")) {
			Iterator<Ore> oreIter = ores.iterator();
			while (oreIter.hasNext()) {
				Ore ore = oreIter.next();
				if (ore.oreName() == "oreAluminium") {
					oreIter.remove();
					oreNames.remove("oreAluminium");
					break;
				}
			}
		}
	}

	private static String adjustName(String name) {
		if (name.equals("FzDarkIron"))
			return "Dark Iron";
		else
			return name;
	}
	
	public static void initColors() {
		for (Ore ore : ores)
			ore.setColor(getColor(ore.oreName().substring(3)));
	}
	
	private static int getStackColor(ItemStack stack, int pass) {
		if (Loader.isModLoaded("gregtech"))
			try {
				Class<?> cls = Class.forName("gregtech.api.items.GT_MetaGenerated_Item");
				Class<?> itemCls = stack.getItem().getClass();
				if (cls.isAssignableFrom(itemCls)) {
					Method m = itemCls.getMethod("getRGBa", ItemStack.class);
					short[] rgba = (short[]) m.invoke(stack.getItem(), stack);
					return new Color(rgba[0], rgba[1], rgba[2], rgba[3]).getRGB();
				}
			} catch (Exception e) {
			}
		return stack.getItem().getColorFromItemStack(stack, pass);
	}

	private static Color getColor(String oreName) {
		List<ItemStack> ores = OreDictionary.getOres("ingot" + oreName);
		if (ores.isEmpty())
			return null;

		Set<Color> colors = new LinkedHashSet<Color>();
		for (ItemStack stack : ores)
			try {
				BufferedImage texture = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(getIconResource(stack)).getInputStream());
				Color texColour = getAverageColor(texture);
				colors.add(texColour);
				for (int pass = 0; pass < stack.getItem().getRenderPasses(stack.getItemDamage()); pass++) {
					int c = getStackColor(stack, pass);
					if (c != 0xFFFFFF) {
						colors.add(new Color(c));
						colors.remove(texColour);
					}
				}
			} catch (Exception e) {
				continue;
			}

		float red = 0;
		float green = 0;
		float blue = 0;
		for (Color c : colors) {
			red += c.getRed();
			green += c.getGreen();
			blue += c.getBlue();
		}
		float count = colors.size();
		return new Color((int) (red / count), (int) (green / count), (int) (blue / count));
	}

	private static Color getAverageColor(BufferedImage image) {
		float red = 0;
		float green = 0;
		float blue = 0;
		float count = 0;
		for (int i = 0; i < image.getWidth(); i++)
			for (int j = 0; j < image.getHeight(); j++) {
				Color c = new Color(image.getRGB(i, j));
				if (c.getAlpha() != 255 || c.getRed() <= 10 && c.getBlue() <= 10 && c.getGreen() <= 10)
					continue;
				red += c.getRed();
				green += c.getGreen();
				blue += c.getBlue();
				count++;
			}

		return new Color((int) (red / count), (int) (green / count), (int) (blue / count));
	}

	private static String getIconName(ItemStack stack) {
		IIcon icon = stack.getItem().getIconFromDamage(stack.getItemDamage());
		if (icon != null)
			return icon.getIconName();
		return null;
	}

	private static ResourceLocation getIconResource(ItemStack stack) {
		String iconName = getIconName(stack);
		if (iconName == null)
			return null;

		String string = "minecraft";

		int colonIndex = iconName.indexOf(58);
		if (colonIndex >= 0) {
			if (colonIndex > 1)
				string = iconName.substring(0, colonIndex);

			iconName = iconName.substring(colonIndex + 1, iconName.length());
		}

		string = string.toLowerCase();
		iconName = "textures/items/" + iconName + ".png";
		return new ResourceLocation(string, iconName);
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
	private int ingotsPerStage;

	protected Ore(String oreName, ItemStack ingot, String name) {
		this.oreName = oreName;
		this.ingot = ingot;
		this.name = adjustName(name);
		ores.add(this);
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

	public int getIngotsPerStage() {
		return ingotsPerStage;
	}
	
	public void setIngotsPerStage(int num) {
		ingotsPerStage = num;
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
