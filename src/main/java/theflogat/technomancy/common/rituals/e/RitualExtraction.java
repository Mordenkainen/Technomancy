package theflogat.technomancy.common.rituals.e;

import java.util.ArrayList;
import org.apache.logging.log4j.Level;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.api.rituals.Ritual;
import theflogat.technomancy.common.items.base.TMItems;

public class RitualExtraction extends Ritual{

	public RitualExtraction() {
		super(new Type[]{Type.DARK,Type.DARK,Type.LIGHT}, Type.EARTH);
	}

	@Override
	public boolean canApplyEffect(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {
		@SuppressWarnings("unchecked")
		ArrayList<EntityLivingBase> l = (ArrayList<EntityLivingBase>) w.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB
				.getBoundingBox(x - 5, y - 5, z - 5, x + 5,  y + 5, z + 5));
		if(!l.isEmpty()){
			for(EntityLivingBase ent : l){
				if(ent instanceof EntityVillager && ent.getEntityData().hasKey("treasure")){
					ItemStack it = TMItems.treasures.getTreasure(ent.getEntityData().getString("treasure"));
					if(it!=null){
						ent.onDeath(DamageSource.generic);
						ent.setDead();
						w.spawnEntityInWorld(new EntityItem(w, x, y+2, z, it));
					}else{
						Technomancy.logger.log(Level.ERROR, "Treasure Error: Please Report to Mod Author");
					}
					return;
				}
			}
			w.createExplosion(null, x, y, z, 30, true);
		}
	}
}
