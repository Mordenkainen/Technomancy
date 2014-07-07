package democretes.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileTechnomancy extends TileEntity {
	
	
	  public void readFromNBT(NBTTagCompound nbttagcompound)
	  {
	    super.readFromNBT(nbttagcompound);
	    readCustomNBT(nbttagcompound);
	  }
	  
	  public void readCustomNBT(NBTTagCompound nbttagcompound) {}
	  
	  public void writeToNBT(NBTTagCompound nbttagcompound)
	  {
	    super.writeToNBT(nbttagcompound);
	    writeCustomNBT(nbttagcompound);
	  }
	  
	  public void writeCustomNBT(NBTTagCompound nbttagcompound) {}
	  
	  public Packet getDescriptionPacket() {
	    NBTTagCompound nbttagcompound = new NBTTagCompound();
	    writeCustomNBT(nbttagcompound);
	    return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, -999, nbttagcompound);
	  }
	  
	  public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
	    super.onDataPacket(net, pkt);
	    readCustomNBT(pkt.data);
	  }
	  
	  public void onNeighborBlockChange() {}
	  
	  public void onNeighborTileChange(int tileX, int tileY, int tileZ) {}

}
