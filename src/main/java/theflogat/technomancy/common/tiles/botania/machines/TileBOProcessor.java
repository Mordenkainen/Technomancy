package theflogat.technomancy.common.tiles.botania.machines;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.IManaReceiver;

public class TileBOProcessor extends TileProcessorBase implements IManaReceiver {
	
	public TileBOProcessor() {
		super(0);
	}
	
	public int mana = 0;
	public static final int maxMana = 1000000;
	
	@Override
	public void update() {
		super.update();
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
					TileEntity tile = world.getTileEntity(new BlockPos(pos.getX() + x, pos.getY(), pos.getZ()+ z));
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
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
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
