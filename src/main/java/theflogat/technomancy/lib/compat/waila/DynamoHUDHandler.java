package theflogat.technomancy.lib.compat.waila;

import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.SpecialChars;
import net.minecraft.item.ItemStack;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.common.tiles.bloodmagic.dynamos.TileBloodDynamo;

public class DynamoHUDHandler extends WailaHUDBase {

    @Override
    public List<String> getWailaBody(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        final TileDynamoBase tileEntity = (TileDynamoBase) accessor.getTileEntity();
        WailaHelper.drawDefault(currenttip, tileEntity);
        if (tileEntity instanceof TileBloodDynamo) {
            currenttip.add(SpecialChars.DRED + "Blood: " + ((TileBloodDynamo) tileEntity).liquid + " / " + TileBloodDynamo.CAPACITY);
        }

        return currenttip;
    }

}
