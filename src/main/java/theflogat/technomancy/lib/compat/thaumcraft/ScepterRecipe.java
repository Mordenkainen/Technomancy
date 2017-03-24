package theflogat.technomancy.lib.compat.thaumcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import theflogat.technomancy.common.items.base.TMItems;

public class ScepterRecipe implements IArcaneRecipe {

    public ScepterRecipe() {}

    @Override
    public boolean matches(IInventory inv, World world, EntityPlayer player) {
        if (!ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), "TECHNOTURGESCEPTER")) {
            return false;
        }
        ItemStack cap1 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 0);
        ItemStack cap2 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 1);
        ItemStack cap3 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 2);
        ItemStack rod = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 1);
        ItemStack focus = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 0);

        if ((ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 0) != null) || (ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 1) != null) || (ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 2) != null) || (ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 2) != null)) {
            return false;
        }
        return checkMatch(cap1, cap2, cap3, rod, focus, player);
    }

    private static boolean checkMatch(ItemStack cap1, ItemStack cap2, ItemStack cap3, ItemStack rod, ItemStack focus, EntityPlayer player) {
        boolean bc = false;
        boolean br = false;

        if ((cap1 != null) && (cap2 != null) && (cap3 != null) && (rod != null) && (focus != null) && (checkItemEquals(focus, new ItemStack(ConfigItems.itemResource, 1, 15))) && (checkItemEquals(cap1, cap2)) && (checkItemEquals(cap1, cap3))) {
            for (WandCap wc : WandCap.caps.values()) {
                if ((checkItemEquals(cap1, wc.getItem())) && (ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), wc.getResearch()))) {
                    bc = true;
                    break;
                }
            }

            if (rod.getItem() == TMItems.itemWandCores && rod.getItem().getDamage(rod) == 1) {
                br = true;
            }
        }
        return (br) && (bc);
    }

    @Override
    public ItemStack getCraftingResult(IInventory paramIInventory) {
        ItemStack out = null;
        String bc = null;
        String br = null;
        int cc = 0;
        int cr = 0;
        ItemStack cap1 = ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 1, 0);
        ItemStack cap2 = ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 2, 1);
        ItemStack cap3 = ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 0, 2);
        ItemStack rod = ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 1, 1);
        ItemStack focus = ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 2, 0);

        if ((ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 0, 0) != null) || (ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 0, 1) != null) || (ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 1, 2) != null) || (ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 2, 2) != null)) {
            return null;
        }

        if ((cap1 != null) && (cap2 != null) && (cap3 != null) && (rod != null) && (focus != null) && (checkItemEquals(focus, new ItemStack(ConfigItems.itemResource, 1, 15))) && (checkItemEquals(cap1, cap2)) && (checkItemEquals(cap1, cap3))) {
            for (WandCap wc : WandCap.caps.values()) {
                if (checkItemEquals(cap1, wc.getItem())) {
                    bc = wc.getTag();
                    cc = wc.getCraftCost();
                    break;
                }
            }

            if (rod.getItem() == TMItems.itemWandCores && rod.getItem().getDamage(rod) == 1) {
                WandRod wr = WandRod.rods.get("technoturge");
                br = wr.getTag();
                cr = wr.getCraftCost();
            }

            if ((bc != null) && (br != null)) {
                int cost = (int) (cc * cr * 1.5F);
                out = new ItemStack(TMItems.technoScepter, 1, cost);
                ((ItemWandCasting) out.getItem()).setCap(out, WandCap.caps.get(bc));
                ((ItemWandCasting) out.getItem()).setRod(out, WandRod.rods.get(br));
                out.setTagInfo("sceptre", new NBTTagByte((byte) 1));
            }
        }
        return out;
    }

    @Override
    public int getRecipeSize() {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    @Override
    public AspectList getAspects() {
        return null;
    }

    @Override
    public AspectList getAspects(IInventory inv) {
        AspectList al = new AspectList();

        int cc = -1;
        int cr = -1;
        ItemStack cap1 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 0);
        ItemStack cap2 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 1);
        ItemStack cap3 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 2);
        ItemStack rod = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 1);
        ItemStack focus = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 0);

        if ((ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 0) != null) || (ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 1) != null) || (ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 2) != null) || (ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 2) != null)) {
            return al;
        }

        int cost;
        if ((cap1 != null) && (cap2 != null) && (cap3 != null) && (rod != null) && (focus != null) && (checkItemEquals(focus, new ItemStack(ConfigItems.itemResource, 1, 15))) && (checkItemEquals(cap1, cap2)) && (checkItemEquals(cap1, cap3))) {
            for (WandCap wc : WandCap.caps.values()) {
                if (checkItemEquals(cap1, wc.getItem())) {
                    cc = wc.getCraftCost();
                    break;
                }
            }

            for (WandRod wr : WandRod.rods.values()) {
                if (checkItemEquals(rod, wr.getItem())) {
                    cr = wr.getCraftCost();
                    break;
                }
            }

            if ((cc >= 0) && (cr >= 0)) {
                cost = (int) (cc * cr * 1.5F);
                for (Aspect as : Aspect.getPrimalAspects()) {
                    al.add(as, cost);
                }
            }
        }

        return al;
    }

    @Override
    public String getResearch() {
        return "";
    }

    private static boolean checkItemEquals(ItemStack target, ItemStack input) {
        if (((input == null) && (target != null)) || ((input != null) && (target == null))) {
            return false;
        }
        return (target.getItem() == input.getItem()) && (target.getItemDamage() == input.getItemDamage());
    }
}
