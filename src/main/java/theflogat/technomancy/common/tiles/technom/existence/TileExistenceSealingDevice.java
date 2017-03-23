package theflogat.technomancy.common.tiles.technom.existence;

import java.util.ArrayList;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.AxisAlignedBB;
import theflogat.technomancy.common.tiles.base.TileExistenceRedstoneBase;

public class TileExistenceSealingDevice extends TileExistenceRedstoneBase {

    public TileExistenceSealingDevice() {
        super(RedstoneSet.LOW, 1000000);
    }

    @Override
    public void updateEntity() {
        if (power >= 500000) {
            int rad = 3;
            ArrayList<EntityVillager> e = (ArrayList<EntityVillager>) getWorldObj().getEntitiesWithinAABB(EntityVillager.class, AxisAlignedBB.getBoundingBox(xCoord - rad, yCoord - rad, zCoord - rad, xCoord + rad, yCoord + rad, zCoord + rad));
            for (EntityVillager ent : e) {
                if (ent != null && ent.getEntityData().hasKey("treasure") && !ent.getEntityData().hasKey("seal")) {
                    ent.getEntityData().setBoolean("seal", true);
                    power -= 500000;
                    return;
                }
            }
        }
    }
}
