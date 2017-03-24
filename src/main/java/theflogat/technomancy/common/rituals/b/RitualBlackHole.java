package theflogat.technomancy.common.rituals.b;

import java.util.ArrayList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import theflogat.technomancy.api.rituals.IRitualEffectHandler;
import theflogat.technomancy.api.rituals.Ritual;
import theflogat.technomancy.client.models.ModelBlackSphere;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.lib.Reference;
import theflogat.technomancy.util.Location;

public abstract class RitualBlackHole extends Ritual implements IRitualEffectHandler {

    ModelBlackSphere specialRender;
    protected static final ResourceLocation textLoc = new ResourceLocation(Reference.MODEL_SPHERE);
    protected final int radiusX, radiusY, radiusZ;

    public RitualBlackHole(Type[] frame, Type core, int radX, int radY, int radZ) {
        super(frame, core);
        radiusX = radX;
        radiusY = radY;
        radiusZ = radZ;
    }

    @Override
    public void applyEffect(TileCatalyst te) {
        @SuppressWarnings("unchecked")
        ArrayList<EntityLivingBase> e = (ArrayList<EntityLivingBase>) te.getWorldObj().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(te.xCoord - radiusX, te.yCoord - radiusY, te.zCoord - radiusZ, te.xCoord + radiusX, te.yCoord + radiusY, te.zCoord + radiusZ));

        for (EntityLivingBase ent : e) {
            if (ent.isEntityInvulnerable()) {
                ent.onDeath(DamageSource.generic);
                ent.setDead();
            }
        }
    }

    @Override
    public boolean canApplyEffect(World w, int x, int y, int z) {
        return true;
    }

    @Override
    public void applyEffect(World w, int x, int y, int z) {
        for (int i = -radiusX; i <= radiusX; i++) {
            for (int j = -radiusY; j <= radiusY; j++) {
                for (int k = -radiusZ; k <= radiusZ; k++) {
                    int xx = x + i;
                    int yy = y + j;
                    int zz = z + k;
                    if (canDestroy(w, x, y, z, i, j, k)) {
                        if (i != 0 || j != 0 || k != 0) {
                            w.setBlockToAir(xx, yy, zz);
                        }
                    }
                }
            }
        }
        removeFrame(w, x, y, z);

        @SuppressWarnings("unchecked")
        ArrayList<EntityLivingBase> e = (ArrayList<EntityLivingBase>) w.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x - radiusX, y - radiusY, z - radiusZ, x + radiusX, y + radiusY, z + radiusZ));

        for (EntityLivingBase ent : e) {
            if (ent.isEntityInvulnerable()) {
                ent.onDeath(DamageSource.generic);
                ent.setDead();
            }
        }

        ((TileCatalyst) w.getTileEntity(x, y, z)).remCount = 60;
        ((TileCatalyst) w.getTileEntity(x, y, z)).handler = this;
        if (Location.isClient()) {
            ((TileCatalyst) w.getTileEntity(x, y, z)).specialRender = specialRender;
            ((TileCatalyst) w.getTileEntity(x, y, z)).textLoc = textLoc;
        }
    }

    protected static boolean canDestroy(World w, int x, int y, int z, int i, int j, int k) {
        return !(w.getBlock(x + i, y + j, z + k).getBlockHardness(w, x + i, y + j, z + k) < 0);
    }

}
