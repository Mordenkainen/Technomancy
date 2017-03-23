package theflogat.technomancy.common.tiles.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class TileTechnomancy extends TileEntity {

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        readCustomNBT(nbttagcompound);
        readSyncData(nbttagcompound);
    }

    public abstract void readCustomNBT(NBTTagCompound comp);

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        writeCustomNBT(nbttagcompound);
        writeSyncData(nbttagcompound);
    }

    public abstract void writeCustomNBT(NBTTagCompound comp);

    public abstract void writeSyncData(NBTTagCompound compound);

    public abstract void readSyncData(NBTTagCompound compound);

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeSyncData(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -999, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readSyncData(pkt.func_148857_g());
    }
}
