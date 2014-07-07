package democretes.blocks.dynamos.tiles;

import java.util.HashMap;
import java.util.Map;

import cofh.api.energy.EnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.nodes.INode;

public class TileNodeDynamo extends TileDynamoBase implements IAspectContainer{

	int currentRF;
	private int amount;
	private Aspect aspect = Aspect.ENERGY;
	private int maxAmount = 1;
	private static int nodeRF = 36000;
	
	public TileNodeDynamo() {
		this.facing = 0;
	}
	
	protected boolean canGenerate() {
		if (this.facing == 0) {
			takeAspectsFromNode();
			if(this.amount != 0) {
				return true;
			}
		}
		return false;	
	}
	
	protected void generate() {
		if (this.fuelRF <= 0) {
			this.fuelRF += getFuelEnergy();
			this.amount -= 1;
		}
		int energy = calcEnergy();
		this.energyStorage.modifyEnergyStored(energy);
		this.fuelRF -= energy;
	}
	
	public static int getFuelEnergy() {				
		return nodeRF;
	}
	
	void takeAspectsFromNode() {
		TileEntity te = getNode();
		if (te != null && te instanceof INode) {
			INode node = (INode)te;
			AspectList aspects = node.getAspects();
			Aspect[] as = aspects.getAspects();
			for (int i = 0; i < as.length;) {
				Aspect aspect = as[i];
				int nodeAmount = node.containerContains(aspect);
				if(this.amount < this.maxAmount){
					if(nodeAmount != 1)
						if(node.takeFromContainer(aspect, 1) ) {
							this.amount += 1;
							break;				
						}else{
							i++;
					}else{
						i++;
					}
				}else{
					break;
				}
			}
		}		
	}
	
	TileEntity getNode() {
		for(int xx = -8; xx <= 8; xx++) {
			for(int yy = -8; yy <= 8; yy++) {
				for(int zz = -8; zz <= 8; zz++) {
					TileEntity te = this.worldObj.getBlockTileEntity(this.xCoord + xx, this.yCoord + yy, this.zCoord + zz);
					if(te instanceof INode) {
							return te;
					}					
				}
			}
		}
		return null;
	}
	
	public void readCustomCompound(NBTTagCompound compound) {
		this.amount = compound.getShort("Amount");
		this.fuelRF = compound.getInteger("RF");	
		this.aspect = Aspect.getAspect(compound.getString("Aspect"));
	}
	
	public void writeCustomNBT(NBTTagCompound compound) {
		compound.setShort("Amount", (short)this.amount);
		compound.setInteger("RF", fuelRF);
		if (this.aspect != null) {
			compound.setString("Aspect", this.aspect.getTag());
		}
	}

	@Override
	public AspectList getAspects() {
		AspectList al = new AspectList();
		if ((this.aspect != null) && (this.amount > 0)) {
			al.add(this.aspect, this.amount);
		}
		return al;
	}

	@Override
	public void setAspects(AspectList aspects) {}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return false;
	}

	@Override
	public int addToContainer(Aspect tag, int amount) {
		if (amount == 0) {
		    return amount;
		  }
		  if (((this.amount < this.maxAmount ) && (tag == this.aspect)) || (this.amount == 0))  {
		    this.aspect = tag;
		    int added = Math.min(amount, this.maxAmount - this.amount);
		    this.amount += added;
		    amount -= added;
		  }
		  this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		  return amount;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		if ((this.amount >= amount) && (tag == this.aspect))
		  {
		    this.amount -= amount;
		    if (this.amount <= 0)
		    {
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
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		if ((this.amount >= amount) && (tag == this.aspect)) {
		    return true;
		  }
		  return false;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		for (Aspect tt : ot.getAspects()) {
		    if ((this.amount > 0) && (tt == this.aspect)) {
		      return true;
		    }
		  }
		  return false;
	}

	@Override
	public int containerContains(Aspect tag) {
		return 0;
	}
	


}
