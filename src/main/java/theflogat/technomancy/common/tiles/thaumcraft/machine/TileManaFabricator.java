package theflogat.technomancy.common.tiles.thaumcraft.machine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.tiles.base.TileMachineBase;
import theflogat.technomancy.lib.Rate;
import theflogat.technomancy.lib.compat.Botania;
import vazkii.botania.api.mana.IManaPool;

public class TileManaFabricator extends TileMachineBase implements IManaPool {
	
	public int maxMana = 100000;
	public int mana;
	public int facing;
	public static int cost = Rate.manaFabCost;

	public TileManaFabricator() {
		super(Rate.manaFabCost * 2);
	}
	@Override
	public void updateEntity() {
		if(getEnergyStored()>=cost && mana+100<=maxMana) {
			mana += 100;
			extractEnergy(cost, false);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		mana = compound.getInteger("Mana");
		facing = compound.getInteger("Facing");
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		compound.setInteger("Mana", mana);
		compound.setInteger("Facing", facing);
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
			Botania.drawHUD.invoke(null, color, mana, maxMana, TMBlocks.manaFabricator.getLocalizedName(), res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from.ordinal() == facing;
	}

}
