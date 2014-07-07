package democretes.blocks.storage;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.aspects.IEssentiaTransport;
import democretes.compat.thaumcraft.TileJarFillable;

public class TileCreativeJar extends TileJarFillable implements IAspectSource, IEssentiaTransport{

	public int max = 320;
	
	public TileCreativeJar() {
		this.maxAmount = this.max;
	}
	
	@Override
	 public boolean takeFromContainer(Aspect tt, int am)  {
		if ((this.amount >= am) && (tt == this.aspect))	    {
			this.amount -= am;
			if (this.amount <= 0)	      {
				this.aspect = null;
				this.amount = 0;
			}
			this.addToContainer(aspect, am);
			return true;
	   }
	   return false;
	}
	
	@Override
	public int addToContainer(Aspect tt, int am) {
		if (am == 0) {
			return am;
	    }
	    if (((this.amount < this.maxAmount) && (tt == this.aspect)) || (this.amount == 0))	    {
	    	this.aspect = tt;
	    	int added = this.maxAmount - this.amount;
	    	this.amount += added;
	    }
	    return am;
	}
}
