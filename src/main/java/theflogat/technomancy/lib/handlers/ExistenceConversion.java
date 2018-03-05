package theflogat.technomancy.lib.handlers;

import java.util.HashMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.IAnimals;

public final class ExistenceConversion {
	
	static HashMap<Class<? extends IAnimals>, Integer> values = new HashMap<>();
	
	static {
		values.put(EntityVillager.class, 50);
		values.put(EnumCreatureType.AMBIENT.getCreatureClass(), 5);
		values.put(EnumCreatureType.CREATURE.getCreatureClass(), 5);
		values.put(EnumCreatureType.MONSTER.getCreatureClass(), 1);
	}
	
	public static int getValue(EntityLivingBase ent){
		Class<? extends EntityLivingBase> c = ent.getClass();
		for(Class<? extends IAnimals> b : values.keySet()){
			if(b.isAssignableFrom(c) || b.isInstance(ent)){
				return values.get(b);
			}
		}
		return 1;
	}
	
	public static int getGem(EntityLivingBase ent) {
		return Math.max(1, getValue(ent)/2);
	}
}
