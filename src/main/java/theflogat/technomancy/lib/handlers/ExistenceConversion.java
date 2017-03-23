package theflogat.technomancy.lib.handlers;

import java.util.HashMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityVillager;

public final class ExistenceConversion {

    static HashMap<Class<? extends EntityLivingBase>, Integer> values = new HashMap<Class<? extends EntityLivingBase>, Integer>();

    static {
        values.put(EntityVillager.class, 50);
        values.put(EnumCreatureType.ambient.getCreatureClass(), 5);
        values.put(EnumCreatureType.creature.getCreatureClass(), 5);
        values.put(EnumCreatureType.monster.getCreatureClass(), 1);
    }

    public static int getValue(EntityLivingBase ent) {
        Class<? extends EntityLivingBase> c = ent.getClass();
        for (Class<? extends EntityLivingBase> b : values.keySet()) {
            if (b.isAssignableFrom(c) || b.isInstance(ent)) {
                return values.get(b);
            }
        }
        return 1;
    }

    public static int getGem(EntityLivingBase ent) {
        return Math.max(1, getValue(ent) / 2);
    }
}
