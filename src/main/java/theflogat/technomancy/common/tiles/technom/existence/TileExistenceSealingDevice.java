package theflogat.technomancy.common.tiles.technom.existence;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.AxisAlignedBB;
import theflogat.technomancy.common.tiles.base.TileExistenceRedstoneBase;

public class TileExistenceSealingDevice extends TileExistenceRedstoneBase {

    public TileExistenceSealingDevice() {
        super(RedstoneSet.LOW, 1000000);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateEntity() {
        if (power >= 500000) {
            final List<EntityVillager> e = (ArrayList<EntityVillager>) getWorldObj().getEntitiesWithinAABB(EntityVillager.class, AxisAlignedBB.getBoundingBox(xCoord - 3, yCoord - 3, zCoord - 3, xCoord + 3, yCoord + 3, zCoord + 3));
            for (final EntityVillager ent : e) {
                if (ent != null && ent.getEntityData().hasKey("treasure") && !ent.getEntityData().hasKey("seal")) {
                    ent.getEntityData().setBoolean("seal", true);
                    power -= 500000;
                    return;
                }
            }
        }
    }
}
