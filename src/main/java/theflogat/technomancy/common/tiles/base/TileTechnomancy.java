package theflogat.technomancy.common.tiles.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import javax.annotation.Nullable;

public abstract class TileTechnomancy extends TileEntity implements ITickable {

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound){
		super.readFromNBT(nbttagcompound);
		readCustomNBT(nbttagcompound);
		readSyncData(nbttagcompound);
	}

	public abstract void readCustomNBT(NBTTagCompound comp);

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound){
		writeCustomNBT(nbttagcompound);
		writeSyncData(nbttagcompound);
		return super.writeToNBT(nbttagcompound);
	}

	@Override
	public void update() {
	}

	public abstract void writeCustomNBT(NBTTagCompound comp);
	
	public abstract void writeSyncData(NBTTagCompound compound);
	
	public abstract void readSyncData(NBTTagCompound compound);

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeSyncData(nbttagcompound);
		return new SPacketUpdateTileEntity(pos, -999, nbttagcompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readSyncData(pkt.getNbtCompound());
		super.onDataPacket(net, pkt);
	}
}
