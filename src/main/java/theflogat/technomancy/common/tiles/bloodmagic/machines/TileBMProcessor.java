package theflogat.technomancy.common.tiles.bloodmagic.machines;

import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;

public class TileBMProcessor extends TileProcessorBase {

    public String owner = "";

    public TileBMProcessor() {
        super(2);
    }

    @Override
    protected boolean getFuel(final ItemStack items, final int multiplier, final int reprocess) {
        final int cost = multiplier * 100 + 1000 * reprocess;
        if (SoulNetworkHandler.getCurrentEssence(owner) < cost) {
            return false;
        }
        if (!worldObj.isRemote) {
            SoulNetworkHandler.setCurrentEssence(owner, (SoulNetworkHandler.getCurrentEssence(owner) - cost));
        }
        return true;
    }

    @Override
    public void writeSyncData(final NBTTagCompound compound) {
        super.writeSyncData(compound);
        compound.setString("Owner", owner);
    }

    @Override
    public void readSyncData(final NBTTagCompound compound) {
        super.readSyncData(compound);
        owner = compound.getString("Owner");
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}
}
