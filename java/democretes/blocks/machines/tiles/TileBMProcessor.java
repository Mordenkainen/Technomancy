package democretes.blocks.machines.tiles;

import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import net.minecraft.nbt.NBTTagCompound;

public class TileBMProcessor extends TileProcessorBase {
	
	public String owner;
	
	public TileBMProcessor() {
		tagCompound = "Blood Magic";
	}
	
	@Override
	boolean canProcess() {
		if(this.owner != null) {
			if(SoulNetworkHandler.getCurrentEssence(this.owner) > (250 * (this.multiplier + 1))) {
				return true;
			}
		}
		return false;		
	}
	
	@Override
	void getFuel() {
		if(SoulNetworkHandler.getCurrentEssence(this.owner) > (250 * (this.multiplier + 1))) {
			SoulNetworkHandler.setCurrentEssence(this.owner, (SoulNetworkHandler.getCurrentEssence(this.owner) - (250 * (this.multiplier + 1)))); 
		}
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		compound.setString("Owner", this.owner);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		compound.getString("Owner");
	}
}
