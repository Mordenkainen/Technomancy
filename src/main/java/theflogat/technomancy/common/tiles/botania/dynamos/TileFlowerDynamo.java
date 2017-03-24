package theflogat.technomancy.common.tiles.botania.dynamos;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.IManaReceiver;

public class TileFlowerDynamo extends TileDynamoBase implements IManaReceiver {

    public int mana;
    public int maxMana = 100000;

    @Override
    public int extractFuel(final int ener) {
        final float ratio = (ener) / 80F;
        final int val = (int) Math.ceil(20 * ratio);
        if (val > mana) {
            return 0;
        }
        mana -= val;
        return 160;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (mana <= maxMana - 100) {
            drainMana();
        }
    }

    public void drainMana() {
        for (int x = -4; x < 5; x++) {
            for (int z = -4; z < 5; z++) {
                final TileEntity tile = worldObj.getTileEntity(xCoord + x, yCoord, zCoord + z);
                if (tile instanceof IManaPool) {
                    final IManaPool pool = (IManaPool) tile;
                    if (pool.getCurrentMana() >= 100 && mana <= maxMana - 100) {
                        pool.recieveMana(-100);
                        mana += 100;
                    }
                }
            }
        }
    }

    @Override
    public void writeSyncData(final NBTTagCompound comp) {
        super.writeSyncData(comp);
        comp.setInteger("Mana", this.mana);
    }

    @Override
    public void readSyncData(final NBTTagCompound comp) {
        super.readSyncData(comp);
        mana = comp.getInteger("Mana");
    }

    @Override
    public boolean isFull() {
        return mana >= maxMana;
    }

    @Override
    public void recieveMana(final int mana) {
        this.mana += mana;
    }

    @Override
    public boolean canRecieveManaFromBursts() {
        return true;
    }

    @Override
    public int getCurrentMana() {
        return mana;
    }

    public void renderHUD(final Minecraft mc, final ScaledResolution res) {
        BotaniaAPI.internalHandler.drawSimpleManaHUD(0x660000FF, mana, maxMana, TMBlocks.flowerDynamo.getLocalizedName(), res);
    }

}
