package theflogat.technomancy.common.rituals.b;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import theflogat.technomancy.api.rituals.IRitualEffectHandler;
import theflogat.technomancy.api.rituals.Ritual;
import theflogat.technomancy.client.renderers.models.ModelBlackSphere;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.util.Loc;
import theflogat.technomancy.util.RitualHelper;

public class RitualBlackHoleT3 extends Ritual implements IRitualEffectHandler{
	
	ModelBlackSphere specialRender = new ModelBlackSphere(8.1F, -1F, 0, 1F);
	private static final ResourceLocation textLoc = new ResourceLocation(Ref.MODEL_REF_TEXTURE);

	@Override
	public boolean isCoreComplete(World w, int x, int y, int z) {
		return w.getBlockMetadata(x, y, z)==black;
	}

	@Override
	public boolean isFrameComplete(World w, int x, int y, int z) {
		return RitualHelper.checkForT1(w, x, y, z, black) && RitualHelper.checkForT2(w, x, y, z, black) && RitualHelper.checkForT3(w, x, y, z, black);
	}

	@Override
	public boolean canApplyEffect(World w, int x, int y, int z) {
		return true;
	}

	private boolean canDestroy(World w, int x, int y, int z, int i, int j, int k) {
		if(w.getBlock(x+i, y+j, z+k).getBlockHardness(w, x+i, y+j, z+k)<0)
			return false;
		
		return true;
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {
		for(int i=-9; i<=9; i++){
			for(int j=-9; j<=9; j++){
				for(int k=-9; k<=9; k++){
					int xx = x + i;
					int yy = y + j;
					int zz = z + k;
					if(canDestroy(w, x, y, z, i, j, k)){
						if(i!=0 || j!=0 || k!=0){
							w.setBlockToAir(xx, yy, zz);
						}
					}
				}
			}
		}

		ArrayList<EntityLivingBase> e = (ArrayList<EntityLivingBase>) w.getEntitiesWithinAABB(EntityLivingBase.class,
				AxisAlignedBB.getBoundingBox(x-2, y-2, z-2, x+2, y+2, z+2));

		for(EntityLivingBase ent : e){
			if(ent.isEntityInvulnerable()){
				ent.onDeath(DamageSource.generic);
				ent.setDead();
			}
		}
		
		((TileCatalyst)w.getTileEntity(x, y, z)).remCount = 60;
		((TileCatalyst)w.getTileEntity(x, y, z)).handler = this;
		if(Loc.isClient()){
			((TileCatalyst)w.getTileEntity(x, y, z)).specialRender = specialRender;
			((TileCatalyst)w.getTileEntity(x, y, z)).textLoc = textLoc;
		}
	}

	@Override
	public void applyEffect(TileCatalyst te) {
		ArrayList<EntityLivingBase> e = (ArrayList<EntityLivingBase>) te.getWorldObj().getEntitiesWithinAABB(EntityLivingBase.class,
				AxisAlignedBB.getBoundingBox(te.xCoord-2, te.yCoord-2, te.zCoord-2, te.xCoord+2, te.yCoord+2, te.zCoord+2));

		for(EntityLivingBase ent : e){
			if(ent.isEntityInvulnerable()){
				ent.onDeath(DamageSource.generic);
				ent.setDead();
			}
		}
	}
}
