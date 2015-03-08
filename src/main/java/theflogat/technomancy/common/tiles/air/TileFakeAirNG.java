package theflogat.technomancy.common.tiles.air;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.wands.IWandable;
import theflogat.technomancy.common.blocks.machines.BlockNodeGenerator;
import theflogat.technomancy.common.tiles.IUpgradable;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.util.Coords;
import theflogat.technomancy.util.Loc;

public class TileFakeAirNG extends TileFakeAirCore implements IEnergyHandler, IEssentiaTransport, IAspectContainer, IWandable, IUpgradable {
	
	@Override
	public void updateEntity() {
		if(!(worldObj.getBlock(x, y, z) instanceof BlockNodeGenerator)){
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}else{
			TileNodeGenerator tile = (TileNodeGenerator) worldObj.getTileEntity(x, y, z);
			fill(tile);
		}
	}
	
	public void fill(TileNodeGenerator tile){
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if (dir != ForgeDirection.getOrientation(tile.facing) && dir != ForgeDirection.DOWN) {
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
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).getMaxEnergyStored();
	}

	@Override
	public boolean toggleBoost() {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).toggleBoost();
	}

	@Override
	public boolean getBoost() {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).getBoost();
	}

	@Override
	public void setBoost(boolean newBoost) {
		((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).setBoost(newBoost);
	}

	@Override
	public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md) {
		return ((TileNodeGenerator)worldObj.getTileEntity(this.x, this.y, this.z)).onWandRightClick(world, wandstack, player, this.x, this.y, this.z, side, md);
	}

	@Override
	public ItemStack onWandRightClick(World world, ItemStack wandstack, EntityPlayer player) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).onWandRightClick(world, wandstack, player);
	}

	@Override
	public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {
		((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).onUsingWandTick(wandstack, player, count);
	}

	@Override
	public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {
		((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).onWandStoppedUsing(wandstack, world, player, count);
	}

	@Override
	public AspectList getAspects() {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).getAspects();
	}

	@Override
	public void setAspects(AspectList aspects) {
		((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).setAspects(aspects);
	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).doesContainerAccept(tag);
	}

	@Override
	public int addToContainer(Aspect tag, int amount) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).addToContainer(tag, amount);
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).takeFromContainer(tag, amount);
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).takeFromContainer(ot);
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).doesContainerContainAmount(tag, amount);
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).doesContainerContain(ot);
	}

	@Override
	public int containerContains(Aspect tag) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).containerContains(tag);
	}

	@Override
	public boolean isConnectable(ForgeDirection face) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).isConnectable(face);
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).canInputFrom(face);
	}

	@Override
	public boolean canOutputTo(ForgeDirection face) {
		return false;
	}

	@Override
	public void setSuction(Aspect aspect, int amount) {
		((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).setSuction(aspect, amount);
	}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).getSuctionType(face);
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).getSuctionAmount(face);
	}

	@Override
	public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).takeEssentia(aspect, amount, face);
	}

	@Override
	public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).addEssentia(aspect, amount, face);
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection face) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).getEssentiaType(face);
	}

	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).getEssentiaAmount(face);
	}

	@Override
	public int getMinimumSuction() {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).getMinimumSuction();
	}

	@Override
	public boolean renderExtendedTube() {
		return ((TileNodeGenerator)worldObj.getTileEntity(x, y, z)).renderExtendedTube();
	}

}
