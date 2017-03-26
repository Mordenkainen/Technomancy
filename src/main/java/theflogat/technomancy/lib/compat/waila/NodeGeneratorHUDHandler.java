package theflogat.technomancy.lib.compat.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.SpecialChars;
import net.minecraft.item.ItemStack;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;

public class NodeGeneratorHUDHandler extends WailaHUDNBT {

    @Override
    public List<String> getWailaBody(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        final TileNodeGenerator tileEntity = (TileNodeGenerator) accessor.getTileEntity();
        if (tileEntity.isBoosted()) {
            currenttip.add(SpecialChars.GREEN + "Potency Gem Installed");
        }
        currenttip.add("Redstone Setting: " + WailaHelper.formatSetting(((IRedstoneSensitive) tileEntity).getCurrentSetting().id));
        currenttip.add(tileEntity.canRun() ? SpecialChars.GREEN + "Enabled" : SpecialChars.RED + "Disabled");
        if (accessor.getNBTData().getBoolean("Active")) {
            currenttip.add(accessor.getNBTData().getBoolean("Spawn") ? "Mode: Create Node" : tileEntity.isBoosted() ? "Mode: Enhance Node" : "Mode: Recharge Node");
        }
        return currenttip;
    }

}
