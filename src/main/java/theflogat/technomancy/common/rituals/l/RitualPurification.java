package theflogat.technomancy.common.rituals.l;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import theflogat.technomancy.api.rituals.IRitualEffectHandler;
import theflogat.technomancy.api.rituals.Ritual;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;

public class RitualPurification extends Ritual implements IRitualEffectHandler {
	protected int radiusX, radiusZ, minY, maxY;

	public RitualPurification(Type[] frame, Type core, int radX, int radZ, int minY, int maxY) {
		super(frame, core);
		radiusX = radX;
		radiusZ = radZ;
		this.minY = minY;
		this.maxY = maxY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void applyEffect(TileCatalyst te) {
		ArrayList<EntityLivingBase> e = (ArrayList<EntityLivingBase>) te.getWorld().getEntitiesWithinAABB(EntityLivingBase.class,
				new AxisAlignedBB(te.getPos().getX() - radiusX, te.getPos().getY() + minY, te.getPos().getZ() - radiusZ, te.getPos().getX() + radiusX, te.getPos().getY() + maxY, te.getPos().getZ() + radiusZ));
		for(Entity ent : e){
			if(ent.isCreatureType(EnumCreatureType.MONSTER, false))
				ent.setDead();
		}
	}

	@Override
	public boolean canApplyEffect(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {
		((TileCatalyst)w.getTileEntity(new BlockPos(x, y, z))).handler = this;
	}

}
