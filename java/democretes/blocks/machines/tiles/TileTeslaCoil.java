package democretes.blocks.machines.tiles;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.aspects.IEssentiaTransport;
import democretes.Technomancy;
import democretes.blocks.TileTechnomancy;
import democretes.compat.Thaumcraft;
import democretes.handlers.ConfigHandler;

public class TileTeslaCoil extends TileTechnomancy implements IAspectSource, IEssentiaTransport {
	
	public int maxAmount = 2;
	AspectList aspects = new AspectList();
	public Aspect aspectFilter = null;
	public ArrayList<ChunkCoordinates> sources = new ArrayList();
	public boolean link = false;
	public int facing = 0;
	int color;
	
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		compound.setInteger("Color", this.color);
		compound.setInteger("Size", this.sources.size());
		if (this.aspectFilter != null) {
			compound.setString("AspectFilter", this.aspectFilter.getTag());
		}
		compound.setByte("facing", (byte)this.facing);
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
		this.color = compound.getInteger("Color");
		this.aspectFilter = Aspect.getAspect(compound.getString("AspectFilter"));
		this.facing = compound.getByte("facing");
		int xx;
		int yy;
		int zz;	
		int size = compound.getInteger("Size");
		for(int i = 0; i < size; i ++) {
			xx = compound.getInteger("xChoord" + i);		
			yy = compound.getInteger("yChoord" + i);		
			zz = compound.getInteger("zChoord" + i);
			this.sources.add(new ChunkCoordinates(xx, yy, zz));
		}		
	
	}

	
	@Override
	public void updateEntity() {
		if(!this.sources.isEmpty()) {
			for(int i = 0; i < sources.size(); i++) {
				ChunkCoordinates chords = sources.get(i);
				TileEntity tile = this.worldObj.getBlockTileEntity(chords.posX, chords.posY, chords.posZ);
				if(tile != null) {
					IAspectSource source = (IAspectSource)tile;
					AspectList al = source.getAspects();				
					for(int ii = 0; ii < al.size(); ii++) {
						Aspect aspect = al.getAspects()[ii];
						if(aspect != null) {
							if(this.aspectFilter == null || aspect == this.aspectFilter) {
								this.color = aspect.getColor();
								while(this.aspects.getAmount(aspect) < maxAmount) {							
									if(source.takeFromContainer(aspect, 1)) {
										this.addToContainer(aspect, 1);
										if(ConfigHandler.fancy) {
											if(this != null && tile != null && color > 0) {
												Technomancy.proxy.essentiaTrail(this.worldObj, (double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D, (double)(tile.xCoord) + 0.5D, (double)(tile.yCoord) + 0.5D, (double)(tile.zCoord) + 0.5D, color);
											}
										}
									}else{
										break;									
									}
								}
							}
						}else{
							break;
						}						
					}
				}else{
					sources.remove(i);
				}
			}
		}
		fill();
	}
	
	void fill() {
		TileEntity te = Thaumcraft.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.getOrientation(this.facing));
		if (te != null) {
			if(te instanceof TileTeslaCoil) {
				return;
			}
			int opposite = ForgeDirection.OPPOSITES[this.facing];
			IEssentiaTransport ic = (IEssentiaTransport)te;
			if (!ic.canOutputTo(ForgeDirection.getOrientation(opposite))) {
				return;
			}
			Aspect ta = null;
			if ((ic.getEssentiaAmount(ForgeDirection.getOrientation(opposite)) > 0) && 
					(ic.getSuctionAmount(ForgeDirection.getOrientation(opposite)) < getSuctionAmount(ForgeDirection.getOrientation(opposite))) && (getSuctionAmount(ForgeDirection.getOrientation(opposite)) >= ic.getMinimumSuction())) {
				ta = ic.getEssentiaType(ForgeDirection.getOrientation(opposite));
			}
			if ((ta != null) && (ic.getSuctionAmount(ForgeDirection.getOrientation(opposite)) < getSuctionAmount(ForgeDirection.getOrientation(opposite)))) {
				addToContainer(ta, ic.takeVis(ta, 1));
			}
		}
	}
	
	@Override
	public AspectList getAspects()  {
	    return this.aspects;
	}

	@Override
	public int addToContainer(Aspect tag, int amount)  {
		if (amount != 1) {
			return amount;
		}
		if(this.aspects.getAmount(tag) < 8) {
			this.aspects.add(tag, amount);				
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			return 0;
		}
		return amount;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount)  {
		if (this.aspects.getAmount(tag) >= amount){
			this.aspects.remove(tag, amount);
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			return true;
		}
		return false;		
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amt) {
		return this.aspects.getAmount(tag) >= amt;
	}
	
	@Override
	public int containerContains(Aspect tag) {
		return 0;
	}

	@Override
	public boolean isConnectable(ForgeDirection face) {
		return face == ForgeDirection.getOrientation(this.facing);
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return face == ForgeDirection.getOrientation(this.facing);
	}

	@Override
	public boolean canOutputTo(ForgeDirection face) {
		return face == ForgeDirection.getOrientation(this.facing);
	}

	@Override
	public void setSuction(Aspect aspect, int amount) {	}

	@Override
	public int takeVis(Aspect aspect, int amount) {
		  return takeFromContainer(aspect, amount) ? amount : 0;
	}

	@Override
	public int getMinimumSuction() {
		  return 16;
	}

	@Override
	public boolean renderExtendedTube() {
		return true;
	}	

	@Override
	public void setAspects(AspectList aspects) {	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return false;
	}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		return null;
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		return 16;
	}

	@Override
	public int addVis(Aspect aspect, int amount) {
		return amount - addToContainer(aspect, amount);
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection face) {
		return this.aspects.size() > 0 ? this.aspects.getAspects()[this.worldObj.rand.nextInt(this.aspects.getAspects().length)] : null;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		return this.aspects.visSize();
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		return false;
	}

}
