package theflogat.technomancy.common.rituals.l;

import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import theflogat.technomancy.api.rituals.IRitualEffectHandler;
import theflogat.technomancy.api.rituals.Ritual;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;

public class RitualPurificationT2 extends Ritual implements IRitualEffectHandler{
	
	public RitualPurificationT2() {
		super(new Type[]{Type.LIGHT,Type.LIGHT},Type.LIGHT);
	}

	@Override
	public boolean canApplyEffect(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {
		((TileCatalyst)w.getTileEntity(x, y, z)).handler = this;
	}

	@Override
	public void applyEffect(TileCatalyst te) {
		ArrayList<EntityLivingBase> e = (ArrayList<EntityLivingBase>) te.getWorldObj().getEntitiesWithinAABB(EntityLivingBase.class,
				AxisAlignedBB.getBoundingBox(te.xCoord-1, te.yCoord, te.zCoord-1, te.xCoord+1, te.yCoord+11, te.zCoord+1));
		for(Entity ent : e){
			if(ent.isCreatureType(EnumCreatureType.monster, false))
				ent.setDead();
		}
	}
}
