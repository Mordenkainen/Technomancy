package democretes.blocks.machines.tiles;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import vazkii.botania.api.mana.IManaPool;
import cofh.api.energy.EnergyStorage;
import democretes.blocks.TMBlocks;
import democretes.compat.Botania;

public class TileManaFabricator extends TileMachineBase implements IManaPool {
	
	public int maxMana = 100000;
	public int mana;
	public int facing;

	public TileManaFabricator() {
		this.capacity = 30000;
		this.maxReceive = 10000;
		this.energyStorage = new EnergyStorage(capacity);
	}
	@Override
	public void updateEntity() {
		if(this.getEnergyStored() > 20000 && this.mana + 100 < this.maxMana) {
			this.mana += 100;
			this.energyStorage.extractEnergy(200000, false);
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		this.mana = compound.getInteger("Mana");
		this.facing = compound.getInteger("Facing");
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		compound.setInteger("Mana", this.mana);
		compound.setInteger("Facing", this.facing);
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
		return false;
	}

	@Override
	public int getCurrentMana() {
		return mana;
	}

	@Override
	public boolean isOutputtingPower() {
		return false;
	}
	
	public void renderHUD(Minecraft mc, ScaledResolution res) {
		int color = 0x660000FF;
		try {
			Botania.drawHUD.invoke(null, color, this.mana, this.maxMana, TMBlocks.manaFabricator.getLocalizedName(), res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean canInterface(ForgeDirection from) {
		return from==ForgeDirection.DOWN;
	}

}
