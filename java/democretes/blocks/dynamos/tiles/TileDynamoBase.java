package democretes.blocks.dynamos.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;
import cofh.api.tileentity.IEnergyInfo;
import cofh.api.tileentity.IReconfigurableFacing;
import cofh.util.BlockHelper;
import cofh.util.EnergyHelper;
import cpw.mods.fml.relauncher.Side;
import democretes.blocks.TileTechnomancy;
import democretes.lib.Platform;

public abstract class TileDynamoBase extends TileTechnomancy implements IEnergyHandler, IEnergyInfo, IReconfigurableFacing {


	public int minPower = 4;
	public int maxPower = 80;
	public int maxEnergy = 40000;
	public int maxTransfer = 160;
	public int minPowerLevel = 36000;
	public int maxPowerLevel = 4000;
	public int energyRamp = 9;
	public byte facing = 1;

	EnergyStorage energyStorage;
	IEnergyHandler adjacentHandler = null;
	int fuelRF;
	boolean isActive = false;
	boolean cached = false;
	int check;


	public TileDynamoBase() {
		this.energyStorage = new EnergyStorage(this.maxEnergy, this.maxTransfer);
	}

	protected void attenuate(){
		this.energyStorage.modifyEnergyStored(-this.minPower);
		if (this.fuelRF > 0) {
			this.fuelRF -= 10;
			if (this.fuelRF < 0) {
				this.fuelRF = 0;
			}
		}
	}

	public int calcEnergy(){
		if (!this.isActive) {
			return 0;
		}
		if (this.energyStorage.getEnergyStored() < this.maxPowerLevel) {
			return this.maxPower;
		}
		if (this.energyStorage.getEnergyStored() > this.minPowerLevel) {
			return this.minPower;
		}
		return (this.energyStorage.getMaxEnergyStored() - this.energyStorage.getEnergyStored()) / this.energyRamp;
	}

	protected void transferEnergy(int bSide){
		updateAdjacentHandlers();
		if(this.adjacentHandler != null) {
			this.energyStorage.modifyEnergyStored(-this.adjacentHandler.receiveEnergy(ForgeDirection.VALID_DIRECTIONS[(bSide ^ 0x1)], Math.min(this.maxTransfer, this.energyStorage.getEnergyStored()), false));
		}
	}

	@Override
	public void updateEntity(){
		if (Platform.isClient()) {
			return;
		}
		if (!this.cached) {
			onNeighborBlockChange();
		}
		if (this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)) {
			if (this.isActive) {
				if (canGenerate()){
					generate();
					transferEnergy(this.facing);
				}else{
					this.isActive = false;
				}
			}else if (canGenerate()){
				this.isActive = true;
				generate();
				transferEnergy(this.facing);
			}else{
				attenuate();
			}	
		}
		this.isActive = false;
		attenuate();
		if(this.worldObj.getTotalWorldTime() % 4L == 0L) {
			onNeighborTileChange(this.xCoord + ForgeDirection.VALID_DIRECTIONS[this.facing].offsetX, this.yCoord + ForgeDirection.VALID_DIRECTIONS[this.facing].offsetY, this.zCoord + ForgeDirection.VALID_DIRECTIONS[this.facing].offsetZ);
		}
	}

	public boolean rotateBlock() {
		//		for (int i = this.facing + 1; i < this.facing + 6; i++){
		//			TileEntity theTile = BlockHelper.getAdjacentTileEntity(this, i % 6);
		//			if ((theTile instanceof IEnergyHandler)) {
		//				this.facing = ((byte)(i % 6));
		//				this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord));
		//				updateAdjacentHandlers();
		//				return true;
		//			}
		//		}
		//		for (int i = this.facing + 1; i < this.facing + 6; i++) {
		//			if (EnergyHelper.isAdjacentEnergyHandlerFromSide(this, i % 6)) {
		//				this.facing = ((byte)(i % 6));
		//				this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord));
		//				updateAdjacentHandlers();
		//				return true;
		//			}
		//		}
		return false;
	}


	protected void updateAdjacentHandlers() {
		if (Platform.isClient()) {
			return;
		}
		TileEntity tile = BlockHelper.getAdjacentTileEntity(this, this.facing);
		if (EnergyHelper.isEnergyHandlerFromSide(tile, ForgeDirection.VALID_DIRECTIONS[(this.facing ^ 0x1)])) {
			this.adjacentHandler = ((IEnergyHandler)tile);			
		}else{
			this.adjacentHandler = null;
		}
		this.cached = true;
		sendUpdatePacket(Side.CLIENT);
	}

	public int getLightValue()	  {
		return this.isActive ? 7 : 0;
	}

	public IEnergyStorage getEnergyStorage()	  {
		return this.energyStorage;
	} 

	public boolean isActive()	  {
		return this.isActive;
	}

	@Override
	public void onNeighborBlockChange()	  {
		super.onNeighborBlockChange();
		updateAdjacentHandlers();

	}

	public void onNeighborTileChange(int tileX, int tileY, int tileZ)	  {
		super.onNeighborTileChange(tileX, tileY, tileZ);
		updateAdjacentHandlers();
	}

	@Override
	public void readCustomNBT(NBTTagCompound compound)	  {	    
		this.isActive = compound.getBoolean("Active");
		this.fuelRF = compound.getInteger("Fuel");	    
		this.energyStorage.readFromNBT(compound);

	}

	@Override
	public void writeCustomNBT(NBTTagCompound compound)	  {
		compound.setBoolean("Active", this.isActive);
		compound.setInteger("Fuel", this.fuelRF);	    
		this.energyStorage.writeToNBT(compound);

	}

	public void sendUpdatePacket(Side side) {
		worldObj.updateAllLightTypes(xCoord, yCoord, zCoord);
		shouldRefresh(worldObj.getBlockId(xCoord, yCoord, zCoord), worldObj.getBlockId(xCoord, yCoord, zCoord), 0, 0, worldObj, xCoord,
				yCoord, zCoord);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public boolean canInsertItem(int slot, ItemStack stack, int side){
		return side != this.facing;
	}

	public boolean canExtractItem(int slot, ItemStack stack, int side){
		return side != this.facing;
	}

	public int getFacing(){
		return this.facing;
	}

	public boolean setFacing(int side){
		return false;
	}

	public boolean allowYAxisFacing(){
		return true;
	}

	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
		return 0;
	}

	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate){
		return 0;
	}

	public boolean canInterface(ForgeDirection from){
		return from.ordinal() == this.facing;
	}

	public int getEnergyStored(ForgeDirection from){
		return this.energyStorage.getEnergyStored();
	}

	public int getMaxEnergyStored(ForgeDirection from){
		return this.energyStorage.getMaxEnergyStored();
	}

	public int getEnergyPerTick(){
		return calcEnergy();
	}

	public int getMaxEnergyPerTick(){
		return this.maxPower;
	}

	public int getEnergy(){
		return this.energyStorage.getEnergyStored();
	}

	public int getMaxEnergy(){
		return this.maxEnergy;
	}

	protected abstract boolean canGenerate();

	protected abstract void generate();
	
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
}