package theflogat.technomancy.common.tiles.technom.existence;

import java.util.ArrayList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import theflogat.technomancy.common.tiles.base.TileTechnomancyRedstone;

public class TileExistenceBurner extends TileTechnomancyRedstone implements IExistenceProducer{

	public TileExistenceBurner() {
		super(RedstoneSet.LOW);
	}
	
	public int power;
	private static final int maxPower = 100;
	
	@Override
	public void update() {
		if(world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos))==1){
			world.setTileEntity(pos, new TileExistenceDynamicBurner());
		}
		if(set.canRun(this) && power<maxPower){
			@SuppressWarnings("unchecked")
			ArrayList<EntityLivingBase> e = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class,
					new AxisAlignedBB(pos.getX() - 3, pos.getY() - 3, pos.getZ()- 3, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3));

			for(EntityLivingBase ent : e) {
				if(!ent.getIsInvulnerable() && !(ent instanceof EntityPlayer)) {
					ent.onDeath(DamageSource.GENERIC);
					ent.setDead();
					power += ent instanceof EntityVillager ? 15 : (EnumCreatureType.MONSTER.getCreatureClass().isAssignableFrom(ent.getClass()) ? 1 : 2);
				}
			}
		}
		
		if(power>maxPower){
			power = maxPower;
		}
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		power = comp.getInteger("power");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		comp.setInteger("power", power);
	}

	@Override
	public int getPower() {
		return power;
	}

	@Override
	public int getPowerCap() {
		return maxPower;
	}

	@Override
	public int getMaxRate() {
		return 4;
	}

	@Override
	public boolean canInput() {
		return false;
	}

	@Override
	public boolean canOutput() {
		return true;
	}

	@Override
	public void addPower(int val) {
		power += val;
	}
}