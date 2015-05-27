package theflogat.technomancy.common.tiles.air;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.wands.IWandable;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockNodeGenerator;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.base.IUpgradable;
import theflogat.technomancy.common.tiles.base.IWrenchable;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.util.Coords;

public class TileFakeAirNG extends TileFakeAirCore implements IEnergyHandler, IEssentiaTransport, IAspectContainer, IWandable, IUpgradable, IRedstoneSensitive, IWrenchable {
	
	@Override
	public void updateEntity() {
		if(!(worldObj.getBlock(x, y, z) instanceof BlockNodeGenerator)){
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}else{
			TileNodeGenerator tile = getTE();
			if(tile != null) {
				fill(tile);
			}
		}
	}
	
	public void fill(TileNodeGenerator tile){
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if (dir != ForgeDirection.getOrientation(tile.facing)) {
				TileEntity te = Thaumcraft.getConnectableTile(worldObj, xCoord, yCoord, zCoord, dir);
				if (te != null && !(te instanceof TileNodeGenerator) && !(te instanceof TileFakeAirNG)) {
					IEssentiaTransport ic = (IEssentiaTransport)te;
					Aspect ta = ic.getEssentiaType(dir.getOpposite());
					if (ic.getEssentiaAmount(dir.getOpposite()) > 0 && ic.getSuctionAmount(dir.getOpposite()) < getSuctionAmount(null) &&
							getSuctionAmount(null) >= ic.getMinimumSuction()) {
						addToContainer(ta, ic.takeEssentia(ta, 1, dir.getOpposite()));
					}
				}
			}
		}
	}
	
	public Coords getMain(){
		return new Coords(x, y, z, worldObj);
	}
	
	public void addMain(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		TileNodeGenerator te = getTE();
		if(te != null) {
			return true;
		}
		return false;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		TileNodeGenerator te = getTE();
		return te != null ? te.receiveEnergy(maxReceive, simulate) : 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		TileNodeGenerator te = getTE();
		return te != null ? te.extractEnergy(maxExtract, simulate) : 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		TileNodeGenerator te = getTE();
		return te != null ? te.getEnergyStored() : 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		TileNodeGenerator te = getTE();
		return te != null ? te.getMaxEnergyStored() : 0;
	}

	@Override
	public boolean toggleBoost() {
		TileNodeGenerator te = getTE();
		return te != null ? te.toggleBoost() : false;
	}

	@Override
	public boolean getBoost() {
		TileNodeGenerator te = getTE();
		return te != null ? te.getBoost() : false;
	}

	@Override
	public void setBoost(boolean newBoost) {
		TileNodeGenerator te = getTE();
		if(te != null) {
			te.setBoost(newBoost);
		}
	}

	@Override
	public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md) {
		TileNodeGenerator te = getTE();
		return te != null ? te.onWandRightClick(world, wandstack, player, this.x, this.y, this.z, side, md) : -1;
	}

	@Override
	public ItemStack onWandRightClick(World world, ItemStack wandstack, EntityPlayer player) {
		TileNodeGenerator te = getTE();
		return te != null ? te.onWandRightClick(world, wandstack, player) : wandstack;
	}

	@Override
	public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {
		TileNodeGenerator te = getTE();
		if(te != null) {
			te.onUsingWandTick(wandstack, player, count);
		}
	}

	@Override
	public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {
		TileNodeGenerator te = getTE();
		if(te != null) {
			te.onWandStoppedUsing(wandstack, world, player, count);
		}
	}

	@Override
	public AspectList getAspects() {
		TileNodeGenerator te = getTE();
		return te != null ? te.getAspects() : new AspectList();
	}

	@Override
	public void setAspects(AspectList aspects) {
		TileNodeGenerator te = getTE();
		if(te != null) {
			te.setAspects(aspects);
		}
	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		TileNodeGenerator te = getTE();
		return te != null ? te.doesContainerAccept(tag) : false;
	}

	@Override
	public int addToContainer(Aspect tag, int amount) {
		TileNodeGenerator te = getTE();
		return te != null ? te.addToContainer(tag, amount): amount;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		TileNodeGenerator te = getTE();
		return te != null ? te.takeFromContainer(tag, amount) : false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		TileNodeGenerator te = getTE();
		return te != null ? te.takeFromContainer(ot) : false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		TileNodeGenerator te = getTE();
		return te != null ? te.doesContainerContainAmount(tag, amount) : false;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		TileNodeGenerator te = getTE();
		return te != null ? te.doesContainerContain(ot) : false;
	}

	@Override
	public int containerContains(Aspect tag) {
		TileNodeGenerator te = getTE();
		return te != null ? te.containerContains(tag) : 0;
	}

	@Override
	public boolean isConnectable(ForgeDirection face) {
		TileNodeGenerator te = getTE();
		return te != null ? te.isConnectable(face) : false;
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		TileNodeGenerator te = getTE();
		return te != null ? te.canInputFrom(face) : false;
	}

	@Override
	public boolean canOutputTo(ForgeDirection face) {
		TileNodeGenerator te = getTE();
		return te != null ? te.canOutputTo(face) : false;
	}

	@Override
	public void setSuction(Aspect aspect, int amount) {
		TileNodeGenerator te = getTE();
		if(te != null) {
			te.setSuction(aspect, amount);
		}
	}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		TileNodeGenerator te = getTE();
		return te != null ? te.getSuctionType(face) : null;
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		TileNodeGenerator te = getTE();
		return te != null ? te.getSuctionAmount(face) : 0;
	}

	@Override
	public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
		TileNodeGenerator te = getTE();
		return te != null ? te.takeEssentia(aspect, amount, face) : 0;
	}

	@Override
	public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
		TileNodeGenerator te = getTE();
		return te != null ? te.addEssentia(aspect, amount, face) : 0;
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection face) {
		TileNodeGenerator te = getTE();
		return te != null ? te.getEssentiaType(face) : null;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		TileNodeGenerator te = getTE();
		return te != null ? te.getEssentiaAmount(face) : 0;
	}

	@Override
	public int getMinimumSuction() {
		TileNodeGenerator te = getTE();
		return te != null ? te.getMinimumSuction() : 0;
	}

	@Override
	public boolean renderExtendedTube() {
		TileNodeGenerator te = getTE();
		return te != null ? te.renderExtendedTube() : false;
	}

	@Override
	public RedstoneSet getCurrentSetting() {
		TileNodeGenerator te = getTE();
		return te != null ? te.getCurrentSetting() : RedstoneSet.LOW;
	}

	@Override
	public void setNewSetting(RedstoneSet newSet) {
		TileNodeGenerator te = getTE();
		if(te != null) {
			te.setNewSetting(newSet);
		}
	}

	@Override
	public boolean isModified() {
		TileNodeGenerator te = getTE();
		return te != null ? te.isModified() : false;
	}
	
	@Override
	public boolean canBeModified() {
		TileNodeGenerator te = getTE();
		return te != null ? te.canBeModified() : false;
	}
	
	@Override
	public boolean onWrenched(boolean sneaking) {
		TileNodeGenerator te = getTE();
		return te != null ? te.onWrenched(sneaking) : false;
	}
	
	@Override
	public String getInfo() {
		TileNodeGenerator te = getTE();
		return te != null ? te.getInfo() : ""; 
	}
	
	private TileNodeGenerator getTE() {
		TileEntity tile = worldObj.getTileEntity(x, y, z);
		return tile instanceof TileNodeGenerator ? (TileNodeGenerator)tile : null;
	}
}
