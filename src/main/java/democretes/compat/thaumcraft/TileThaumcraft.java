/**
 * 
 * This class was created by <Azanor>. It's distributed as
 * part of the Thaumcraft Mod.
 * 
 **/

package democretes.compat.thaumcraft;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
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
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, -999, nbttagcompound);
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		super.onDataPacket(net, pkt);
		readCustomNBT(pkt.data);
	}
}