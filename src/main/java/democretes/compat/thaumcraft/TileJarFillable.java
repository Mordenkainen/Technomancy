/**
 * 
 * This class was created by <Azanor>. It's distributed as
 * part of the Thaumcraft Mod.
 * 
 **/

package democretes.compat.thaumcraft;

import democretes.compat.Thaumcraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.aspects.IEssentiaTransport;


public class TileJarFillable extends TileJar implements IAspectSource, IEssentiaTransport{
	public Aspect aspect = null;
	public Aspect aspectFilter = null;
	public int amount = 0;
	public int maxAmount = 64;
	public int facing = 2;


	public boolean forgeLiquid = false;
	public int lid = 0;

	public boolean canUpdate()
	{
		return true;
	}


	public void readCustomNBT(NBTTagCompound nbttagcompound)
	{
		aspect = Aspect.getAspect(nbttagcompound.getString("Aspect"));
		aspectFilter = Aspect.getAspect(nbttagcompound.getString("AspectFilter"));
		amount = nbttagcompound.getShort("Amount");
		facing = nbttagcompound.getByte("facing");
	}


	public void writeCustomNBT(NBTTagCompound nbttagcompound)
	{
		if (aspect != null) nbttagcompound.setString("Aspect", aspect.getTag());
		if (aspectFilter != null) nbttagcompound.setString("AspectFilter", aspectFilter.getTag());
		nbttagcompound.setShort("Amount", (short)amount);
		nbttagcompound.setByte("facing", (byte)facing);
	}



	public AspectList getAspects()
	{
		AspectList al = new AspectList();
		if ((aspect != null) && (amount > 0)) al.add(aspect, amount);
		return al;
	}



	public void setAspects(AspectList aspects) {}


	public int addToContainer(Aspect tt, int am)
	{
		if (am == 0) return am;
		if (((amount < maxAmount) && (tt == aspect)) || (amount == 0))
		{
			aspect = tt;
			int added = Math.min(am, maxAmount - amount);
			amount += added;
			am -= added;
		}
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return am;
	}

	public boolean takeFromContainer(Aspect tt, int am)
	{
		if ((amount >= am) && (tt == aspect)) {
			amount -= am;
			if (amount <= 0) {
				aspect = null;
				amount = 0;
			}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			return true;
		}
		return false;
	}

	public boolean takeFromContainer(AspectList ot)
	{
		return false;
	}


	public boolean doesContainerContainAmount(Aspect tag, int amt)
	{
		if ((amount >= amt) && (tag == aspect)) return true;
		return false;
	}

	public boolean doesContainerContain(AspectList ot)
	{
		for (Aspect tt : ot.getAspects()) {
			if ((amount > 0) && (tt == aspect)) return true;
		}
		return false;
	}

	public int containerContains(Aspect tag)
	{
		return 0;
	}

	public boolean doesContainerAccept(Aspect tag)
	{
		return aspectFilter != null ? tag.equals(aspectFilter) : true;
	}




	public boolean isConnectable(ForgeDirection face)
	{
		return face == ForgeDirection.UP;
	}

	public boolean canInputFrom(ForgeDirection face)
	{
		return face == ForgeDirection.UP;
	}

	public boolean canOutputTo(ForgeDirection face)
	{
		return face == ForgeDirection.UP;
	}


	public void setSuction(Aspect aspect, int amount) {}

	public boolean renderExtendedTube()
	{
		return true;
	}

	public int getMinimumSuction()
	{
		return aspectFilter != null ? 64 : 32;
	}

	public Aspect getSuctionType(ForgeDirection loc)
	{
		return aspectFilter != null ? aspectFilter : aspect;
	}

	public int getSuctionAmount(ForgeDirection loc)
	{
		if (amount < maxAmount) {
			if (aspectFilter != null) {
				return 64;
			}
			return 32;
		}

		return 0;
	}


	public Aspect getEssentiaType(ForgeDirection loc)
	{
		return aspect;
	}

	public int getEssentiaAmount(ForgeDirection loc)
	{
		return amount;
	}

	public int takeVis(Aspect aspect, int amount)
	{
		return takeFromContainer(aspect, amount) ? amount : 0;
	}

	public int addVis(Aspect aspect, int amount)
	{
		return amount - addToContainer(aspect, amount);
	}

	int count = 0;

	public void updateEntity()
	{
		super.updateEntity();
		if ((!worldObj.isRemote) && (++count % 5 == 0) && (amount < maxAmount)) {
			fillJar();
		}
	}

	void fillJar() {
		TileEntity te = Thaumcraft.getConnectableTile(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UP);
		if (te != null) {
			IEssentiaTransport ic = (IEssentiaTransport)te;
			if (!ic.canOutputTo(ForgeDirection.DOWN)) { return;
			}
			Aspect ta = null;
			if (aspectFilter != null) {
				ta = aspectFilter;
			}
			else if ((aspect != null) && (amount > 0)) {
				ta = aspect;
			}
			else if ((ic.getEssentiaAmount(ForgeDirection.DOWN) > 0) && 
					(ic.getSuctionAmount(ForgeDirection.DOWN) < getSuctionAmount(ForgeDirection.UP)) && (getSuctionAmount(ForgeDirection.UP) >= ic.getMinimumSuction()))
			{
				ta = ic.getEssentiaType(ForgeDirection.DOWN);
			}




			if ((ta != null) && (ic.getSuctionAmount(ForgeDirection.DOWN) < getSuctionAmount(ForgeDirection.UP))) {
				addToContainer(ta, ic.takeVis(ta, 1));
			}
		}
	}
}