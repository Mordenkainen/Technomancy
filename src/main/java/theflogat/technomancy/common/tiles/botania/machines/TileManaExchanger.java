package theflogat.technomancy.common.tiles.botania.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.base.IWrenchable;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;
import theflogat.technomancy.lib.handlers.Rate;
import vazkii.botania.common.block.tile.mana.TilePool;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;

public class TileManaExchanger extends TileTechnomancy implements IFluidHandler, IEnergyReceiver, IRedstoneSensitive, IWrenchable{
	public FluidTank tank = new FluidTank(1000);
	public RedstoneSet set = RedstoneSet.LOW;
	public boolean mode;
	public boolean active;
	public EnergyStorage storage = new EnergyStorage(Rate.exchangerCost * 10);

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		tank = new FluidTank(1000);
		tank.readFromNBT(comp);
		set = RedstoneSet.load(comp);
		mode = comp.getBoolean("Mode");
		storage.readFromNBT(comp);
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		tank.writeToNBT(comp);
		set.save(comp);
		comp.setBoolean("Mode", mode);
		storage.writeToNBT(comp);
	}
	
	@Override
	public void updateEntity() {
		if (!set.canRun(this)) {
			active = false;
			return;
		} else {
			active = true;
		}
		TileEntity tile = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
		if(tile instanceof TilePool){
			if (worldObj.isRemote) return;
			TilePool pool = (TilePool)tile;
			if(storage.getEnergyStored() >= Rate.exchangerCost) {
				if(mode) {
					if(tank.getFluidAmount() > 0 && pool.getCurrentMana() <= pool.manaCap - 1000) {
						pool.recieveMana(1000);
						tank.drain(1, true);
						storage.extractEnergy(Rate.exchangerCost, false);
						worldObj.markBlockForUpdate(xCoord, yCoord + 1, zCoord);
					}
				} else {
					if(tank.getFluidAmount() < tank.getCapacity() && pool.getCurrentMana() >= 1000) {
						pool.recieveMana(-1000);
						tank.fill(new FluidStack(TMBlocks.manaFluid, 1), true);
						storage.extractEnergy(Rate.exchangerCost, false);
						worldObj.markBlockForUpdate(xCoord, yCoord + 1, zCoord);
					}
				}
			}
		} else {
			active = false;
		}
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] {this.tank.getInfo()};
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(resource.getFluid() != TMBlocks.manaFluid || from == ForgeDirection.UP || !mode) return 0;
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if(resource.getFluid() != TMBlocks.manaFluid) return null;
		return drain(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(from == ForgeDirection.UP || mode) return null;
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return from != ForgeDirection.UP && mode;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return from != ForgeDirection.UP && !mode;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from != ForgeDirection.UP;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
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
		mode = !mode;
		return true;
	}
}
