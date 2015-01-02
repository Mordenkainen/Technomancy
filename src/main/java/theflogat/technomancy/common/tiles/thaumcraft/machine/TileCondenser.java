package theflogat.technomancy.common.tiles.thaumcraft.machine;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.aspects.IEssentiaTransport;
import theflogat.technomancy.common.tiles.base.TileMachineBase;
import theflogat.technomancy.lib.Rate;

public class TileCondenser extends TileMachineBase implements IEssentiaTransport, IAspectSource {

	public static final Aspect aspect = Aspect.ENERGY;
	
	public int amount = 0;
	public int maxAmount = 64;
	public static int cost = Rate.condenserCost;

	public TileCondenser() {
		super(Rate.condenserCost * 5);
	}
	
	@Override
	public void updateEntity() {
		if(energy >= cost && amount < maxAmount) {
			extractEnergy(cost, false);
			amount++;
		}
	}

	@Override
	public void readCustomNBT(NBTTagCompound compound)  {
		amount = compound.getShort("Amount");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound compound)  {
		compound.setShort("Amount", (short)amount);
	}

	@Override
	public AspectList getAspects()  {
		AspectList al = new AspectList();
		if (amount > 0) {
			al.add(aspect, amount);
		}
		return al;
	}

	@Override
	public int addToContainer(Aspect tag, int amount)  {
		return 0;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount)  {
		if(tag==aspect && amount<=this.amount){
			this.amount -= amount;
			return true;
		}
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		if(ot.getAspects().length==1 && ot.getAspects()[0]==aspect && ot.getAmount(aspect)<=amount){
			amount -= ot.getAmount(aspect);
			return true;
		}
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amt) {
		if (tag==aspect && amt<=amount) {
			return true;
		}
		return false;
	}

	@Override
	public int containerContains(Aspect tag) {
		return tag==aspect ? amount : 0;
	}

	@Override
	public boolean isConnectable(ForgeDirection face) {
		return face==ForgeDirection.DOWN || face==ForgeDirection.UP;
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return false;
	}

	@Override
	public boolean canOutputTo(ForgeDirection face) {
		return face==ForgeDirection.DOWN || face==ForgeDirection.UP;
	}

	@Override
	public void setSuction(Aspect aspect, int amount) {}

	@Override
	public int takeEssentia(Aspect aspect, int amount, ForgeDirection dir) {
		return takeFromContainer(aspect, amount) ? amount : 0;
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
	public void setAspects(AspectList aspects) {}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return tag==aspect;
	}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		return null;
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		return 0;
	}

	@Override
	public int addEssentia(Aspect aspect, int amount, ForgeDirection dir) {
		return 0;
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection face) {
		return aspect;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		return isConnectable(face) ? amount : 0;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		return ot.getAspects().length==1 && ot.getAspects()[0]==aspect && amount>0;
	}
}