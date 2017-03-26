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

    @Override
    public boolean matches(final IInventory inv, final World world, final EntityPlayer player) {
        if (!ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), "TECHNOTURGESCEPTER")) {
            return false;
        }

        if (ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 0) != null || ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 1) != null || ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 2) != null || ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 2) != null) {
            return false;
        }
        
        final ItemStack cap1 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 0);
        final ItemStack cap2 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 1);
        final ItemStack cap3 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 2);
        final ItemStack rod = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 1);
        final ItemStack focus = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 0);
        
        return checkMatch(cap1, cap2, cap3, rod, focus, player);
    }

    private static boolean checkMatch(final ItemStack cap1, final ItemStack cap2, final ItemStack cap3, final ItemStack rod, final ItemStack focus, final EntityPlayer player) {
        boolean bc = false;
        boolean br = false;

        if (cap1 != null && cap2 != null && cap3 != null && rod != null && focus != null && checkItemEquals(focus, new ItemStack(ConfigItems.itemResource, 1, 15)) && checkItemEquals(cap1, cap2) && checkItemEquals(cap1, cap3)) {
            for (final WandCap wc : WandCap.caps.values()) {
                if (checkItemEquals(cap1, wc.getItem()) && ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), wc.getResearch())) {
                    bc = true;
                    break;
                }
            }

            if (rod.getItem() == TMItems.itemWandCores && rod.getItem().getDamage(rod) == 1) {
                br = true;
            }
        }
        return br && bc;
    }

    @Override
    public ItemStack getCraftingResult(final IInventory paramIInventory) {
        if (ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 0, 0) != null || ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 0, 1) != null || ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 1, 2) != null || ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 2, 2) != null) {
            return null;
        }

        ItemStack out = null;
        String bc = null;
        String br = null;
        int cc = 0;
        int cr = 0;
        final ItemStack cap1 = ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 1, 0);
        final ItemStack cap2 = ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 2, 1);
        final ItemStack cap3 = ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 0, 2);
        final ItemStack rod = ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 1, 1);
        final ItemStack focus = ThaumcraftApiHelper.getStackInRowAndColumn(paramIInventory, 2, 0);
        
        if (cap1 != null && cap2 != null && cap3 != null && rod != null && focus != null && checkItemEquals(focus, new ItemStack(ConfigItems.itemResource, 1, 15)) && checkItemEquals(cap1, cap2) && checkItemEquals(cap1, cap3)) {
            for (final WandCap wc : WandCap.caps.values()) {
                if (checkItemEquals(cap1, wc.getItem())) {
                    bc = wc.getTag();
                    cc = wc.getCraftCost();
                    break;
                }
            }

            if (rod.getItem() == TMItems.itemWandCores && rod.getItem().getDamage(rod) == 1) {
                final WandRod wr = WandRod.rods.get("technoturge");
                br = wr.getTag();
                cr = wr.getCraftCost();
            }

            if (bc != null && br != null) {
                final int cost = (int) (cc * cr * 1.5F);
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
    public AspectList getAspects(final IInventory inv) {
        final AspectList al = new AspectList();

        if (ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 0) != null || ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 1) != null || ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 2) != null || ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 2) != null) {
            return al;
        }

        int cc = -1;
        int cr = -1;
        final ItemStack cap1 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 0);
        final ItemStack cap2 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 1);
        final ItemStack cap3 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 2);
        final ItemStack rod = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 1);
        final ItemStack focus = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 0);
        
        int cost;
        if (cap1 != null && cap2 != null && cap3 != null && rod != null && focus != null && checkItemEquals(focus, new ItemStack(ConfigItems.itemResource, 1, 15)) && checkItemEquals(cap1, cap2) && checkItemEquals(cap1, cap3)) {
            for (final WandCap wc : WandCap.caps.values()) {
                if (checkItemEquals(cap1, wc.getItem())) {
                    cc = wc.getCraftCost();
                    break;
                }
            }

            for (final WandRod wr : WandRod.rods.values()) {
                if (checkItemEquals(rod, wr.getItem())) {
                    cr = wr.getCraftCost();
                    break;
                }
            }

            if (cc >= 0 && cr >= 0) {
                cost = (int) (cc * cr * 1.5F);
                for (final Aspect as : Aspect.getPrimalAspects()) {
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

    private static boolean checkItemEquals(final ItemStack target, final ItemStack input) {
        if (input == null && target != null || input != null && target == null) {
            return false;
        }
        return target.getItem() == input.getItem() && target.getItemDamage() == input.getItemDamage();
    }
}
