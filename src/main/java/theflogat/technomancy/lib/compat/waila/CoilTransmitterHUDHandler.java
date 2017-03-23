package theflogat.technomancy.lib.compat.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import theflogat.technomancy.common.tiles.base.TileCoilTransmitter;
import theflogat.technomancy.common.tiles.technom.TileItemTransmitter;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaTransmitter;

public class CoilTransmitterHUDHandler implements IWailaDataProvider {

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        final TileCoilTransmitter te = (TileCoilTransmitter) accessor.getTileEntity();
        if (te.redstoneState) {
            currenttip.add("Currently Emitting a Signal");
        }
        if (te instanceof TileEssentiaTransmitter && ((TileEssentiaTransmitter) te).aspectFilter != null) {
            currenttip.add("Filter: " + ((TileEssentiaTransmitter) te).aspectFilter.getName());
        }
        if (te instanceof TileItemTransmitter && ((TileItemTransmitter) te).filter != null) {
            currenttip.add("Filter: " + StatCollector.translateToLocal(((TileItemTransmitter) te).filter.getUnlocalizedName() + ".name"));
        }
        WailaHelper.drawDefault(currenttip, te);
        return currenttip;
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        return null;
    }
}
