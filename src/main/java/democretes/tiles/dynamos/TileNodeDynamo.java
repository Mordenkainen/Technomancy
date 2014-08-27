package democretes.tiles.dynamos;

import democretes.tiles.base.EnergyStorage;
import democretes.tiles.base.TileDynamoBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.nodes.INode;

public class TileNodeDynamo extends TileDynamoBase{
	
	private int amount;
	private Aspect aspect = Aspect.ENERGY;
	private int maxAmount = 4;
	private static int nodeRF = 36000;
	
	public TileNodeDynamo() {
		super(new EnergyStorage(maxEnergy_default, maxExtract_default, maxReceive_default));
		facing = 0;
	}
	
	@Override
	public int extractFuel(int ener) {
		float ratio = ((float) ener) / 80F;
		if(((int)ratio)>amount)
			return 0;
		amount -= ((int)ratio);
		return (int) (((float)10*20)*ratio);
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
	
	public void readCustomNBT(NBTTagCompound comp) {
		super.readCustomNBT(comp);
		this.amount = comp.getShort("Amount");
		this.aspect = Aspect.getAspect(comp.getString("Aspect"));
	}
	
	public void writeCustomNBT(NBTTagCompound comp) {
		super.writeCustomNBT(comp);
		comp.setShort("Amount", (short)this.amount);
		if (this.aspect != null) {
			comp.setString("Aspect", this.aspect.getTag());
		}
	}
}