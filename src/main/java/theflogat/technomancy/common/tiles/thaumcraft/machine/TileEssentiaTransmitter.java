package theflogat.technomancy.common.tiles.thaumcraft.machine;

import java.util.Collections;
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
import theflogat.technomancy.common.tiles.base.TileCoilTransmitter;
import theflogat.technomancy.common.tiles.thaumcraft.util.AspectContainerEssentiaTransport;
import theflogat.technomancy.lib.Conf;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.util.helpers.WorldHelper;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileEssentiaTransmitter extends TileCoilTransmitter implements IEssentiaTransport{

	public Aspect aspectFilter = null;
	private boolean onSpecialBlock;
	
	@Override
	public void writeSyncData(NBTTagCompound comp) {
		super.writeSyncData(comp);
		if (aspectFilter != null) {
			comp.setString("AspectFilter", aspectFilter.getTag());
		}
	}
	
	@Override
	public void readSyncData(NBTTagCompound comp) {
		super.readSyncData(comp);
		aspectFilter = Aspect.getAspect(comp.getString("AspectFilter"));
	}
	
	@SuppressWarnings("null")
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
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
			if(te == null) {
				return;
			}
			
			IAspectContainer cont;
			if(te instanceof IAspectContainer) {
				cont = (IAspectContainer)te;
			} else {
				cont = new AspectContainerEssentiaTransport((IEssentiaTransport)te);
			}
			
			boolean flag = false;
			if(!boost){ 
				if(redstoneState) {
					redstoneState = false;
					flag = true;
				}
			} else {
				if(!Thaumcraft.isFull(cont)) {
					if(!redstoneState) {
						redstoneState = true;
						flag = true;
					}
				} else if (Thaumcraft.isFull(cont)) {
					if(redstoneState) {
						redstoneState = false;
						flag = true;
					}
				}
			}
			if(flag) {
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord));
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			
			if (set.canRun(this) && !sources.isEmpty()) {
				Collections.shuffle(sources);
				Iterator<ChunkCoordinates> sourceIter = sources.iterator();
				while (sourceIter.hasNext()) {
					ChunkCoordinates coords = sourceIter.next();
					TileEntity tile = worldObj.getTileEntity(coords.posX, coords.posY, coords.posZ);
					if(tile != null && !tile.isInvalid() && tile instanceof IAspectContainer) {
						IAspectContainer source = (IAspectContainer)tile;
						AspectList al = source.getAspects();
						if(al != null) {
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
						}
					}else{
						if(tile == null || (tile != null && !tile.isInvalid())) {
							sourceIter.remove();
						}
					}
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

	@SuppressWarnings("null")
	@Override
	public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
		if (onSpecialBlock && set.canRun(this) && !sources.isEmpty()) {
			Collections.shuffle(sources);
			Iterator<ChunkCoordinates> sourceIter = sources.iterator();
			boolean gotEssentia = false;
			while (sourceIter.hasNext() && !gotEssentia) {
				ChunkCoordinates coords = sourceIter.next();
				TileEntity tile = worldObj.getTileEntity(coords.posX, coords.posY, coords.posZ);
				if(tile!=null && !tile.isInvalid() && tile instanceof IAspectContainer) {
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
					if(tile == null || (tile != null && !tile.isInvalid())) {
						sourceIter.remove();
					}
				}
				
			}
		}
		return 0;
	}
	
	@Override
	public Type getType() {
		return Type.ESSENTIA;
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
	public boolean onWrenched(boolean sneaking) {
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
