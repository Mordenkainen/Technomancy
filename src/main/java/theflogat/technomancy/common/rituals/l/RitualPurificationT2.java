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
import theflogat.technomancy.util.RitualHelper;

public class RitualPurificationT2 extends Ritual implements IRitualEffectHandler{
	
	@Override
	public boolean isCoreComplete(World w, int x, int y, int z) {
		return w.getBlockMetadata(x, y, z)==white;
	}

	@Override
	public boolean isFrameComplete(World w, int x, int y, int z) {
		return RitualHelper.checkForT1(w, x, y, z, white) && RitualHelper.checkForT2(w, x, y, z, white);
	}

	@Override
	public boolean applyEffect(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void afterEffect(World w, int x, int y, int z) {
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
