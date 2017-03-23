package theflogat.technomancy.lib.compat.waila;

import java.util.List;

import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaFusor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

public class EssentiaFusorHUDHandler implements IWailaDataProvider {

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        WailaHelper.drawDefault(currenttip, accessor.getTileEntity());
        if (accessor.getPlayer().isSneaking()) {
            TileEssentiaFusor tile = (TileEssentiaFusor) accessor.getTileEntity();
            for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                if (side != ForgeDirection.UP && side != ForgeDirection.DOWN) {
                    if (tile.getEssentiaType(side) != null) {
                        currenttip.add(side.name() + " : " + tile.getEssentiaType(side).getName());
                    } else {
                        currenttip.add(side.name() + " : None");
                    }
                }
            }
        }
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
        if (te != null) {
            te.writeToNBT(tag);
        }
        return tag;
    }
}
