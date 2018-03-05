package theflogat.technomancy.common.tiles.base;

import cofh.redstoneflux.api.IEnergyHandler;
import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.api.IEnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.EnumSkyBlock;
import theflogat.technomancy.util.helpers.WorldHelper;

public abstract class TileDynamoBase extends TileTechnomancyRedstone implements IEnergyHandler, IEnergyReceiver, IEnergyProvider, IUpgradable, IWrenchable {
	public TileDynamoBase() {
		super(RedstoneSet.HIGH);
	}

	public static final int maxEnergy = 40000;
	public static final int maxExtract = 320;

	public int ener = 0;
	public boolean boost = false;
	public byte facing = 1;
	public int fuel = 0;

	@Override
	public void update() {
		if(world.isRemote)
			return;

		if(set.canRun(this)) {
			if(fuel < 32) {
				fuel += extractFuel(calcEner());
			}
			if(fuel != 0 && ener + calcEner() <= maxEnergy) {
				ener += calcEner();
				fuel--;
			}
		}
		
		updateAdjacentHandlers();
	}

	public int calcEner() {
		return Math.min(maxEnergy - ener, (boost ? 320 : 80));
	}

	public void update2() {
		world.checkLightFor(EnumSkyBlock.BLOCK, pos);
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
	}

	protected void updateAdjacentHandlers() {
		if(ener > 0) {
			TileEntity tile = WorldHelper.getAdjacentTileEntity(this, facing);
			EnumFacing dir = EnumFacing.VALUES[facing%EnumFacing.VALUES.length];
			if(WorldHelper.isEnergyHandlerFromOppFacing(tile, facing)) {
				ener -= ((IEnergyReceiver)tile).receiveEnergy(dir, Math.min(maxExtract, ener), false);
			}
		}
		update2();
	}

	public String nextRedstoneSet() {
		set = set.cycle();
		return set.id;
	}

	@Override
	public boolean toggleBoost(){
		boost = !boost;
		return boost;
	}
	
	@Override
	public boolean getBoost() {
		return boost;
	}
	
	@Override
	public void setBoost(boolean newBoost) {
		boost = newBoost;
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {}
	
	@Override
	public void writeSyncData(NBTTagCompound comp) {
		super.writeSyncData(comp);
		comp.setInteger("energy", ener);
		comp.setByte("face", facing);
		comp.setInteger("fuel", fuel);
		comp.setBoolean("Boost", boost);
	}
	
	@Override
	public void readSyncData(NBTTagCompound comp) {
		super.readSyncData(comp);
		ener = comp.getInteger("energy");
		facing = comp.getByte("face");
		fuel = comp.getInteger("fuel");
		boost = comp.getBoolean("Boost");
	}

	public abstract int extractFuel(int ener);

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		if(from.ordinal() == facing) {
			int ener = this.ener;
			this.ener -= simulate ? 0 : Math.min(maxExtract, ener);
			return Math.min(maxExtract, ener);
		}
		return 0;
	}



	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return from.ordinal() == facing;
	}
	
	@Override
	public int getEnergyStored(EnumFacing from) {
		return ener;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return maxEnergy;
	}
	
	@Override
	public boolean onWrenched(boolean sneaking) {
		for (int i = facing + 1; i < facing + 6; i++) {
			TileEntity tile = WorldHelper.getAdjacentTileEntity(this, (byte)(i % 6));
			if (WorldHelper.isEnergyHandlerFromOppFacing(tile, (byte)(i % 6))) {
				if(!world.isRemote) {
					facing = (byte)(i % 6);
					world.notifyNeighborsOfStateChange(pos, world.getBlockState(pos).getBlock(), true);
					updateAdjacentHandlers();
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getInfos() {
		return "360 RF/t For Four Times The Fuel";
	}
}