package theflogat.technomancy.common.tiles.thaumcraft.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;
import theflogat.technomancy.lib.compat.Thaumcraft;

public class TileEssentiaReservoir extends TileTechnomancy implements IEssentiaTransport, IAspectContainer{

	private static final int maxAmount = 256;

	public Aspect suction;
	Aspect as = null;
	int amount = 0;

	@Override
	public void updateEntity() {
		if(as!=null){
			suction = as;
		}
		fill();
	}

	public void fill() {
		if(amount<maxAmount){
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				TileEntity te = Thaumcraft.getConnectableTile(worldObj, xCoord, yCoord, zCoord, dir);
				if (te != null) {
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

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		amount = comp.getInteger("amount");
		as = Aspect.getAspect(comp.getString("as"));
		suction = Aspect.getAspect(comp.getString("suction"));
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		comp.setInteger("amount", amount);
		if(as!=null){
			comp.setString("as", as.getTag());
		}
		if(suction!=null){
			comp.setString("suction", suction.getTag());
		}
	}

	@Override
	public boolean isConnectable(ForgeDirection face) {
		return true;
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return true;
	}

	@Override
	public boolean canOutputTo(ForgeDirection face) {
		return true;
	}

	@Override
	public void setSuction(Aspect aspect, int amount) {

	}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		return suction;
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		return 128;
	}

	@Override
	public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
		return takeFromContainer(aspect, amount) ? 0 : amount;
	}

	@Override
	public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
		return addToContainer(aspect, amount);
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection face) {
		return as;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		return amount;
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
	public AspectList getAspects() {
		if(as!=null && amount>0){
			AspectList al = new AspectList();
			al.add(as, amount);
			return al;
		}
		return null;
	}

	@Override
	public void setAspects(AspectList aspects) {

	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return as==null||as==tag;
	}

	@Override
	public int addToContainer(Aspect tag, int amt) {
		if (amt != 0 && (amount < maxAmount && tag == as || amount == 0)) {
			as = tag;
			int added = Math.min(amt, maxAmount - amount);
			amount += added;
			amt -= added;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		return amt;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		if(as!=null && tag==as && amount<=this.amount){
			this.amount -= amount;
			return true;
		}
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		return as!=null && tag==as && amount<=this.amount;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		return false;
	}

	@Override
	public int containerContains(Aspect tag) {
		return as!=null && tag==as ? amount : 0;
	}

}
