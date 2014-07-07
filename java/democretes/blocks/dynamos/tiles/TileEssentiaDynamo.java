package democretes.blocks.dynamos.tiles;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import democretes.compat.Thaumcraft;

public class TileEssentiaDynamo extends TileDynamoBase implements IAspectContainer, IEssentiaTransport{

	private int amount;
	private int maxAmount = 64;
	private static Aspect aspect = null;
	public int count;


	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		amount = compound.getShort("Amount");
		fuelRF = compound.getInteger("RF");
		aspect = Aspect.getAspect(compound.getString("Aspect"));
		facing = compound.getByte("Facing");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		compound.setShort("Amount", (short)amount);
		compound.setInteger("RF", fuelRF);	
		compound.setByte("Facing", facing);
		if (aspect != null) {
			compound.setString("Aspect", aspect.getTag());
		}
	}

	@Override
	protected boolean canGenerate() {
		if (amount > 0) {
			return true;
		}
		return false;
	}

	@Override
	protected void generate() {
		if (fuelRF <= 0) {
			fuelRF += ((getAspectFuel(aspect))*3/4);
			takeFromContainer(aspect, getFuelRemoval(aspect));
		}
		int energy = calcEnergy();
		energyStorage.modifyEnergyStored(energy);
		fuelRF -= energy;
	}

	@Override
	protected void transferEnergy(int bSide){
		updateAdjacentHandlers();
		if(adjacentHandler!=null) {
			energyStorage.modifyEnergyStored(-adjacentHandler.receiveEnergy(ForgeDirection.VALID_DIRECTIONS[(bSide ^ 0x1)],
					Math.min(maxTransfer * getTransferRate(aspect), energyStorage.getEnergyStored()), false));
		}
	}

	public int getAspectFuel(Aspect aspect) {
		if(aspect == Aspect.FIRE || aspect == Aspect.ENERGY) {
			return 32000;
		}
		if(aspect == Aspect.AIR ||  aspect == Aspect.WATER  || aspect == Aspect.ORDER) {
			return 12000;
		}
		if(aspect == Aspect.MAGIC  || aspect == Aspect.ELDRITCH ) {
			if(this.worldObj.getBiomeGenForCoords(xCoord, yCoord) == BiomeGenBase.sky) {
				return 36000;
			}
			return 18000;
		}
		if(aspect == Aspect.MECHANISM || aspect == Aspect.METAL || aspect == Aspect.MOTION || aspect == Aspect.TOOL || aspect == Aspect.TRAP || 
				aspect == Aspect.MINE || aspect == Aspect.CRAFT || aspect == Aspect.TRAVEL) {
			return 16000;
		}
		if(aspect == Aspect.STONE || aspect == Aspect.CRYSTAL || aspect == Aspect.EARTH || aspect == Aspect.ENTROPY){
			if(this.yCoord < 8) {
				return 2000;
			}
			return 800;	
		}
		if(aspect == Aspect.MAN || aspect == Aspect.SENSES | aspect == Aspect.HEAL || aspect == Aspect.HARVEST || aspect == Aspect.HUNGER ||
				aspect == Aspect.DEATH || aspect == Aspect.BEAST ||  aspect == Aspect.POISON|| aspect == Aspect.MIND || 
				aspect == Aspect.SOUL || aspect == Aspect.WEAPON || aspect == Aspect.WEATHER || aspect == Aspect.UNDEAD) {
			return 12000;
		}
		if( aspect == Aspect.TREE || aspect == Aspect.SEED || aspect == Aspect.PLANT || aspect == Aspect.CROP || aspect == Aspect.CLOTH || aspect == Aspect.VOID || aspect == Aspect.FLESH) { 
			return 8000;
		}
		if(aspect == Aspect.UNDEAD) {
			if(this.worldObj.getBiomeGenForCoords(xCoord, yCoord) == BiomeGenBase.sky)
				return 12000;
		}
		if(aspect == Aspect.EXCHANGE) {
			Random rand = new Random();
			return rand.nextInt(24000);
		}
		if(aspect == Aspect.FLIGHT){
			if(yCoord > 200) {
				return 32000;
			}
			return 12000;
		}
		if(aspect == Aspect.ICE) {
			return 8000;
		}
		if(aspect == Aspect.SLIME) {
			return 16000;
		}
		if(aspect == Aspect.LIGHT) {
			if(worldObj.isDaytime()) {
				return 2400;
			}
			return 800;
		}
		if(aspect == Aspect.DARKNESS) {
			if(!worldObj.isDaytime()) {
				return 36000;
			}
			return 1200;
		}
		if(aspect == Aspect.AURA) {
			if(worldObj.getBiomeGenForCoords(this.xCoord, this.yCoord) == Thaumcraft.biomeMagicalForest) {
				return 32000;
			}
			return 24000;
		}
		if(aspect == Aspect.TAINT) {
			if(worldObj.getBiomeGenForCoords(this.xCoord, this.yCoord) == Thaumcraft.biomeTaint) {
				return 60000;
			}
			return 24000;
		}
		return 1000;
	}

	public int getTransferRate(Aspect aspect) {
		if(aspect == Aspect.AURA) {
			if(worldObj.getBiomeGenForCoords(this.xCoord, this.yCoord) == Thaumcraft.biomeMagicalForest) {
				return 2;
			}
		}
		if(aspect == Aspect.TAINT) {
			if(worldObj.getBiomeGenForCoords(this.xCoord, this.yCoord) == Thaumcraft.biomeTaint) {
				return 4;
			}
		}
		if(aspect == Aspect.SLIME) {
			return 1/2;
		}
		if(aspect == Aspect.FIRE || aspect == Aspect.ENERGY) {
			return 2;
		}
		if(aspect == Aspect.LIGHT && worldObj.isDaytime()) {
			return 3;
		}
		if(aspect == Aspect.DARKNESS && !worldObj.isDaytime()) {
			return 3;
		}
		if(aspect == Aspect.MECHANISM || aspect == Aspect.METAL || aspect == Aspect.MOTION) {
			return 2;
		}
		return 1;
	}

	boolean firstRemoval = true;
	int getFuelRemoval(Aspect aspect) {
		if(aspect == Aspect.WEATHER && worldObj.isThundering()) {
			return 0;
		}
		BiomeGenBase bg = worldObj.getBiomeGenForCoords(xCoord, yCoord);
		if(aspect == Aspect.ICE &&  (bg == BiomeGenBase.frozenRiver || bg == BiomeGenBase.frozenOcean || bg == BiomeGenBase.iceMountains
				|| bg == BiomeGenBase.icePlains || bg == BiomeGenBase.taiga || bg == BiomeGenBase.taigaHills)) {
			if(firstRemoval) {
				firstRemoval = false;
				return 0;
			}else{
				firstRemoval = true;
			}
		}
		if(aspect == Aspect.WATER && (bg == BiomeGenBase.ocean || bg == BiomeGenBase.river)) {
			if(firstRemoval) {
				firstRemoval = false;
				return 0;
			}else{
				firstRemoval = true;
			} 
		}
		return 1;
	}

	@Override
	public AspectList getAspects() {
		AspectList al = new AspectList();
		if ((aspect != null) && (amount > 0)) {
			al.add(this.aspect, this.amount);
		}
		return al;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if ((!this.worldObj.isRemote) && (++count % 10 == 0) && (amount < maxAmount)) {
			fill();
		}
	}

	void fill() {
		TileEntity te = null;
		IEssentiaTransport ic = null;
		for (int y = 0; y <= 1; y++) {
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if ((dir != ForgeDirection.getOrientation(facing)) && (dir != ForgeDirection.DOWN) && ((y != 0) || (dir != ForgeDirection.UP))) {
					te = Thaumcraft.getConnectableTile(worldObj, xCoord, yCoord + y, zCoord, dir);
					if (te != null) {
						ic = (IEssentiaTransport)te;
						Aspect ta = ic.getEssentiaType(dir.getOpposite());
						if ((ic.getEssentiaAmount(dir.getOpposite()) > 0) && (ic.getSuctionAmount(dir.getOpposite()) < getSuctionAmount(null))
								&& (getSuctionAmount(null) >= ic.getMinimumSuction())) {
							addToContainer(ta, ic.takeVis(ta, 1));
							return;
						}
					}
				}
			}
		}
	}

	@Override
	public int addToContainer(Aspect tag, int am) {
		if (am == 0) {
			return am;
		}
		if (((amount < maxAmount ) && (tag == aspect)) || (amount == 0)) {
			aspect = tag;
			int added = Math.min(am, maxAmount - amount);
			amount += added;
			am -= added;
		}
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return am;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		if ((amount >= amount) && (tag == aspect))
		{
			amount -= amount;
			if (amount <= 0)  {
				aspect = null;
				amount = 0;
			}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			return true;
		}
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		if ((amount >= amount) && (tag == aspect)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		for (Aspect tt : ot.getAspects()) {
			if ((amount > 0) && (tt == aspect)) {
				return true;
			}
		}
		return false;
	}	

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
	public boolean isConnectable(ForgeDirection face) {
		return true;
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return true;
	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {return false;}

	@Override
	public int containerContains(Aspect tag) {return 0;}

	@Override
	public boolean takeFromContainer(AspectList ot) {return false;}

	@Override
	public boolean canOutputTo(ForgeDirection face) {return false;}

	@Override
	public void setAspects(AspectList aspects) {}

	@Override
	public void setSuction(Aspect aspect, int amount) {}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		if(amount > 0) {
			return aspect;
		}
		return null;
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		if (amount < maxAmount){
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
}