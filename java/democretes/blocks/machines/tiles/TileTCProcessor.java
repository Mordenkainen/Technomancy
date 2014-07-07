package democretes.blocks.machines.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import democretes.compat.Thaumcraft;

public class TileTCProcessor extends TileProcessorBase implements IAspectContainer, IEssentiaTransport {
	

	public Aspect aspect;
	public int amount = 0;
	public int maxAmount = 64;
	public Aspect suction = Aspect.FIRE;
	
	public TileTCProcessor() {
		this.tagCompound = "Thaumcraft";
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		fill();
	}
	
	@Override
	boolean canProcess() {
		if(this.amount > 4) {
			return true;
		}
		return false;		
	}
	
	@Override
	void getFuel() {
		this.takeFromContainer(this.aspect, (1 * (this.multiplier + 1)));
	}
	
	void fill() {
		TileEntity te = null;
		IEssentiaTransport ic = null;
		for (int y = 0; y <= 1; y++) {
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if ((dir != ForgeDirection.getOrientation(this.facing)) && (dir != ForgeDirection.DOWN) && ((y != 0) || (dir != ForgeDirection.UP))) {
					te = Thaumcraft.getConnectableTile(this.worldObj, this.xCoord, this.yCoord + y, this.zCoord, dir);
					if (te != null) {
						ic = (IEssentiaTransport)te;
						Aspect ta = ic.getEssentiaType(dir.getOpposite());
						if ((ic.getEssentiaAmount(dir.getOpposite()) > 0) && (ic.getSuctionAmount(dir.getOpposite()) < getSuctionAmount(null)) && (getSuctionAmount(null) >= ic.getMinimumSuction())) {
							addToContainer(ta, ic.takeVis(ta, 1));
							return;
						}
					}
				}
			}
		}
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound)  {
		if (this.aspect != null) {
			compound.setString("Aspect", this.aspect.getTag());
		}
		compound.setShort("Amount", (short)this.amount);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound compound)  {
		this.aspect = Aspect.getAspect(compound.getString("Aspect"));
		this.amount = compound.getShort("Amount");
	}

	@Override
	public AspectList getAspects()  {
	    AspectList al = new AspectList();
	    if ((this.aspect != null) && (this.amount > 0)) {
	    	al.add(this.aspect, this.amount);
	    }
	    return al;
	}

	@Override
	public int addToContainer(Aspect tag, int amount)  {
		if (amount == 0) {
			return amount;
		}
		if (((this.amount < this.maxAmount) && (tag == this.aspect)) || (this.amount == 0)) {
			this.aspect = tag;
		    int added = Math.min(amount, this.maxAmount - this.amount);
		    this.amount += added;
		    amount -= added;
		}
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		return amount;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount)  {
		if(this.amount >= amount && this.aspect == tag) {
			this.amount -= amount;
			if(this.amount <= 0) {
				this.aspect = null;
				this.amount = 0;					
			}
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
		if ((this.amount >= amt) && (tag == this.aspect)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int containerContains(Aspect tag) {
		return 0;
	}

	@Override
	public boolean isConnectable(ForgeDirection face) {
		return (face != ForgeDirection.getOrientation(this.facing));
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return (face != ForgeDirection.getOrientation(this.facing));
	}

	@Override
	public boolean canOutputTo(ForgeDirection face) {
		return false;
	}

	@Override
	public void setSuction(Aspect aspect, int amount) {	}

	@Override
	public int takeVis(Aspect aspect, int amount) {
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
	public void setAspects(AspectList aspects) {	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return false;
	}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		return this.suction;
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		if (this.amount < this.maxAmount){
			return 128;
		}
	return 0;
	}

	@Override
	public int addVis(Aspect aspect, int amount) {
		return amount - addToContainer(aspect, amount);
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection face) {
		return this.aspect;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		return this.amount;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		return false;
	}
}
