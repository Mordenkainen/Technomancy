package democretes.blocks.machines.tiles;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import vazkii.botania.api.ISpecialFlower;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.IManaReceiver;
import democretes.blocks.TMBlocks;
import democretes.compat.Botania;

public class TileBOProcessor extends TileProcessorBase implements IManaReceiver {
	
	public TileBOProcessor() {
		this.tagCompound = "Botania";
	}
	
	public int mana = 0;
	public int maxMana = 100000;
	int flowers;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		for(int x = -4; x < 5; x++) {
			for(int z = -4; z < 5; z++) {
				TileEntity tile = worldObj.getBlockTileEntity(xCoord + x, yCoord, zCoord + z);
				if(tile instanceof IManaPool) {
					IManaPool pool = (IManaPool)tile;
					if(pool.getCurrentMana() >= 500 && this.mana <= this.maxMana - 500) {
						pool.recieveMana(-500);
						this.mana += 500;
					}
				}
			}
		}	
	}
	
	void checkForFlowers() {
		for(int x = -4; x < 5; x++) {
			for(int z = -4; z < 5; z++) {
				TileEntity tile = worldObj.getBlockTileEntity(xCoord + x, yCoord, zCoord + z);
				if(tile instanceof ISpecialFlower) {
					this.flowers += 1;
				}
			}
		}
	}
	@Override
	boolean canProcess() {		
		return this.mana >= ((5000 - (this.flowers*200)) * (this.multiplier + 1));
	}	
	
	@Override
	void getFuel() {
		this.mana -= ((5000 - (this.flowers*200)) * (this.multiplier + 1));
	}

	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		this.mana = compound.getInteger("Mana");
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		compound.setInteger("Mana", this.mana);
	}

	@Override
	public boolean isFull() {
		return this.mana >= this.maxMana;
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
		int color = 0x660000FF;
		try {
			Botania.drawHUD.invoke(null, color, this.mana, this.maxMana, TMBlocks.processorBO.getLocalizedName(), res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
