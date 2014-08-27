package democretes.tiles.dynamos;

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
import democretes.tiles.base.EnergyStorage;
import democretes.tiles.base.TileDynamoBase;

public class TileEssentiaDynamo extends TileDynamoBase implements IAspectContainer, IEssentiaTransport{

	private int amount;
	private int maxAmount = 64;
	private static Aspect aspect = null;
	public int count;
	

	public TileEssentiaDynamo() {
		super(new EnergyStorage(maxEnergy_default, maxExtract_default, maxReceive_default));
	}
	
	@Override
	public int extractFuel(int ener) {
		if(amount<=0 || aspect==null){
			return 0;
		}
		float ratio = ((float) ener) / 80F;
		int val = (int) (getAspectFuel(aspect) * ratio);amount--;
		return val;
	}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		super.readCustomNBT(comp);
		amount = comp.getShort("Amount");
		aspect = Aspect.getAspect(comp.getString("Aspect"));
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		super.writeCustomNBT(comp);
		comp.setShort("Amount", (short)amount);
		if (aspect != null) {
			comp.setString("Aspect", aspect.getTag());
		}
	}

	public int getAspectFuel(Aspect aspect) {
		if(aspect == Aspect.FIRE || aspect == Aspect.ENERGY) {
			return 240;
		}
		if(aspect == Aspect.AIR  || aspect == Aspect.ORDER) {
			return 80;
		}
		if(aspect == Aspect.WATER){
			if(worldObj.getBiomeGenForCoords(xCoord, yCoord).isHighHumidity()) {
				return 140;
			}
			return 40;
		}
		if(aspect == Aspect.MAGIC  || aspect == Aspect.ELDRITCH ) {
			if(worldObj.getBiomeGenForCoords(xCoord, yCoord) == BiomeGenBase.sky || worldObj.getBiomeGenForCoords(xCoord, yCoord) == Thaumcraft.biomeMagicalForest) {
				return 180;
			}
			return 90;
		}
		if(aspect == Aspect.MECHANISM || aspect == Aspect.METAL || aspect == Aspect.MOTION || aspect == Aspect.TOOL || aspect == Aspect.TRAP || 
				aspect == Aspect.MINE || aspect == Aspect.CRAFT || aspect == Aspect.TRAVEL) {
			return 80;
		}
		if(aspect == Aspect.STONE || aspect == Aspect.CRYSTAL || aspect == Aspect.EARTH || aspect == Aspect.ENTROPY){
			if(this.yCoord < 8) {
				return 20;
			}
			return 8;	
		}
		if(aspect == Aspect.MAN || aspect == Aspect.SENSES | aspect == Aspect.HEAL || aspect == Aspect.HARVEST || aspect == Aspect.HUNGER ||
				aspect == Aspect.DEATH || aspect == Aspect.BEAST ||  aspect == Aspect.POISON|| aspect == Aspect.MIND || 
				aspect == Aspect.SOUL || aspect == Aspect.WEAPON || aspect == Aspect.WEATHER) {
			return 60;
		}
		if( aspect == Aspect.TREE || aspect == Aspect.SEED || aspect == Aspect.PLANT || aspect == Aspect.CROP || aspect == Aspect.CLOTH || aspect == Aspect.VOID || aspect == Aspect.FLESH) { 
			return 40;
		}
		if(aspect == Aspect.UNDEAD) {
			if(worldObj.getBiomeGenForCoords(xCoord, yCoord) == BiomeGenBase.sky){
				return 120;
			}
		}
		if(aspect == Aspect.EXCHANGE) {
			Random rand = new Random();
			return rand.nextInt(2400);
		}
		if(aspect == Aspect.FLIGHT){
			if(yCoord > 200) {
				return 480;
			}
			return 240;
		}
		if(aspect == Aspect.ICE) {
			return 160;
		}
		if(aspect == Aspect.SLIME) {
			if(worldObj.getChunkFromBlockCoords(xCoord, zCoord).getRandomWithSeed(987234911L).nextInt(10) == 0){
				return 64;
			}
			return 16;
		}
		if(aspect == Aspect.LIGHT) {
			if(worldObj.isDaytime()) {
				return 120;
			}
			return 32;
		}
		if(aspect == Aspect.DARKNESS) {
			if(!worldObj.isDaytime()) {
				return 120;
			}
			return 24;
		}
		if(aspect == Aspect.AURA) {
			if(worldObj.getBiomeGenForCoords(this.xCoord, this.yCoord) == Thaumcraft.biomeMagicalForest) {
				return 640;
			}
			return 480;
		}
		if(aspect == Aspect.TAINT) {
			if(worldObj.getBiomeGenForCoords(this.xCoord, this.yCoord) == Thaumcraft.biomeTaint) {
				return 1200;
			}
			return 480;
		}
		return 10;
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
		if (!worldObj.isRemote && amount < maxAmount) {
			fill();
		}
	}

	public void fill() {
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
	public boolean doesContainerAccept(Aspect tag) {return aspect!=null || tag==aspect;}

	@Override
	public int containerContains(Aspect tag) {return (aspect!=null && tag==aspect) ? amount : 0;}

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