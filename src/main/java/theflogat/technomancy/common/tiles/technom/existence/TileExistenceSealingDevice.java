package theflogat.technomancy.common.tiles.technom.existence;

import java.util.ArrayList;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.math.AxisAlignedBB;
import theflogat.technomancy.common.tiles.base.TileExistenceRedstoneBase;

public class TileExistenceSealingDevice extends TileExistenceRedstoneBase{

	public TileExistenceSealingDevice() {
		super(RedstoneSet.LOW, 1000000);
	}
	
	@Override
	public void update() {
		if(power>=500000){
			int rad = 3;
			ArrayList<EntityVillager> e = (ArrayList<EntityVillager>) world.getEntitiesWithinAABB(EntityVillager.class,
					new AxisAlignedBB(pos.getX() - rad, pos.getY() - rad, pos.getZ() - rad, pos.getX() + rad, pos.getY() + rad, pos.getZ() + rad));
			for(EntityVillager ent : e){
				if(ent!=null && ent.getEntityData().hasKey("treasure") && !ent.getEntityData().hasKey("seal")){
					ent.getEntityData().setBoolean("seal", true);
					power -= 500000;
					return;
				}
			}
		}
	}
}
