package theflogat.technomancy.common.items.tome;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;

public class ItemRitualTome extends Item {

    public ItemRitualTome() {
        super();
        setCreativeTab(Technomancy.tabsTM);
        setUnlocalizedName(Reference.getId(Names.RITUALTOME));
    }

    @Override
    public ItemStack onItemRightClick(final ItemStack item, final World w, final EntityPlayer player) {
        player.openGui(Technomancy.instance, 3, w, 0, 0, 0);
        return item;
    }

    @Override
    public void registerIcons(final IIconRegister reg) {
        itemIcon = reg.registerIcon(Reference.getAsset(Names.RITUALTOME));
    }

    public static class Resources {

        public static final String E = ".png";

        public static final String BH1 = "blackHoleT1" + E;
        public static final String BH2 = "blackHoleT2" + E;
        public static final String BH3 = "blackHoleT3" + E;

        public static final String CI1 = "caveInT1" + E;
        public static final String CI2 = "caveInT2" + E;
        public static final String CI3 = "caveInT3" + E;

        public static final String PU1 = "purificationT1" + E;
        public static final String PU2 = "purificationT2" + E;
        public static final String PU3 = "purificationT3" + E;

        public static final String F1 = "fireT1" + E;
        public static final String F2 = "fireT2" + E;
        public static final String F3 = "fireT3" + E;

        public static final String W1 = "waterT1" + E;
        public static final String W2 = "waterT2" + E;
        public static final String W3 = "waterT3" + E;

        public static final String FT = "fountain" + E;
        public static final String ET = "extract" + E;
    }

}
