package theflogat.technomancy.common.items.base;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.items.technom.ItemBoost;
import theflogat.technomancy.common.tiles.base.IUpgradable;
import theflogat.technomancy.lib.TMConfig;

public class ItemAdvancedBase extends ItemBlock {

    protected List<String> tileInterfaces = new ArrayList<String>();

    public ItemAdvancedBase(final Block b) {
        super(b);
        final TileEntity te = ((BlockContainerAdvanced) b).createNewTileEntity(null, 0);
        final Class<?>[] interfaces = te.getClass().getInterfaces();
        for (final Class<?> curInterface : interfaces) {
            final String intName = curInterface.getName();
            tileInterfaces.add(intName.substring(intName.lastIndexOf('.') + 1, intName.length()));
        }
        setUnlocalizedName(b.getUnlocalizedName());
        if (tileInterfaces.contains("IUpgradable")) {
            final IUpgradable up = (IUpgradable) te;
            final String name = String.format(StatCollector.translateToLocal(b.getUnlocalizedName() + ".name"));
            ItemBoost.upgradeable.add(name + ": " + up.getInfo());
        }
    }

    @Override
    public void onUpdate(final ItemStack items, final World w, final Entity player, final int dmg, final boolean held) {
        if (items.stackTagCompound == null) {
            initializeNBT(items);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(final ItemStack items, final EntityPlayer player, final List l, final boolean moreInfo) {
        if (items == null) {
            return;
        }
        try {
            if (items.stackTagCompound == null) {
                initializeNBT(items);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                ((BlockContainerAdvanced) field_150939_a).getNBTInfo(items.stackTagCompound, (ArrayList<String>) l, items.getItemDamage());
            } else {
                l.add(EnumChatFormatting.WHITE.toString() + EnumChatFormatting.ITALIC + StatCollector.translateToLocal("info.techno:shift"));
            }
        } catch (Exception e) {
            TMConfig.error(e);
        }
    }

    public void initializeNBT(final ItemStack items) {
        items.stackTagCompound = new NBTTagCompound();
        ((BlockContainerAdvanced) field_150939_a).createNewTileEntity(null, 0).writeToNBT(items.stackTagCompound);
    }
}
