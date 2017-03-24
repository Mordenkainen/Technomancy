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

public class RitualPurification extends Ritual implements IRitualEffectHandler {

    protected int radiusX, radiusZ, minY, maxY;

    public RitualPurification(final Type[] frame, final Type core, final int radX, final int radZ, final int minY, final int maxY) {
        super(frame, core);
        radiusX = radX;
        radiusZ = radZ;
        this.minY = minY;
        this.maxY = maxY;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void applyEffect(final TileCatalyst te) {
        final ArrayList<EntityLivingBase> e = (ArrayList<EntityLivingBase>) te.getWorldObj().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(te.xCoord - radiusX, te.yCoord + minY, te.zCoord - radiusZ, te.xCoord + radiusX, te.yCoord + maxY, te.zCoord + radiusZ));
        for (final Entity ent : e) {
            if (ent.isCreatureType(EnumCreatureType.monster, false)) {
                ent.setDead();
            }
        }
    }

    @Override
    public boolean canApplyEffect(final World w, final int x, final int y, final int z) {
        return true;
    }

    @Override
    public void applyEffect(final World w, final int x, final int y, final int z) {
        ((TileCatalyst) w.getTileEntity(x, y, z)).handler = this;
    }

}
