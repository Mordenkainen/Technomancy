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

public class RitualPurificationT3 extends Ritual implements IRitualEffectHandler{
	
	@Override
	public boolean isCoreComplete(World w, int x, int y, int z) {
		return w.getBlockMetadata(x, y, z)==white;
	}

	@Override
	public boolean isFrameComplete(World w, int x, int y, int z) {
		return RitualHelper.checkForT1(w, x, y, z, white) && RitualHelper.checkForT2(w, x, y, z, white) && RitualHelper.checkForT3(w, x, y, z, white);
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
				AxisAlignedBB.getBoundingBox(te.xCoord-3, te.yCoord-1, te.zCoord-3, te.xCoord+3, 256, te.zCoord+3));
		for(Entity ent : e){
			if(ent.isCreatureType(EnumCreatureType.monster, false))
				ent.setDead();
		}
	}
}
