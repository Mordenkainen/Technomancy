package theflogat.technomancy.common.blocks.bloodmagic.machines;

import java.util.ArrayList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.BlockProcessor;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBMProcessor;

public class BlockBMProcessor extends BlockProcessor {

    public BlockBMProcessor() {
        name = "BM";
    }

    @Override
    public boolean onBlockActivated(final World w, final int x, final int y, final int z, final EntityPlayer player, final int side, final float hitX, final float hitY, final float hitZ) {
        if (super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ)) {
            return true;
        }
        if (player != null) {
            TileEntity te = w.getTileEntity(x, y, z);
            if (te instanceof TileBMProcessor) {
                if (((TileBMProcessor) te).owner.equals("")) {
                    ((TileBMProcessor) te).owner = player.getDisplayName();
                }
                player.openGui(Technomancy.instance, 1, w, x, y, z);
            }
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(final World w, final int x, final int y, final int z, final EntityLivingBase entity, final ItemStack items) {
        super.onBlockPlacedBy(w, x, y, z, entity, items);
        TileEntity tile = w.getTileEntity(x, y, z);
        if (tile instanceof TileBMProcessor && entity instanceof EntityPlayer) {
            ((TileBMProcessor) tile).owner = ((EntityPlayer) entity).getDisplayName();
        }
    }

    @Override
    public TileEntity createNewTileEntity(final World w, final int meta) {
        return new TileBMProcessor();
    }

    @Override
    public void getNBTInfo(final NBTTagCompound comp, final ArrayList<String> l, final int meta) {
        super.getNBTInfo(comp, l, meta);
        l.add("Owner:" + comp.getString("owner"));
    }
}