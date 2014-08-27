package democretes.tiles.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.tileentity.IEnergyInfo;
import cofh.api.tileentity.IReconfigurableFacing;
import democretes.util.RedstoneSet;
import democretes.util.WorldHelper;

public abstract class TileDynamoBase extends TileTechnomancy implements IEnergyHandler {

	public static final int maxEnergy_default = 40000;
	public static final int maxExtract_default = 160;
	public static final int maxReceive_default = 0;

	public RedstoneSet set = RedstoneSet.HIGH;
	public EnergyStorage ener;
	public boolean boost = false;
	public byte facing = 1;
	public int fuel = 0;

	public TileDynamoBase(EnergyStorage ener) {
		this.ener = ener;
	}

	@Override
	public void updateEntity() {
		if(worldObj.isRemote)
			return;

		if(set.canRun(this)){
			if(fuel!=0){
				if(!ener.isFull()){
					onNeighborBlockChange();
					ener.receiveEnergy(calcEner(), false);
				}else{
					fuel = extractFuel(calcEner());
				}
			}
		}
	}

	public int calcEner() {
		return Math.min(ener.getMaxEnergyStored() - ener.energy, (boost ? 320 : 80));
	}

	public void update() {
		worldObj.updateAllLightTypes(xCoord, yCoord, zCoord);
		shouldRefresh(worldObj.getBlockId(xCoord, yCoord, zCoord), worldObj.getBlockId(xCoord, yCoord, zCoord), 0, 0, worldObj, xCoord, yCoord, zCoord);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	protected void updateAdjacentHandlers() {
		TileEntity tile = WorldHelper.getAdjacentTileEntity(this, facing);
		if(WorldHelper.isEnergyHandler(tile, facing)) {
			ener.energy -= ((IEnergyHandler)tile).receiveEnergy(ForgeDirection.VALID_DIRECTIONS[facing], Math.min(ener.maxExtract, ener.getEnergyStored()), false);		
		}
		update();
		if(this.worldObj.getTotalWorldTime() % 4L == 0L) {
			onNeighborTileChange(this.xCoord + ForgeDirection.VALID_DIRECTIONS[this.facing].offsetX, this.yCoord + ForgeDirection.VALID_DIRECTIONS[this.facing].offsetY, this.zCoord + ForgeDirection.VALID_DIRECTIONS[this.facing].offsetZ);
		}
	}

	public boolean rotateBlock() {
		for (int i = facing + 1; i < facing + 6; i++){
			TileEntity tile = WorldHelper.getAdjacentTileEntity(this, (byte) (i % 6));
			if ((tile instanceof IEnergyHandler)) {
				facing = (byte) (i % 6);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlockId(xCoord, yCoord, zCoord));
				updateAdjacentHandlers();
				return true;
			}
		}
		for (int i = facing + 1; i < facing + 6; i++) {
			if (WorldHelper.isEnergyHandler(this, (byte) (i % 6))) {
				facing = (byte) (i % 6);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlockId(xCoord, yCoord, zCoord));
				updateAdjacentHandlers();
				return true;
			}
		}
		return false;
	}

	public String nextRedstoneSet() {
		set = set.cycle();
		return set.id;
	}

	public boolean toggleBoost(){
		boost = !boost;
		return boost;
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		set.save(comp);
		ener.save(comp);
		comp.setByte("face", facing);
		comp.setInteger("fuel", fuel);
	}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		set = set.load(comp);
		ener.load(comp);
		facing = comp.getByte("face");
		fuel = comp.getInteger("fuel");
	}

	public abstract int extractFuel(int ener);

	@Override
	public void onNeighborBlockChange()	  {
		super.onNeighborBlockChange();
		updateAdjacentHandlers();
	}

	@Override
	public void onNeighborTileChange(int tileX, int tileY, int tileZ)	  {
		super.onNeighborTileChange(tileX, tileY, tileZ);
		updateAdjacentHandlers();
	}

	@Override
	public Packet getDescriptionPacket() {
		if(!worldObj.isRemote){
			NBTTagCompound comp = new NBTTagCompound();writeToNBT(comp);
			return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, comp);
		}
		return null;
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		if(pkt!=null && pkt.data!=null){
			readFromNBT(pkt.data);
		}
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return from.ordinal()==facing ? ener.receiveEnergy(maxReceive, simulate) : 0;
	}



	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return from.ordinal()==facing ? ener.extractEnergy(maxExtract, simulate) : 0;
	}



	@Override
	public boolean canInterface(ForgeDirection from) {
		return from.ordinal()==facing;
	}



	@Override
	public int getEnergyStored(ForgeDirection from) {
		return ener.energy;
	}



	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return ener.getMaxEnergyStored();
	}
}