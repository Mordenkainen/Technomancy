package theflogat.technomancy.common.items.technom;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import theflogat.technomancy.common.items.base.ItemBase;
import theflogat.technomancy.lib.Reference;
import theflogat.technomancy.util.Ore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemProcessedOre extends ItemBase {

    public static final int MAXSTAGE = 6;

    protected String[] processors = { "Thaumcraft", "Botania", "Blood Magic", "Ars Magica", "Witchery", "Totemic" };
    protected Ore ore;

    public ItemProcessedOre(Ore ore) {
        this.ore = ore;
        setMaxStackSize(64);
        setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public IIcon[] itemIcon;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister icon) {
        itemIcon = new IIcon[MAXSTAGE];
        for (int i = 0; i < MAXSTAGE; i++) {
            itemIcon[i] = icon.registerIcon(Reference.TEXTURE_PREFIX + "ore" + i);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs tab, List list) {
        for (int i = 0; i < MAXSTAGE; i++) {
            ItemStack stack = new ItemStack(id, 1, i);
            list.add(stack);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par) {
        return itemIcon[par % itemIcon.length];
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return Reference.MOD_PREFIX + "pure" + ore.oreName().substring(3);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return String.format(StatCollector.translateToLocal("item.techno.pure.name") + ore.name());
    }

    @Override
    public ItemStack onItemRightClick(ItemStack items, World w, EntityPlayer player) {
        for (int i = 0; i < processors.length; i++) {
            if (items.stackTagCompound != null) {
                if (items.stackTagCompound.hasKey(processors[i])) {
                    player.addChatComponentMessage(new ChatComponentText(processors[i] + " " + items.stackTagCompound.getInteger(processors[i]) + "x/2x"));
                }
            }
        }
        return items;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (!(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
            list.add(EnumChatFormatting.BLUE.toString() + EnumChatFormatting.ITALIC + StatCollector.translateToLocal("info.techno:purity") + ": " + (stack.getItemDamage() + 1));
            list.add(EnumChatFormatting.WHITE.toString() + EnumChatFormatting.ITALIC + StatCollector.translateToLocal("info.techno:shift"));
        } else {
            list.add(StatCollector.translateToLocal(this.getUnlocalizedName()));
            list.remove("item.null");
            list.add(EnumChatFormatting.BLUE.toString() + EnumChatFormatting.ITALIC + StatCollector.translateToLocal("info.techno:purity") + ": " + (stack.getItemDamage() + 1));
            list.add(EnumChatFormatting.WHITE.toString() + EnumChatFormatting.ITALIC + StatCollector.translateToLocal("info.techno:process") + ":");
            for (int i = 0; i < processors.length; i++) {
                if (stack.stackTagCompound != null) {
                    if (stack.stackTagCompound.hasKey(processors[i])) {
                        list.add(processors[i] + " " + stack.stackTagCompound.getInteger(processors[i]) + "x/2x");
                    }
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        return ore.color();
    }
}