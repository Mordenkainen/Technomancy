/**
 * 
 * This class was created by <Azanor>. It's distributed as
 * part of the Thaumcraft Mod.
 * 
 **/

package theflogat.technomancy.handlers.compat.thaumcraft;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileThaumcraft extends TileEntity{
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		readCustomNBT(nbttagcompound);
	}



	public void readCustomNBT(NBTTagCompound nbttagcompound) {}


	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		writeCustomNBT(nbttagcompound);
	}

	public void writeCustomNBT(NBTTagCompound nbttagcompound) {}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeCustomNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -999, nbttagcompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		super.onDataPacket(net, pkt);
		readCustomNBT(pkt.func_148857_g());
	}
}