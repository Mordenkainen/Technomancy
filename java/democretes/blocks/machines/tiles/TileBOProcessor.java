package democretes.blocks.machines.tiles;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.IManaReceiver;
import democretes.blocks.TMBlocks;
import democretes.compat.Botania;

public class TileBOProcessor extends TileProcessorBase implements IManaReceiver {
	
	public TileBOProcessor() {
		super(1);
	}
	
	public int mana = 0;
	public static final int maxMana = 1000000;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		for(int x = -4; x < 5; x++) {
			for(int z = -4; z < 5; z++) {
				TileEntity tile = worldObj.getBlockTileEntity(xCoord + x, yCoord, zCoord + z);
				if(tile instanceof IManaPool) {
					IManaPool pool = (IManaPool)tile;
					if(pool.getCurrentMana() >= 5000 && mana <= maxMana - 5000) {
						pool.recieveMana(-5000);
						mana += 5000;
					}else if(pool.getCurrentMana() >= 2000 && mana <= maxMana - 2000) {
						pool.recieveMana(-2000);
						mana += 2000;
					}else if(pool.getCurrentMana() >= 500 && mana <= maxMana - 500) {
						pool.recieveMana(-500);
						mana += 500;
					}
				}
			}
		}	
	}
	
	@Override
	protected boolean getFuel(ItemStack items, int multiplier) {
		if(!(mana >= 35 * multiplier)){
			return false;
		}
		mana -= 35 * (multiplier);
		return true;
	}

	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		super.readCustomNBT(compound);
		mana = compound.getInteger("Mana");
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		super.writeCustomNBT(compound);
		compound.setInteger("Mana", mana);
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
		int color = Color.LIGHT_GRAY.getRGB();
		try {
			Botania.drawHUD.invoke(null, color, mana, maxMana, TMBlocks.processorBO.getLocalizedName(), res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}