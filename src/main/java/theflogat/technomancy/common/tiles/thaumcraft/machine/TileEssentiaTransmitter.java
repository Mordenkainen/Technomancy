package theflogat.technomancy.common.tiles.thaumcraft.machine;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXEssentiaSource;
import theflogat.technomancy.common.tiles.base.ICouplable;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.base.IWrenchable;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;
import theflogat.technomancy.common.tiles.thaumcraft.util.AspectContainerEssentiaTransport;
import theflogat.technomancy.lib.Conf;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.util.WorldHelper;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileEssentiaTransmitter extends TileTechnomancy implements IEssentiaTransport, ICouplable, IRedstoneSensitive, IWrenchable{

	public Aspect aspectFilter = null;
	public ArrayList<ChunkCoordinates> sources = new ArrayList<ChunkCoordinates>();
	public int facing = 0;
	public RedstoneSet set = RedstoneSet.LOW;
	private boolean onSpecialBlock;

	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		if (aspectFilter != null) {
			compound.setString("AspectFilter", aspectFilter.getTag());
		}
		compound.setByte("facing", (byte)facing);
		int sourceCount = 0;
		for(int i = 0; i < sources.size(); i++) {
			if(sources.get(i) != null) {
				compound.setInteger("xcoord" + sourceCount, sources.get(i).posX);
				compound.setInteger("ycoord" + sourceCount, sources.get(i).posY);
				compound.setInteger("zcoord" + sourceCount, sources.get(i).posZ);
				sourceCount++;
			}		
		}
		compound.setInteger("size", sourceCount);
	}

	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		aspectFilter = Aspect.getAspect(compound.getString("AspectFilter"));
		facing = compound.getByte("facing");
		int size = compound.getInteger("size");
		for(int i = 0; i < size; i ++) {
			int xx = compound.getInteger("xcoord" + i);		
			int yy = compound.getInteger("ycoord" + i);		
			int zz = compound.getInteger("zcoord" + i);
			this.sources.add(new ChunkCoordinates(xx, yy, zz));
		}
	}

	@Override
	public void updateEntity() {
		Block connectedBlock = worldObj.getBlock(xCoord + ForgeDirection.getOrientation(facing).offsetX, 
				yCoord + ForgeDirection.getOrientation(facing).offsetY, zCoord + ForgeDirection.getOrientation(facing).offsetZ);
		int blockMeta = worldObj.getBlockMetadata(xCoord + ForgeDirection.getOrientation(facing).offsetX, 
				yCoord + ForgeDirection.getOrientation(facing).offsetY, zCoord + ForgeDirection.getOrientation(facing).offsetZ);
		if(connectedBlock == GameRegistry.findBlock("Thaumcraft", "blockMetalDevice") && 
				(blockMeta == 8 || blockMeta == 10 || blockMeta == 11 || blockMeta == 13)) {
			onSpecialBlock = true;
			return;
		}
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
										if (this.xCoord - tile.xCoord <= Byte.MAX_VALUE && this.yCoord - tile.yCoord <= Byte.MAX_VALUE && this.zCoord - tile.zCoord <= Byte.MAX_VALUE) {
											PacketHandler.INSTANCE.sendToAllAround(new PacketFXEssentiaSource(this.xCoord, this.yCoord+1, this.zCoord, (byte)(this.xCoord - tile.xCoord),
												(byte)(this.yCoord - tile.yCoord), (byte)(this.zCoord - tile.zCoord), aspect.getColor()), new NetworkRegistry.TargetPoint(
												tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord, 32.0D));
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
	
	@Override
	public boolean isConnectable(ForgeDirection face) {
		return onSpecialBlock && face == ForgeDirection.getOrientation(facing);
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return false;
	}

	@Override
	public boolean canOutputTo(ForgeDirection face) {
		return onSpecialBlock && face == ForgeDirection.getOrientation(facing);
	}

	@Override
	public void setSuction(Aspect aspect, int amount) {}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		return null;
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		return 0;
	}

	@Override
	public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
		if (onSpecialBlock && set.canRun(this) && !sources.isEmpty()) {
			Iterator<ChunkCoordinates> sourceIter = sources.iterator();
			boolean gotEssentia = false;
			while (sourceIter.hasNext() && !gotEssentia) {
				ChunkCoordinates coords = sourceIter.next();
				TileEntity tile = worldObj.getTileEntity(coords.posX, coords.posY, coords.posZ);
				if(tile!=null && tile instanceof IAspectContainer) {
					IAspectContainer source = (IAspectContainer)tile;
					if(source.doesContainerContainAmount(aspect, amount)) {
						gotEssentia = source.takeFromContainer(aspect, amount);
						if(gotEssentia) {
							if(Conf.fancy) {
								if (this.xCoord - tile.xCoord <= Byte.MAX_VALUE && this.yCoord - tile.yCoord <= Byte.MAX_VALUE && this.zCoord - tile.zCoord <= Byte.MAX_VALUE) {
									PacketHandler.INSTANCE.sendToAllAround(new PacketFXEssentiaSource(this.xCoord, this.yCoord+1, this.zCoord, (byte)(this.xCoord - tile.xCoord),
										(byte)(this.yCoord - tile.yCoord), (byte)(this.zCoord - tile.zCoord), aspect.getColor()), new NetworkRegistry.TargetPoint(
										tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord, 32.0D));
								}
							}
							return amount;
						}
					}
				}else{
					sourceIter.remove();
				}
				
			}
		}
		return 0;
	}

	@Override
	public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
		return 0;
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection face) {
		return null;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		return 1;
	}

	@Override
	public int getMinimumSuction() {
		return 0;
	}

	@Override
	public boolean renderExtendedTube() {
		return true;
	}

	@Override
	public Type getType() {
		return Type.ESSENTIA;
	}

	@Override
	public void addPos(ChunkCoordinates coords) {
		sources.add(coords);
	}

	@Override
	public void clear() {
		sources.clear();
	}

	@Override
	public RedstoneSet getCurrentSetting() {
		return set;
	}

	@Override
	public void setNewSetting(RedstoneSet newSet) {
		set = newSet;
	}
	
	@Override
	public boolean onWrenched() {
		for (int i = facing + 1; i < facing + 6; i++){
			TileEntity tile = WorldHelper.getAdjacentTileEntity(this, (byte) (i % 6));
			if (tile instanceof IAspectContainer) {
				facing = (byte) (i % 6);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord));
				return true;
			}
		}
		return false;
	}
}