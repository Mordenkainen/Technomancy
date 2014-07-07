package democretes.blocks.dynamos.tiles;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.IManaReceiver;
import democretes.blocks.TMBlocks;
import democretes.compat.Botania;

public class TileFlowerDynamo extends TileDynamoBase implements IManaReceiver {

	public int mana = 0;
	public int maxMana = 100000;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		for(int x = -4; x < 5; x++) {
			for(int z = -4; z < 5; z++) {
				TileEntity tile = worldObj.getBlockTileEntity(xCoord + x, yCoord, zCoord + z);
				if(tile instanceof IManaPool) {
					IManaPool pool = (IManaPool)tile;
					if(pool.getCurrentMana() >= 100 && mana <= maxMana - 100) {
						pool.recieveMana(-100);
						this.mana += 100;
					}
				}
			}
		}	
	}
		
	@Override
	protected boolean canGenerate() {
		if(this.mana >= 1000) {
			return true;
		}
		return false;	
	}	
	
	@Override
	protected void generate() {
		if (this.fuelRF <= 0) {
			this.fuelRF += 5000;
			this.mana -= 1000;
		}
		int energy = calcEnergy();
		this.energyStorage.modifyEnergyStored(energy);
		this.fuelRF -= energy;
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		this.fuelRF = compound.getInteger("RF");
		this.facing = compound.getByte("Facing");
		this.mana = compound.getInteger("Mana");
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		compound.setInteger("RF", fuelRF);	
		compound.setByte("Facing", this.facing);
		compound.setInteger("Mana", this.mana);
	}

	@Override
	public boolean isFull() {
		if(this.mana < this.maxMana) {
			return true;
		}
		return false;
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
			Botania.drawHUD.invoke(null, color, mana, maxMana, TMBlocks.flowerDynamo.getLocalizedName(), res);
		}catch (Exception e){e.printStackTrace();}
	}

}
