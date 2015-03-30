package theflogat.technomancy.common.tiles.base;

import theflogat.technomancy.util.Coords;
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

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound){
		super.readFromNBT(nbttagcompound);
		readCustomNBT(nbttagcompound);
	}

	public abstract void readCustomNBT(NBTTagCompound comp);

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound){
		super.writeToNBT(nbttagcompound);
		writeCustomNBT(nbttagcompound);
	}

	public abstract void writeCustomNBT(NBTTagCompound comp);

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeToNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -999, nbttagcompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		readFromNBT(pkt.func_148857_g());
	}

	public void onNeighborBlockChange() {}

	public void onNeighborTileChange(int tileX, int tileY, int tileZ) {}

}
