package theflogat.technomancy.lib.compat.waila;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class WailaHUDNBT extends WailaHUDBase {

    @Override
    public NBTTagCompound getNBTData(final EntityPlayerMP player, final TileEntity te, final NBTTagCompound tag, final World world, final int x, final int y, final int z) {
        if (te != null) {
            te.writeToNBT(tag);
        }
        return tag;
    }

}
