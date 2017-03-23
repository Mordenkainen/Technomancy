package theflogat.technomancy.common.items.botania;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.util.IIcon;

public class ItemManaBucket extends ItemBucket {

    public ItemManaBucket(Block fluid) {
        super(fluid);
        setUnlocalizedName(Ref.MOD_PREFIX + Names.manaBucket);
        setContainerItem(Items.bucket);
        setCreativeTab(Technomancy.tabsTM);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_) {
        return Items.water_bucket.getIconFromDamage(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister icon) {}
}
