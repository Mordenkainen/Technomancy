package theflogat.technomancy.common.tiles.thaumcraft.machine;

import java.util.HashMap;

import cpw.mods.fml.common.Optional;
import me.jezza.thaumicpipes.api.interfaces.IThaumicOutput;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.aspects.IEssentiaTransport;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockCondenser;
import theflogat.technomancy.common.tiles.base.IWrenchable;
import theflogat.technomancy.common.tiles.base.TileMachineRedstone;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.lib.handlers.Rate;

@Optional.Interface(iface = "me.jezza.thaumicpipes.api.interfaces.IThaumicOutput", modid = "ThaumicPipes")
public class TileCondenser extends TileMachineRedstone implements IEssentiaTransport, IAspectSource, IWrenchable, IThaumicOutput {

	public static final Aspect aspect = Aspect.ENERGY;

	public HashMap<ForgeDirection, Boolean> sides = new HashMap<ForgeDirection, Boolean>();
	public int amount = 0;
	public static final int maxAmount = 64;
	public static int cost = Rate.condenserCost;

	public TileCondenser() {
		super(Rate.condenserCost * 5, RedstoneSet.LOW);
		for(ForgeDirection dir:ForgeDirection.VALID_DIRECTIONS) {
			sides.put(dir, false);
		}
	}

	@Override
	public void updateEntity() {
		if(set.canRun(this) && energy >= cost && amount < maxAmount) {
			extractEnergy(cost, false);
			amount++;
		}
		if(amount > 0) {
			for(ForgeDirection dir:ForgeDirection.VALID_DIRECTIONS) {
				if(sides.get(dir).booleanValue()) {
					IEssentiaTransport te = (IEssentiaTransport)Thaumcraft.getConnectableTile(worldObj, xCoord, yCoord, zCoord, dir);
					if(te != null && te.canInputFrom(dir)) {
						amount = te.addEssentia(aspect, amount, dir);
					}
				}
			}
		}
	}
	
	@Override
	public void writeSyncData(NBTTagCompound compound) {
		super.writeSyncData(compound);
		compound.setShort("Amount", (short)amount);
		for(ForgeDirection dir:ForgeDirection.VALID_DIRECTIONS) {
			NBTTagCompound side = new NBTTagCompound();
			side.setBoolean("s", sides.get(dir).booleanValue());
			compound.setTag(dir.name(), side);
		}
	}
	
	@Override
	public void readSyncData(NBTTagCompound compound) {
		super.readSyncData(compound);
		amount = compound.getShort("Amount");
		sides.clear();
		for(ForgeDirection dir:ForgeDirection.VALID_DIRECTIONS) {
			NBTTagCompound side = compound.getCompoundTag(dir.name());
			sides.put(dir, side.getBoolean("s"));
		}
	}

	@Override
	public AspectList getAspects() {
		AspectList al = new AspectList();
		if (amount > 0) {
			al.add(aspect, amount);
		}
		return al;
	}

	@Override
	public int addToContainer(Aspect tag, int amount) {
		return 0;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		if(tag==aspect && amount<=this.amount){
			this.amount -= amount;
			return true;
		}
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		if(ot.getAspects().length == 1 && ot.getAspects()[0] == aspect && ot.getAmount(aspect) <= amount) {
			amount -= ot.getAmount(aspect);
			return true;
		}
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amt) {
		if (tag == aspect && amt <= amount) {
			return true;
		}
		return false;
	}

	@Override
	public int containerContains(Aspect tag) {
		return tag == aspect ? amount : 0;
	}

	@Override
	public boolean isConnectable(ForgeDirection face) {
		return sides.get(face);
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return false;
	}

	@Override
	public boolean canOutputTo(ForgeDirection face) {
		return sides.get(face);
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
		return tag == aspect;
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
		return ot.getAspects().length == 1 && ot.getAspects()[0] == aspect && amount > 0;
	}
	
	public boolean toggleDir(int side) {
		if(side != BlockCondenser.getFacingFromMeta(worldObj.getBlockMetadata(xCoord, yCoord, zCoord))) {
			boolean b = sides.get(ForgeDirection.VALID_DIRECTIONS[side]).booleanValue();
			sides.put(ForgeDirection.VALID_DIRECTIONS[side], !b);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			return true;
		}
		return false;
	}

	@Override
	public boolean onWrenched(boolean sneaking) {
		blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		blockMetadata++;
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, blockMetadata >= 4 ? 0 : blockMetadata, 2);
		return false;
	}
}