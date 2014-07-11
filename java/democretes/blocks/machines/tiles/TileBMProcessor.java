package democretes.blocks.machines.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;

public class TileBMProcessor extends TileProcessorBase {

	public String owner = "";

	public TileBMProcessor() {
		super(2);
	}

	@Override
	protected boolean getFuel(ItemStack items, int multiplier) {
		if(!(SoulNetworkHandler.getCurrentEssence(owner) > (75 * (multiplier + 1)))){
			return false;
		}
		SoulNetworkHandler.setCurrentEssence(owner, (SoulNetworkHandler.getCurrentEssence(owner) - (75 * (multiplier + 1))));
		return true;
	}

	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		super.writeCustomNBT(compound);
		compound.setString("Owner", owner);
	}

	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		super.readCustomNBT(compound);
		owner = compound.getString("Owner");
	}
}
