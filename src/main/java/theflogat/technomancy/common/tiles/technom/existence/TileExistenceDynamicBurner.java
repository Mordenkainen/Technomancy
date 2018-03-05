package theflogat.technomancy.common.tiles.technom.existence;

import java.util.ArrayList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import theflogat.technomancy.common.tiles.base.TileMachineRedstone;

public class TileExistenceDynamicBurner extends TileMachineRedstone implements IExistenceProducer{

	public TileExistenceDynamicBurner() {
		super(100000, RedstoneSet.LOW);
	}
	
	public int power = 0;
	private static final int maxPower = 150;
	
	@Override
	public void update() {
		if(power<maxPower && energy>=10000){
			@SuppressWarnings("unchecked")
			ArrayList<EntityLivingBase> e = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class,
					new AxisAlignedBB(pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3));

			for(EntityLivingBase ent : e) {
				if((!ent.getIsInvulnerable()) && !(ent instanceof EntityPlayer)) {
					ent.onDeath(DamageSource.GENERIC);
					ent.setDead();
					power += ent instanceof EntityVillager ? 20 : (EnumCreatureType.MONSTER.getCreatureClass().isAssignableFrom(ent.getClass()) ? 2 : 4);
					energy -= 10000;
				}
			}
		}
		
		if(power>maxPower){
			power = maxPower;
		}
	}
	
	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return from==EnumFacing.DOWN;
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
		return power>0;
	}

	@Override
	public void addPower(int val) {
		power += val;
	}
}