package theflogat.technomancy.common.rituals.b;

import java.util.ArrayList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
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
		((TileCatalyst)w.getTileEntity(new BlockPos(x, y, z))).handler = this;
		((TileCatalyst)w.getTileEntity(new BlockPos(x, y, z))).data = new Object[]{0};
	}

	@Override
	public void applyEffect(TileCatalyst te) {
		if(((Integer)te.data[0]).intValue()>=1000){
			te.getWorld().setBlockState(te.getPos(), TMBlocks.fountainExistence.getBlockState().getBaseState());
		}else{
			@SuppressWarnings("unchecked")
			ArrayList<EntityLivingBase> e = (ArrayList<EntityLivingBase>) te.getWorld().getEntitiesWithinAABB(EntityLivingBase.class,
					new AxisAlignedBB(te.getPos().getX() - 11, te.getPos().getY() - 11, te.getPos().getZ() - 11, te.getPos().getX() + 11, te.getPos().getY() + 11, te.getPos().getZ() + 11));

			for(EntityLivingBase ent : e) {
				if(!ent.getIsInvulnerable() && !(ent instanceof EntityPlayer)) {
					ent.onDeath(DamageSource.GENERIC);
					ent.setDead();
					te.data[0] = ((Integer)te.data[0]).intValue() + ExistenceConversion.getValue(ent);
				}
			}
		}
	}
}
