package theflogat.technomancy.common.tiles.thaumcraft.machine;

import java.util.ArrayList;
import java.util.Iterator;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;
import theflogat.technomancy.common.tiles.thaumcraft.util.AspectContainerEssentiaTransport;
import theflogat.technomancy.lib.Conf;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.util.RedstoneSet;

public class TileWirelessCoil extends TileTechnomancy {

	public Aspect aspectFilter = null;
	public ArrayList<ChunkCoordinates> sources = new ArrayList<ChunkCoordinates>();
	public int facing = 0;
	public RedstoneSet set = RedstoneSet.LOW;

	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		if (aspectFilter != null) {
			compound.setString("AspectFilter", aspectFilter.getTag());
		}
		compound.setByte("facing", (byte)facing);
		int sourceCount = 0;
		for(int i = 0; i < sources.size(); i++) {
			if(sources.get(i) != null) {
				compound.setInteger("xChoord" + sourceCount, sources.get(i).posX);
				compound.setInteger("yChoord" + sourceCount, sources.get(i).posY);
				compound.setInteger("zChoord" + sourceCount, sources.get(i).posZ);
				sourceCount++;
			}		
		}
		compound.setInteger("Size", sourceCount);
	}

	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		aspectFilter = Aspect.getAspect(compound.getString("AspectFilter"));
		facing = compound.getByte("facing");
		int size = compound.getInteger("Size");
		for(int i = 0; i < size; i ++) {
			int xx = compound.getInteger("xChoord" + i);		
			int yy = compound.getInteger("yChoord" + i);		
			int zz = compound.getInteger("zChoord" + i);
			this.sources.add(new ChunkCoordinates(xx, yy, zz));
		}
	}

	@Override
	public void updateEntity() {
		TileEntity te = Thaumcraft.getConnectableAsContainer(worldObj, xCoord, yCoord, zCoord, ForgeDirection.getOrientation(facing));
		if (set.canRun(this) && te != null && !sources.isEmpty()) {
			IAspectContainer cont;
			if(te instanceof IAspectContainer){
				cont = (IAspectContainer)te;
			}else{
				cont = new AspectContainerEssentiaTransport((IEssentiaTransport) te);
			}
			Iterator<ChunkCoordinates> sourceIter = sources.iterator();
			while (sourceIter.hasNext()) {
				ChunkCoordinates coords = sourceIter.next();
				TileEntity tile = worldObj.getTileEntity(coords.posX, coords.posY, coords.posZ);
				if(tile!=null && tile instanceof IAspectContainer) {
					IAspectContainer source = (IAspectContainer)tile;
					AspectList al = source.getAspects();
					if(al!=null)
						for(int i = 0; i < al.size(); i++) {
							Aspect aspect = al.getAspects()[i];
							if(aspect != null && (aspectFilter == null || aspect == aspectFilter) && cont.doesContainerAccept(aspect) && cont.addToContainer(aspect, 1) == 0) {
								if(source.takeFromContainer(aspect, 1)) {
									if(Conf.fancy) {
										try {
											if (this.xCoord - tile.xCoord <= Byte.MAX_VALUE && this.yCoord - tile.yCoord <= Byte.MAX_VALUE && this.zCoord - tile.zCoord <= Byte.MAX_VALUE) {
												Thaumcraft.PHInstance.sendToAllAround((IMessage)Thaumcraft.PacketFXEssentiaSourceConst.newInstance(
														this.xCoord, this.yCoord+1, this.zCoord, (byte)(this.xCoord - tile.xCoord),
														(byte)(this.yCoord - tile.yCoord), (byte)(this.zCoord - tile.zCoord), aspect.getColor()),
														new NetworkRegistry.TargetPoint(tile.getWorldObj().provider.dimensionId, tile.xCoord,
																tile.yCoord, tile.zCoord, 32.0D));
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								} else {
									cont.takeFromContainer(aspect, 1);
								}
							}
						}
				}else{
					sourceIter.remove();
				}
			}
		}
	}
}