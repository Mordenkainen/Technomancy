package theflogat.technomancy.common.tiles.botania.dynamos;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.IManaReceiver;

public class TileFlowerDynamo extends TileDynamoBase implements IManaReceiver {

	public int mana = 0;
	public int maxMana = 100000;
	
	@Override
	public int extractFuel(int ener) {
		float ratio = (ener) / 80F;
		int val = (int)Math.ceil(20 * ratio);
		if(val > mana) {
			return 0;
		}
		mana -= val;
		return 160;
	}
	
	@Override
	public void update() {
		super.update();
		if(mana <= maxMana - 100) {
			drainMana();
		}
	}
	
	public void drainMana(){
		for(int x = -4; x < 5; x++) {
			for(int z = -4; z < 5; z++) {
				TileEntity tile = world.getTileEntity(new BlockPos(pos.getX() + x, pos.getY(), pos.getZ()+ z));
				if(tile instanceof IManaPool) {
					IManaPool pool = (IManaPool)tile;
					if(pool.getCurrentMana() >= 100 && mana <= maxMana - 100) {
						pool.recieveMana(-100);
						mana += 100;
					}
				}
			}
		}
	}
	
	@Override
	public void writeSyncData(NBTTagCompound comp) {
		super.writeSyncData(comp);
		comp.setInteger("Mana", this.mana);
	}
	
	@Override
	public void readSyncData(NBTTagCompound comp) {
		super.readSyncData(comp);
		mana = comp.getInteger("Mana");
	}

	@Override
	public boolean isFull() {
		if(mana < maxMana) {
			return false;
		}
		return true;
	}

	@Override
	public void recieveMana(int mana) {
		mana += mana;		
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
		int color = 0x660000FF;
		BotaniaAPI.internalHandler.drawSimpleManaHUD(color, mana, maxMana, TMBlocks.flowerDynamo.getLocalizedName(), res);
	}

}
