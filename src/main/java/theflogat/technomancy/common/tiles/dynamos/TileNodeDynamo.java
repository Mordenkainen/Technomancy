package theflogat.technomancy.common.tiles.dynamos;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.nodes.INode;
import theflogat.technomancy.common.tiles.base.EnergyStorage;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;

public class TileNodeDynamo extends TileDynamoBase{
	
	public static final int maxAmount = 32;
	
	public int amount = 0;

	@Override
	public int extractFuel(int ener) {
		float ratio = ((float) ener) / 80F;
		if(((int)ratio)>amount)
			return 0;
		amount -= ((int)ratio);
		return (int) (((float)10*20)*ratio);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if(amount<maxAmount){
			takeAspectsFromNodes();
		}
	}

	public void takeAspectsFromNodes() {
		ArrayList<TileEntity> l = getNodes();
		if(!l.isEmpty()){
			for(TileEntity te : l){
				if (te != null && te instanceof INode) {
					INode node = (INode)te;
					Aspect[] as = node.getAspects().getAspects();
					for (int i = 0; i < as.length; i++) {
						Aspect aspect = as[i];
						int nodeAmount = node.containerContains(aspect);
						if(nodeAmount!= 1 && amount < 16){
							if(node.takeFromContainer(aspect, 1) ) {
								amount += 1;
							}
						}
					}
				}
			}
		}
	}

	public ArrayList<TileEntity> getNodes() {
		ArrayList<TileEntity> l = new ArrayList<TileEntity>();
		for(int xx = -4; xx <= 4; xx++) {
			for(int yy = -4; yy <= 4; yy++) {
				for(int zz = -4; zz <= 4; zz++) {
					TileEntity te = worldObj.getTileEntity(xCoord + xx, yCoord + yy, zCoord + zz);
					if(te instanceof INode) {
						l.add(te);
					}					
				}
			}
		}
		return l;
	}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		super.readCustomNBT(comp);
		this.amount = comp.getShort("Amount");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		super.writeCustomNBT(comp);
		comp.setShort("Amount", (short)amount);
	}
}