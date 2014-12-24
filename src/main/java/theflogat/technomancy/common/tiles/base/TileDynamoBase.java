package theflogat.technomancy.common.tiles.base;

import theflogat.technomancy.handlers.util.RedstoneSet;
import theflogat.technomancy.handlers.util.WorldHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

public abstract class TileDynamoBase extends TileTechnomancy implements IEnergyHandler {

	public static final int maxEnergy = 40000;
	public static final int maxExtract = 160;

	public RedstoneSet set = RedstoneSet.HIGH;
	public int ener = 0;
	public boolean boost = false;
	public byte facing = 1;
	public int fuel = 0;

	@Override
	public void updateEntity() {
		if(worldObj.isRemote)
			return;

		if(set.canRun(this)){
			if(fuel<32){
				fuel += extractFuel(calcEner());
			}
			if(fuel!=0 && ener + calcEner() <= maxEnergy){
				ener += calcEner();
				fuel--;
			}
		}
		
		if(ener>0){
			updateAdjacentHandlers();
		}
	}

	public int calcEner() {
		return Math.min(maxEnergy - ener, (boost ? 320 : 80));
	}

	public void update() {
		worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
		shouldRefresh(worldObj.getBlock(xCoord, yCoord, zCoord), worldObj.getBlock(xCoord, yCoord, zCoord), 0, 0, worldObj, xCoord, yCoord, zCoord);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	protected void updateAdjacentHandlers() {
		TileEntity tile = WorldHelper.getAdjacentTileEntity(this, facing);
		if(WorldHelper.isEnergyHandlerFromOppFacing(tile, facing)) {
			ener -= ((IEnergyHandler)tile).receiveEnergy(ForgeDirection.VALID_DIRECTIONS[facing].getOpposite(), Math.min(maxExtract, ener), false);		
		}
		update();
//		if(worldObj.getTotalWorldTime() % 4L == 0L) {
//			onNeighborTileChange(xCoord + ForgeDirection.VALID_DIRECTIONS[facing].offsetX, yCoord +
//					ForgeDirection.VALID_DIRECTIONS[facing].offsetY, zCoord + ForgeDirection.VALID_DIRECTIONS[facing].offsetZ);
//		}
	}

	public boolean rotateBlock() {
		for (int i = facing + 1; i < facing + 6; i++){
			TileEntity tile = WorldHelper.getAdjacentTileEntity(this, (byte) (i % 6));
			if ((tile instanceof IEnergyHandler)) {
				facing = (byte) (i % 6);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord));
				updateAdjacentHandlers();
				return true;
			}
		}
		for (int i = facing + 1; i < facing + 6; i++) {
			if (WorldHelper.isEnergyHandlerFromOppFacing(this, (byte) (i % 6))) {
				facing = (byte) (i % 6);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord));
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
		comp.setInteger("energy", ener);;
		comp.setByte("face", facing);
		comp.setInteger("fuel", fuel);
	}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		set = RedstoneSet.load(comp);
		ener = comp.getInteger("energy");
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
//		updateAdjacentHandlers();
	}

	@Override
	public Packet getDescriptionPacket() {
		if(!worldObj.isRemote){
			NBTTagCompound comp = new NBTTagCompound();writeToNBT(comp);
			return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, comp);
		}
		return null;
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		if(pkt!=null && pkt.func_148857_g()!=null){
			readFromNBT(pkt.func_148857_g());
		}
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if(from.ordinal()==facing){
			int ener = this.ener;
			this.ener -= simulate ? 0 : Math.min(maxExtract, ener);
			return Math.min(maxExtract, ener);
		}
		return 0;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from.ordinal()==facing;
	}
	
	@Override
	public int getEnergyStored(ForgeDirection from) {
		return ener;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return maxEnergy;
	}
	
	public ForgeDirection getDirFromFacing(){
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
			if(dir.ordinal()==facing)
				return dir;
		}
		return ForgeDirection.UNKNOWN;
	}
}