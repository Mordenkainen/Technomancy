package theflogat.technomancy.common.tiles.thaumcraft.machine;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import theflogat.technomancy.lib.compat.Thaumcraft;

public class TileTCProcessor extends TileProcessorBase implements IAspectContainer, IEssentiaTransport {
	
	public int amount = 0;
	public int maxAmount = 64;
	
	public TileTCProcessor() {
		super(0);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		perform();
	}
	
	@Override
	protected boolean getFuel(ItemStack items, int multiplier, int reprocess) {
		int cost = Math.max(1, multiplier + 2*reprocess);
		if(cost > amount) {
				return false;
		}
		takeFromContainer(Aspect.FIRE, cost);
		return true;
	}
	
	protected void perform() {
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity te = Thaumcraft.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, dir);
			if (te != null) {
				IEssentiaTransport ic = (IEssentiaTransport)te;
				Aspect ta = ic.getEssentiaType(dir.getOpposite());
				if (ta == Aspect.FIRE && ic.getEssentiaAmount(dir.getOpposite()) > 0 && ic.getSuctionAmount(dir.getOpposite()) <
						getSuctionAmount(null) && getSuctionAmount(null) >= ic.getMinimumSuction()) {
					addToContainer(ta, ic.takeEssentia(ta, 1, dir.getOpposite()));
				}
			}
		}
	}
	
	@Override
	public void writeSyncData(NBTTagCompound compound) {
		super.writeSyncData(compound);
		compound.setShort("Amount", (short)amount);
	}
	
	@Override
	public void readSyncData(NBTTagCompound compound) {
		super.readSyncData(compound);
		amount = compound.getShort("Amount");
	}

	@Override
	public AspectList getAspects() {
	    AspectList al = new AspectList();
	    if (amount > 0) {
	    	al.add(Aspect.FIRE, amount);
	    }
	    return al;
	}

	@Override
	public int addToContainer(Aspect tag, int amount) {
		if (amount != 0 || tag == Aspect.FIRE) {
			if (this.amount < maxAmount) {
			    int added = Math.min(amount, maxAmount - this.amount);
			    this.amount += added;
			    amount -= added;
			    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}		
		return amount;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount)  {
		if(this.amount >= amount && tag == Aspect.FIRE) {
			this.amount -= amount;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			return true;			
		}
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		if(doesContainerContain(ot)) {
			this.amount -= ot.getAmount(Aspect.FIRE);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			return true;			
		}
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amt) {
		if (amount >= amt && tag == Aspect.FIRE) {
			return true;
		}
		return false;
	}
	
	@Override
	public int containerContains(Aspect tag) {
		return tag == Aspect.FIRE ? amount : 0;
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
		return false;
	}

	@Override
	public void setSuction(Aspect aspect, int amount) {}

	@Override
	public int takeEssentia(Aspect aspect, int amount, ForgeDirection dir) {
		return takeFromContainer(aspect, amount) ? amount : 0;
	}

	@Override
	public int getMinimumSuction() {
		return 128;
	}

	@Override
	public boolean renderExtendedTube() {
		return true;
	}

	@Override
	public void setAspects(AspectList aspects) {}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return tag == Aspect.FIRE;
	}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		return Aspect.FIRE;
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		if (amount < maxAmount){
			return 128;
		}
		return 0;
	}

	@Override
	public int addEssentia(Aspect aspect, int amount, ForgeDirection dir) {
		return addToContainer(aspect, amount);
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection face) {
		return amount > 0 ? Aspect.FIRE : null;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		return amount;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		return ot.size() == 1 && ot.aspects.containsKey(Aspect.FIRE) && amount >= ot.getAmount(Aspect.FIRE);
	}

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}
}