package theflogat.technomancy.common.tiles.botania.machines;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.IManaReceiver;

public class TileBOProcessor extends TileProcessorBase implements IManaReceiver {
	
	public TileBOProcessor() {
		super(1);
	}
	
	public int mana = 0;
	public static final int maxMana = 1000000;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		perform();
	}
	
	@Override
	protected boolean getFuel(ItemStack items, int multiplier, int reprocess) {
		int cost = multiplier * 150 + 1500 * reprocess;
		if(cost > mana) {
			return false;
		}
		mana -= cost;
		return true;
	}
	
	protected void perform() {
		if (!isFull()) {
			for(int x = -4; x < 5; x++) {
				for(int z = -4; z < 5; z++) {
					TileEntity tile = worldObj.getTileEntity(xCoord + x, yCoord, zCoord + z);
					if(tile instanceof IManaPool) {
						IManaPool pool = (IManaPool)tile;
						int toRecieve = Math.min(pool.getCurrentMana(), Math.min(maxMana - mana, 5000));
						if (toRecieve > 0) {
							pool.recieveMana(-toRecieve);
							mana += toRecieve;
						}
					}
				}
			}
		}
	}
	
	@Override
	public void writeSyncData(NBTTagCompound compound) {
		super.writeSyncData(compound);
		compound.setInteger("Mana", mana);
	}
	
	@Override
	public void readSyncData(NBTTagCompound compound) {
		super.readSyncData(compound);
		mana = compound.getInteger("Mana");
	}

	@Override
	public boolean isFull() {
		return mana >= maxMana;
	}

	@Override
	public void recieveMana(int mana) {
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

	public void renderHUD(Minecraft mc, ScaledResolution res) {
		int color = Color.CYAN.getRGB();
		BotaniaAPI.internalHandler.drawSimpleManaHUD(color, mana, maxMana, TMBlocks.processorBO.getLocalizedName(), res);
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
