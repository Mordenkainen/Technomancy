package theflogat.technomancy.common.items.technom.existence;

import java.util.List;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemGlasses extends ItemArmor {

    public ItemGlasses() {
        super(Technomancy.existencePower, 0, 0);
        setUnlocalizedName(Ref.getId(Names.itemGlasses));
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 1, 0));
        player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 1, 2));
        player.addPotionEffect(new PotionEffect(Potion.resistance.id, 1, 0));
        player.addPotionEffect(new PotionEffect(Ids.slowFall, 1, 0));

        if (stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound();
        } else {
            if (stack.getItemDamage() != 0 && stack.stackTagCompound.hasKey("repair")) {
                stack.setItemDamage(stack.getItemDamage() - 1);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List l, boolean moreInfo) {
        if (stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound();
        } else {
            if (stack.stackTagCompound.hasKey("repair")) {
                l.add("Auto-Repairs");
            }
        }
        super.addInformation(stack, player, l, moreInfo);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return Ref.MOD_ID + ":textures/armor/" + Names.itemGlasses + ".png";
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        itemIcon = reg.registerIcon(Ref.getAsset(Names.itemGlasses));
    }
}