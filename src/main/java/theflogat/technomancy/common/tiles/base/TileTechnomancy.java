package theflogat.technomancy.common.tiles.base;

import theflogat.technomancy.handlers.util.Coords;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class TileTechnomancy extends TileEntity {

	protected Coords getCoords() {
		Coords coords = new Coords(xCoord, yCoord, zCoord, worldObj);
		return coords;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound){
		super.readFromNBT(nbttagcompound);
		readCustomNBT(nbttagcompound);
	}

	public abstract void readCustomNBT(NBTTagCompound comp);

	public void writeToNBT(NBTTagCompound nbttagcompound){
		super.writeToNBT(nbttagcompound);
		writeCustomNBT(nbttagcompound);
	}

	public abstract void writeCustomNBT(NBTTagCompound comp);

	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeCustomNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, -999, nbttagcompound);
	}

	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		readCustomNBT(pkt.func_148857_g());
	}

	public void onNeighborBlockChange() {}

	public void onNeighborTileChange(int tileX, int tileY, int tileZ) {}

}
