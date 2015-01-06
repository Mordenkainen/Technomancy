/**
 * 
 * This class was created by <Azanor>. It's distributed as
 * part of the Thaumcraft Mod.
 * 
 **/

package theflogat.technomancy.handlers.compat.thaumcraft;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.aspects.IEssentiaTransport;
import theflogat.technomancy.handlers.compat.Thaumcraft;


public class TileJarFillable extends TileJar implements IAspectSource, IEssentiaTransport{
	public Aspect aspect = null;
	public Aspect aspectFilter = null;
	public int amount = 0;
	public int maxAmount = 64;
	public int facing = 2;


	public boolean forgeLiquid = false;
	public int lid = 0;

	@Override
	public boolean canUpdate()
	{
		return true;
	}


	@Override
	public void readCustomNBT(NBTTagCompound nbttagcompound)
	{
		aspect = Aspect.getAspect(nbttagcompound.getString("Aspect"));
		aspectFilter = Aspect.getAspect(nbttagcompound.getString("AspectFilter"));
		amount = nbttagcompound.getShort("Amount");
		facing = nbttagcompound.getByte("facing");
	}


	@Override
	public void writeCustomNBT(NBTTagCompound nbttagcompound)
	{
		if (aspect != null) nbttagcompound.setString("Aspect", aspect.getTag());
		if (aspectFilter != null) nbttagcompound.setString("AspectFilter", aspectFilter.getTag());
		nbttagcompound.setShort("Amount", (short)amount);
		nbttagcompound.setByte("facing", (byte)facing);
	}



	@Override
	public AspectList getAspects()
	{
		AspectList al = new AspectList();
		if ((aspect != null) && (amount > 0)) al.add(aspect, amount);
		return al;
	}



	@Override
	public void setAspects(AspectList aspects) {}


	@Override
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

	@Override
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

	@Override
	public boolean takeFromContainer(AspectList ot)
	{
		return false;
	}


	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amt)
	{
		if ((amount >= amt) && (tag == aspect)) return true;
		return false;
	}

	@Override
	public boolean doesContainerContain(AspectList ot)
	{
		for (Aspect tt : ot.getAspects()) {
			if ((amount > 0) && (tt == aspect)) return true;
		}
		return false;
	}

	@Override
	public int containerContains(Aspect tag)
	{
		return 0;
	}

	@Override
	public boolean doesContainerAccept(Aspect tag)
	{
		return aspectFilter != null ? tag.equals(aspectFilter) : true;
	}




	@Override
	public boolean isConnectable(ForgeDirection face)
	{
		return face == ForgeDirection.UP;
	}

	@Override
	public boolean canInputFrom(ForgeDirection face)
	{
		return face == ForgeDirection.UP;
	}

	@Override
	public boolean canOutputTo(ForgeDirection face)
	{
		return face == ForgeDirection.UP;
	}


	@Override
	public void setSuction(Aspect aspect, int amount) {}

	@Override
	public boolean renderExtendedTube()
	{
		return true;
	}

	@Override
	public int getMinimumSuction()
	{
		return aspectFilter != null ? 64 : 32;
	}

	@Override
	public Aspect getSuctionType(ForgeDirection loc)
	{
		return aspectFilter != null ? aspectFilter : aspect;
	}

	@Override
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


	@Override
	public Aspect getEssentiaType(ForgeDirection loc)
	{
		return aspect;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection loc)
	{
		return amount;
	}

	@Override
	public int takeEssentia(Aspect aspect, int amount, ForgeDirection dir)
	{
		return takeFromContainer(aspect, amount) ? amount : 0;
	}

	@Override
	public int addEssentia(Aspect aspect, int amount, ForgeDirection dir)
	{
		return amount - addToContainer(aspect, amount);
	}

	int count = 0;

	@Override
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
				addToContainer(ta, ic.takeEssentia(ta, 1, ForgeDirection.DOWN));
			}
		}
	}
}