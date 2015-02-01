package theflogat.technomancy.common.tiles.technom;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import theflogat.technomancy.api.renderers.ModelCatalystSpecial;
import theflogat.technomancy.api.rituals.Ritual;
import theflogat.technomancy.api.rituals.RitualRegistry;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;

public class TileCatalyst extends TileTechnomancy {
	
	public int remCount = -1;
	public ModelCatalystSpecial specialRender = null;
	public ResourceLocation textLoc = null;
	
	@Override
	public void updateEntity() {
		if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
			activateRitual();
		}
		
		if(remCount!=-1){
			if(remCount==0){
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}else{
				remCount--;
			}
		}
	}
	
	public void activateRitual() {
		for(Ritual r : RitualRegistry.getRituals()){
			if(r!=null && r.isCoreComplete(worldObj, xCoord, yCoord, zCoord) && r.isFrameComplete(worldObj, xCoord, yCoord, zCoord)){
				if(r.applyEffect(worldObj, xCoord, yCoord, zCoord))
					r.afterEffect(worldObj, xCoord, yCoord, zCoord);
			}
		}
	}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		
	}

}
