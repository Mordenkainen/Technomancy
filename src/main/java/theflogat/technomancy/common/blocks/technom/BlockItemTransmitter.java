package theflogat.technomancy.common.blocks.technom;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockCoilTransmitter;
import theflogat.technomancy.common.items.technom.ItemBoost;
import theflogat.technomancy.common.items.technom.ItemCoilCoupler;
import theflogat.technomancy.common.tiles.technom.TileItemTransmitter;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;
import theflogat.technomancy.lib.RenderIds;

public class BlockItemTransmitter extends BlockCoilTransmitter {

    public BlockItemTransmitter() {
        super();
        setBlockName(Reference.getId(Names.ITEMTRANSMITTER));
    }

    @Override
    public TileEntity createNewTileEntity(World w, int meta) {
        return new TileItemTransmitter();
    }

    @Override
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        ItemStack items = player.inventory.mainInventory[player.inventory.currentItem];
        if (items != null && (items.getItem() instanceof ItemCoilCoupler || items.getItem() instanceof ItemBoost)) {
            return false;
        }
        boolean flag = super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ);
        TileItemTransmitter tile = getTE(w, x, y, z);
        if (tile != null) {
            if (player.isSneaking() && !flag) {
                if (tile.filter != null) {
                    if (!w.isRemote) {
                        tile.filter = null;
                        w.markBlockForUpdate(x, y, z);
                    }
                    return true;
                }
            } else if (items != null && tile.filter == null) {
                if (!w.isRemote) {
                    tile.addFilter(player.inventory.mainInventory[player.inventory.currentItem]);
                    w.markBlockForUpdate(x, y, z);
                }
                return true;
            }
        }
        return flag;
    }

    @Override
    public int getRenderType() {
        return RenderIds.idItemTransmitter;
    }

    @Override
    public void getNBTInfo(NBTTagCompound comp, ArrayList<String> l, int meta) {
        super.getNBTInfo(comp, l, meta);
        if (comp.hasKey("filter")) {
            NBTTagCompound item = comp.getCompoundTag("filter");
            ItemStack filter = ItemStack.loadItemStackFromNBT(item);
            l.add("Filter: " + StatCollector.translateToLocal(filter.getUnlocalizedName() + ".name"));
        }
    }

    private static TileItemTransmitter getTE(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        return tile instanceof TileItemTransmitter ? (TileItemTransmitter) tile : null;
    }
}
