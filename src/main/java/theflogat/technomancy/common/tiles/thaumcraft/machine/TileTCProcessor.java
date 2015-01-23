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
	public Aspect suction = Aspect.FIRE;
	
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
		if(!(amount>cost))
				return false;
		takeFromContainer(suction, cost);
		return true;
	}
	
	protected void perform() {
		TileEntity te = null;
		IEssentiaTransport ic = null;
		for (int y = 0; y <= 1; y++) {
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if ((dir != ForgeDirection.getOrientation(this.facing)) && (dir != ForgeDirection.DOWN) && ((y != 0) || (dir != ForgeDirection.UP))) {
					te = Thaumcraft.getConnectableTile(this.worldObj, this.xCoord, this.yCoord + y, this.zCoord, dir);
					if (te != null) {
						ic = (IEssentiaTransport)te;
						Aspect ta = ic.getEssentiaType(dir.getOpposite());
						if (ta==suction && (ic.getEssentiaAmount(dir.getOpposite()) > 0) && (ic.getSuctionAmount(dir.getOpposite()) <
								getSuctionAmount(null)) && (getSuctionAmount(null) >= ic.getMinimumSuction())) {
							addToContainer(ta, ic.takeEssentia(ta, 1, dir.getOpposite()));
							return;
						}
					}
				}
			}
		}
		//System.out.println(amount);
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound)  {
		super.writeCustomNBT(compound);
		compound.setShort("Amount", (short)amount);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound compound)  {
		super.readCustomNBT(compound);
		amount = compound.getShort("Amount");
	}

	@Override
	public AspectList getAspects()  {
	    AspectList al = new AspectList();
	    if (amount > 0) {
	    	al.add(Aspect.FIRE, amount);
	    }
	    return al;
	}

	@Override
	public int addToContainer(Aspect tag, int amount)  {
		if (amount == 0 || tag!=suction) {
			return amount;
		}
		if (this.amount < maxAmount) {
		    int added = Math.min(amount, maxAmount - this.amount);
		    this.amount += added;
		    amount -= added;
		}
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return amount;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount)  {
		if(this.amount >= amount && tag==suction) {
			this.amount -= amount;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			return true;			
		}
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		if(ot.aspects.containsKey(suction) && amount >= ot.getAmount(suction)) {
			this.amount -= ot.getAmount(suction);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			return true;			
		}
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amt) {
		if (amount >= amt && tag == suction) {
			return true;
		}
		return false;
	}
	
	@Override
	public int containerContains(Aspect tag) {
		return tag==suction ? amount : 0;
	}

	@Override
	public boolean isConnectable(ForgeDirection face) {
		return (face != ForgeDirection.getOrientation(facing));
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return (face != ForgeDirection.getOrientation(facing));
	}

	@Override
	public boolean canOutputTo(ForgeDirection face) {
		return false;
	}

	@Override
	public void setSuction(Aspect aspect, int amount) {	}

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
	public void setAspects(AspectList aspects) {	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return tag==suction && amount>0;
	}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		return suction;
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
		return amount!=0 ? suction : null;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		return amount;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		return ot.aspects.containsKey(suction);
	}

	@Override
	public String getInventoryName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}
}