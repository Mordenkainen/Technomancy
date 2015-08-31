package theflogat.technomancy.common.rituals.b;

import java.util.ArrayList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import theflogat.technomancy.api.rituals.IRitualEffectHandler;
import theflogat.technomancy.api.rituals.Ritual;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.lib.handlers.ExistenceConversion;

public class RitualFountainExistence extends Ritual implements IRitualEffectHandler  {

	public RitualFountainExistence() {
		super(new Type[]{Type.LIGHT, Type.DARK, Type.LIGHT}, Type.DARK);
	}

	@Override
	public boolean canApplyEffect(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {
		removeFrame(w, x, y, z);
		((TileCatalyst)w.getTileEntity(x, y, z)).handler = this;
		((TileCatalyst)w.getTileEntity(x, y, z)).data = new Object[]{0};
	}

	@Override
	public void applyEffect(TileCatalyst te) {
		if(((Integer)te.data[0]).intValue()>=1000){
			te.getWorldObj().setBlock(te.xCoord, te.yCoord, te.zCoord, TMBlocks.fountainExistence);
		}else{
			@SuppressWarnings("unchecked")
			ArrayList<EntityLivingBase> e = (ArrayList<EntityLivingBase>) te.getWorldObj().getEntitiesWithinAABB(EntityLivingBase.class,
					AxisAlignedBB.getBoundingBox(te.xCoord - 11, te.yCoord - 11, te.zCoord - 11, te.xCoord + 11, te.yCoord + 11, te.zCoord + 11));

			for(EntityLivingBase ent : e) {
				if(!ent.isEntityInvulnerable() && !(ent instanceof EntityPlayer)) {
					ent.onDeath(DamageSource.generic);
					ent.setDead();
					te.data[0] = ((Integer)te.data[0]).intValue() + ExistenceConversion.getValue(ent);
				}
			}
		}
	}
}
