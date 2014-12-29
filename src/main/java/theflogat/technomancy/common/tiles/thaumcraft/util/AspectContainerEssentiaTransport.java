package theflogat.technomancy.common.tiles.thaumcraft.util;

import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;

public class AspectContainerEssentiaTransport implements IAspectContainer{
	
	IEssentiaTransport trans;
	
	public AspectContainerEssentiaTransport(IEssentiaTransport trans) {
		this.trans = trans;
	}

	@Override
	public AspectList getAspects() {
		AspectList as = new AspectList();
		for(ForgeDirection dir: ForgeDirection.VALID_DIRECTIONS){
			if(trans.getEssentiaType(dir)!=null){
				as.add(trans.getEssentiaType(dir), trans.getEssentiaAmount(dir));
			}
		}
		return as;
	}

	@Override
	public void setAspects(AspectList aspects) {}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		AspectList as = new AspectList();
		for(ForgeDirection dir: ForgeDirection.VALID_DIRECTIONS){
			if(trans.getEssentiaType(dir)!=null){
				as.add(trans.getEssentiaType(dir), 1);
			}
		}
		return as.getAmount(tag)==1;
	}

	@Override
	public int addToContainer(Aspect tag, int amount) {
		int amountSent = 0;
		int amountToSend = amount;
		for(ForgeDirection dir: ForgeDirection.VALID_DIRECTIONS){
			int i = trans.addEssentia(tag, amountToSend, dir);
			amountToSend -= i;
			amountSent += i;
			if (amountToSend <= 0) break;
		}
		return amount - amountSent;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		for(ForgeDirection dir: ForgeDirection.VALID_DIRECTIONS){
			if (trans.getEssentiaType(dir) == tag && trans.getEssentiaAmount(dir) >= amount) {
				trans.takeEssentia(tag, amount, dir);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		for(ForgeDirection dir: ForgeDirection.VALID_DIRECTIONS){
			if (trans.getEssentiaType(dir) == tag && trans.getEssentiaAmount(dir) >= amount) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		return false;
	}

	@Override
	public int containerContains(Aspect tag) {
		for(ForgeDirection dir: ForgeDirection.VALID_DIRECTIONS){
			if (trans.getEssentiaType(dir) == tag) {
				return trans.getEssentiaAmount(dir);
			}
		}
		return 0;
	}
}
