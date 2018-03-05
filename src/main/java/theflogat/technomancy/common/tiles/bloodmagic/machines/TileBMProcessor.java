package theflogat.technomancy.common.tiles.bloodmagic.machines;

import WayofTime.bloodmagic.util.helper.NetworkHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileBMProcessor extends TileProcessorBase {

	public String owner = "";

	public TileBMProcessor() {
		super(1);
	}

	@Override
	protected boolean getFuel(ItemStack items, int multiplier, int reprocess) {
		int cost = 16;
		if(!(NetworkHelper.getSoulNetwork(owner).getCurrentEssence() > cost)){
			return false;
		}
		if (!world.isRemote) {
			NetworkHelper.getSoulNetwork(owner).setCurrentEssence(NetworkHelper.getSoulNetwork(owner).getCurrentEssence() - cost);
		}
		return true;
	}
	
	@Override
	public void writeSyncData(NBTTagCompound compound) {
		super.writeSyncData(compound);
		compound.setString("Owner", owner);
	}
	
	@Override
	public void readSyncData(NBTTagCompound compound) {
		super.readSyncData(compound);
		owner = compound.getString("Owner");
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return true;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {

	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}
}
