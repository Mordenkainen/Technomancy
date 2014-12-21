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
		return null;
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
		for(ForgeDirection dir: ForgeDirection.VALID_DIRECTIONS){
			int i = trans.addEssentia(tag, amount, dir);
			if(i!=0){
				return i;
			}
		}
		return 0;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int containerContains(Aspect tag) {
		// TODO Auto-generated method stub
		return 0;
	}
}
