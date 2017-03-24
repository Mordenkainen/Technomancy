package theflogat.technomancy.common.items.thaumcraft;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.IScribeTools;
import thaumcraft.common.tiles.TileResearchTable;
import thaumcraft.common.tiles.TileTable;
import theflogat.technomancy.common.items.base.ItemBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPen extends ItemBase implements IScribeTools {

    public ItemPen() {
        this.maxStackSize = 1;
        this.canRepair = true;
        setMaxDamage(3000);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = w.getTileEntity(x, y, z);
        int md = w.getBlockMetadata(x, y, z);
        Block bi = w.getBlock(x, y, z);

        if (tile instanceof TileTable && md != 6) {
            if (w.isRemote) {
                return false;
            }
            for (int a = 2; a < 6; a++) {
                ForgeDirection dir = ForgeDirection.getOrientation(a);
                TileEntity tile2 = w.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);

                int md2 = w.getBlockMetadata(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);

                if (tile2 instanceof TileTable && md2 < 6) {
                    w.setBlock(x, y, z, bi, a, 0);
                    w.setTileEntity(x, y, z, new TileResearchTable());
                    w.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, bi, dir.getOpposite().ordinal() + 4, 0);

                    w.markBlockForUpdate(x, y, z);
                    w.markBlockForUpdate(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);

                    TileEntity tile3 = w.getTileEntity(x, y, z);
                    if (tile3 instanceof TileResearchTable) {
                        ((IInventory) tile3).setInventorySlotContents(0, stack.copy());
                        if (!player.capabilities.isCreativeMode) {
                            player.inventory.decrStackSize(player.inventory.currentItem, 1);
                            player.inventory.markDirty();
                        }
                        w.markBlockForUpdate(x, y, z);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister icon) {
        itemIcon = icon.registerIcon(Reference.getAsset("penItem"));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return Reference.getId(Names.PEN);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List info, boolean useExtraInformation) {
        info.add("It's the 21st century,");
        info.add("why are we using quills and ink?");
    }

}
