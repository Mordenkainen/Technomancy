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

import cpw.mods.fml.common.Loader;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class Ore {

    public static final List<Ore> ORES = new ArrayList<Ore>();
    public static final List<String> ORENAMES = new ArrayList<String>();
    
    private final String oreName;
    private String name;
    private final ItemStack ingot;
    private Item pureItem;
    private Color color = Color.WHITE;
    private boolean state;
    private int[] ingotsPerStage = { 2, 3, 4, 5, 6, 7 };

    public static Ore newOre(final String dictName, final ItemStack ingot, final String name) {
        ORENAMES.add(dictName);
        return new Ore(dictName, ingot, name);
    }

    public static void init() {
        getMetalsWithPrefixes("ore", "ingot");
    }

    private static void getMetalsWithPrefixes(final String prefix1, final String prefix2) {
        for (final String name : OreDictionary.getOreNames()) {
            if (name.startsWith(prefix1) && !OreDictionary.getOres(name).isEmpty()) {
                final String oreName = name.substring(prefix1.length());
                foundit: for (final String n : OreDictionary.getOreNames()) {
                    if (n.equals(prefix2 + oreName) && !OreDictionary.getOres(n).isEmpty()) {
                        for (final ItemStack ore : OreDictionary.getOres(name)) {
                            final ItemStack testIngot = FurnaceRecipes.smelting().getSmeltingResult(ore);
                            if (testIngot != null) {
                                for (final ItemStack ingot : OreDictionary.getOres(n)) {
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

        if (ORENAMES.contains("oreAluminum") && ORENAMES.contains("oreAluminium")) {
            final Iterator<Ore> oreIter = ORES.iterator();
            while (oreIter.hasNext()) {
                final Ore ore = oreIter.next();
                if ("oreAluminium".equals(ore.oreName())) {
                    oreIter.remove();
                    ORENAMES.remove("oreAluminium");
                    break;
                }
            }
        }
    }

    private static String adjustName(final String name) {
        if ("FzDarkIron".equals(name)) {
            return "Dark Iron";
        } else {
            return name;
        }
    }

    public static void initColors() {
        for (final Ore ore : ORES) {
            ore.setColor(getColor(ore.oreName().substring(3)));
        }
    }

    private static int getStackColor(final ItemStack stack, final int pass) {
        if (Loader.isModLoaded("gregtech")) {
            try {
                final Class<?> cls = Class.forName("gregtech.api.items.GT_MetaGenerated_Item");
                final Class<?> itemCls = stack.getItem().getClass();
                if (cls.isAssignableFrom(itemCls)) {
                    final Method m = itemCls.getMethod("getRGBa", ItemStack.class);
                    final short[] rgba = (short[]) m.invoke(stack.getItem(), stack);
                    return new Color(rgba[0], rgba[1], rgba[2], rgba[3]).getRGB();
                }
            } catch (Exception e) {}
        }
        return stack.getItem().getColorFromItemStack(stack, pass);
    }

    private static Color getColor(final String oreName) {
        final List<ItemStack> ores = OreDictionary.getOres("ingot" + oreName);
        if (ores.isEmpty()) {
            return null;
        }

        final Set<Color> colors = new LinkedHashSet<Color>();
        for (final ItemStack stack : ores) {
            try {
                final BufferedImage texture = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(getIconResource(stack)).getInputStream());
                final Color texColour = getAverageColor(texture);
                colors.add(texColour);
                for (int pass = 0; pass < stack.getItem().getRenderPasses(stack.getItemDamage()); pass++) {
                    final int c = getStackColor(stack, pass);
                    if (c != 0xFFFFFF) {
                        colors.add(new Color(c));
                        colors.remove(texColour);
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }

        float red = 0;
        float green = 0;
        float blue = 0;
        for (final Color c : colors) {
            red += c.getRed();
            green += c.getGreen();
            blue += c.getBlue();
        }
        final float count = colors.size();
        return new Color((int) (red / count), (int) (green / count), (int) (blue / count));
    }

    private static Color getAverageColor(final BufferedImage image) {
        float red = 0;
        float green = 0;
        float blue = 0;
        float count = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                final Color c = new Color(image.getRGB(i, j));
                if (c.getAlpha() != 255 || c.getRed() <= 10 && c.getBlue() <= 10 && c.getGreen() <= 10) {
                    continue;
                }
                red += c.getRed();
                green += c.getGreen();
                blue += c.getBlue();
                count++;
            }
        }

        return new Color((int) (red / count), (int) (green / count), (int) (blue / count));
    }

    private static String getIconName(final ItemStack stack) {
        final IIcon icon = stack.getItem().getIconFromDamage(stack.getItemDamage());
        if (icon != null) {
            return icon.getIconName();
        }
        return null;
    }

    private static ResourceLocation getIconResource(final ItemStack stack) {
        String iconName = getIconName(stack);
        if (iconName == null) {
            return null;
        }

        String string = "minecraft";

        final int colonIndex = iconName.indexOf(58);
        if (colonIndex >= 0) {
            if (colonIndex > 1) {
                string = iconName.substring(0, colonIndex);
            }

            iconName = iconName.substring(colonIndex + 1, iconName.length());
        }

        string = string.toLowerCase();
        iconName = "textures/items/" + iconName + ".png";
        return new ResourceLocation(string, iconName);
    }

    public static boolean isProcessableOre(final ItemStack items) {
        final int[] ids = OreDictionary.getOreIDs(items);
        for (final int id : ids) {
            if (Ore.ORENAMES.contains(OreDictionary.getOreName(id))) {
                return true;
            }
        }
        return false;
    }

    protected Ore(final String oreName, final ItemStack ingot, final String name) {
        this.oreName = oreName;
        this.ingot = ingot;
        this.name = adjustName(name);
        ORES.add(this);
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

    public void setColor(final Color color) {
        this.color = color;
    }

    public void setPure(final Item pure) {
        pureItem = pure;
    }

    public Item getPure() {
        return pureItem;
    }

    public int getIngotsPerStage(final int stage) {
        return ingotsPerStage[stage];
    }

    public void setIngotsPerStage(final List<String> nums) {
        for (int i = 0; i < ingotsPerStage.length; i++) {
            ingotsPerStage[i] = Integer.parseInt(nums.get(i).trim());
        }
    }

    @Override
    public int hashCode() {
        return oreName.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Ore && oreName.equals(((Ore) obj).oreName);
    }

    @Override
    public String toString() {
        return oreName();
    }

    public void setEnabled(final boolean state) {
        this.state = state;
        if (!state) {
            ORENAMES.remove(oreName);
        }
    }

    public boolean isEnabled() {
        return state;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
