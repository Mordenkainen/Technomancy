package theflogat.technomancy.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public final class ToolWrench {

    private static List<String> wrenchInterfaces = new ArrayList<String>();
    private static List<String> wrenchClasses = new ArrayList<String>();

    private ToolWrench() {}

    static {
        wrenchInterfaces.add("IToolWrench");
        wrenchInterfaces.add("IYetaWrench");
        wrenchInterfaces.add("IAEWrench");
        wrenchInterfaces.add("IToolHammer");
        wrenchInterfaces.add("IToolCrowbar");

        wrenchClasses.add("ItemWandCasting");
        wrenchClasses.add("ItemTechnoturgeScepter");
    }

    public static boolean isWrench(final ItemStack items) {
        if (items != null) {
            final Class<?>[] interfaces = items.getItem().getClass().getInterfaces();
            for (final Class<?> curInterface : interfaces) {
                final String intName = curInterface.getName();
                if (wrenchInterfaces.contains(intName.substring(intName.lastIndexOf('.') + 1, intName.length()))) {
                    return true;
                }
            }

            final String name = items.getItem().getClass().getName();
            if (wrenchClasses.contains(name.substring(name.lastIndexOf('.') + 1, name.length()))) {
                return true;
            }
        }

        return false;
    }
}
