package theflogat.technomancy.lib.compat.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.SpecialChars;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.util.Coords;

public class FakeAirNGHUDHandler extends WailaHUDBase {

    @Override
    public List<String> getWailaBody(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        final TileFakeAirNG tileAir = (TileFakeAirNG) accessor.getTileEntity();
        final Coords nodeGenerator = tileAir.getMain();
        final TileNodeGenerator tileEntity = (TileNodeGenerator) nodeGenerator.w.getTileEntity(nodeGenerator.x, nodeGenerator.y, nodeGenerator.z);
        if (tileEntity != null) {
            if (tileEntity.isBoosted()) {
                currenttip.add(SpecialChars.GREEN + "Potency Gem Installed");
            }
            currenttip.add("Redstone Setting: " + WailaHelper.formatSetting(((IRedstoneSensitive) tileEntity).getCurrentSetting().id));
            currenttip.add(tileEntity.canRun() ? SpecialChars.GREEN + "Enabled" : SpecialChars.RED + "Disabled");
            if (accessor.getNBTData().getBoolean("Active")) {
                currenttip.add(accessor.getNBTData().getBoolean("Spawn") ? "Mode: Create Node" : tileEntity.isBoosted() ? "Mode: Enhance Node" : "Mode: Recharge Node");
            }
        }
        return currenttip;
    }

    @Override
    public ItemStack getWailaStack(final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        return new ItemStack(Item.getItemFromBlock(TMBlocks.nodeGenerator), 1);
    }

    @Override
    public NBTTagCompound getNBTData(final EntityPlayerMP player, final TileEntity te, final NBTTagCompound tag, final World world, final int x, final int y, final int z) {
        if (te != null) {
            final Coords nodeGenerator = ((TileFakeAirNG) te).getMain();
            final TileNodeGenerator tileEntity = (TileNodeGenerator) nodeGenerator.w.getTileEntity(nodeGenerator.x, nodeGenerator.y, nodeGenerator.z);
            tileEntity.writeToNBT(tag);
        }
        return tag;
    }

}
