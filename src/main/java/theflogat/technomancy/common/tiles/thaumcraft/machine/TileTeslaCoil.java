package theflogat.technomancy.common.tiles.thaumcraft.machine;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;
import theflogat.technomancy.common.tiles.thaumcraft.util.AspectContainerEssentiaTransport;
import theflogat.technomancy.handlers.compat.Thaumcraft;
import theflogat.technomancy.lib.Conf;

public class TileTeslaCoil extends TileTechnomancy {

	public Aspect aspectFilter = null;
	public ArrayList<ChunkCoordinates> sources = new ArrayList<ChunkCoordinates>();
	public int facing = 0;


	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		compound.setInteger("Size", sources.size());
		if (aspectFilter != null) {
			compound.setString("AspectFilter", aspectFilter.getTag());
		}
		compound.setByte("facing", (byte)facing);
		for(int i = 0; i < sources.size(); i++) {
			if(sources.get(i) != null) {
				compound.setInteger("xChoord" + i, sources.get(i).posX);
				compound.setInteger("yChoord" + i, sources.get(i).posY);
				compound.setInteger("zChoord" + i, sources.get(i).posZ);
			}			
		}
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
		if (te != null && !sources.isEmpty()) {
			//ForgeDirection opp = ForgeDirection.VALID_DIRECTIONS[facing].getOpposite();
			IAspectContainer cont;
			if(te instanceof IAspectContainer){
				cont = (IAspectContainer)te;
			}else{
				cont = new AspectContainerEssentiaTransport((IEssentiaTransport) te);
			}
			for(int i = 0; i < sources.size(); i++) {
				ChunkCoordinates coords = sources.get(i);
				TileEntity tile = worldObj.getTileEntity(coords.posX, coords.posY, coords.posZ);
				if(tile != null && tile instanceof IAspectContainer) {
					IAspectContainer source = (IAspectContainer)tile;
					AspectList al = source.getAspects();				
					for(int ii = 0; ii < al.size(); ii++) {
						Aspect aspect = al.getAspects()[ii];
						if(aspect != null && (aspectFilter == null || aspect == aspectFilter) && cont.doesContainerAccept(aspect)) {
							int color = aspect.getColor();
							if(cont.addToContainer(aspect, 1) == 0) {
								if(source.takeFromContainer(aspect, 1)) {
									if(Conf.fancy) {
										if(this!=null && tile!=null && color>0 && source!=null) {
											Technomancy.proxy.essentiaTrail(worldObj, xCoord + 0.5D, yCoord + 0.5D,
													zCoord + 0.5D, (tile.xCoord) + 0.5D, (tile.yCoord) + 0.5D,
													(tile.zCoord) + 0.5D, color);
										}
									}
								} else {
									cont.takeFromContainer(aspect, 1);
								}
							}
						}						
					}
				}else{
					sources.remove(i);
				}
			}
		}
		//		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
}