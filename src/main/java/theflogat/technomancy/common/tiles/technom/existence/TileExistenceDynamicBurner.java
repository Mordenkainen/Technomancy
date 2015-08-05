package theflogat.technomancy.common.tiles.technom.existence;

import java.util.ArrayList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import theflogat.technomancy.common.tiles.base.TileMachineRedstone;

public class TileExistenceDynamicBurner extends TileMachineRedstone implements IExistenceProducer{

	public TileExistenceDynamicBurner() {
		super(100000, RedstoneSet.LOW);
	}
	
	public int power = 0;
	private static final int maxPower = 150;
	
	@Override
	public void updateEntity() {
		if(power<maxPower && energy>=10000){
			@SuppressWarnings("unchecked")
			ArrayList<EntityLivingBase> e = (ArrayList<EntityLivingBase>) worldObj.getEntitiesWithinAABB(EntityLivingBase.class,
					AxisAlignedBB.getBoundingBox(xCoord - 3, yCoord - 3, zCoord - 3, xCoord + 3, yCoord + 3, zCoord + 3));

			for(EntityLivingBase ent : e) {
				if(!ent.isEntityInvulnerable() && !(ent instanceof EntityPlayer)) {
					ent.onDeath(DamageSource.generic);
					ent.setDead();
					power += ent instanceof EntityVillager ? 20 : (EnumCreatureType.monster.getCreatureClass().isAssignableFrom(ent.getClass()) ? 2 : 4);
					energy -= 10000;
				}
			}
		}
		
		if(power>maxPower){
			power = maxPower;
		}
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		super.readCustomNBT(comp);
		power = comp.getInteger("power");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		super.writeCustomNBT(comp);
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