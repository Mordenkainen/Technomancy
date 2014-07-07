package democretes.items;

import cofh.api.energy.IEnergyContainerItem;
import democretes.compat.Thaumcraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.api.wands.IWandRodOnUpdate;

public class ElectricWandUpdate implements IWandRodOnUpdate{

	final int maxEner = 20000;

	@Override
	public void onUpdate(ItemStack itemstack, EntityPlayer player) {
		try{
			if(itemstack != null) {
				if(Thaumcraft.ItemWandCasting.isInstance(itemstack.getItem())) {
					if(Thaumcraft.getAspectsWithRoom.invoke(itemstack.getItem(), itemstack)!=null) {
						int energy = itemstack.stackTagCompound.getInteger("energy");
						for(Aspect al : Aspect.getPrimalAspects()) {
							int slot = checkInventory(player);
							if(slot!=-1) {
								IEnergyContainerItem capacitor = (IEnergyContainerItem)player.inventory.mainInventory[slot].getItem();

								while(energy<maxEner){
									if(slot!=-1) {
										if(capacitor.extractEnergy(player.inventory.mainInventory[slot], 1000, true)>0) {
											energy += capacitor.extractEnergy(player.inventory.mainInventory[slot], 1000, false);
										}else{
											continue;
										}
									}
								}
							}
							
							while(energy >= 10000) {
								if(isSmaller(Thaumcraft.getVis.invoke(itemstack.getItem(), itemstack, al),
										Thaumcraft.getMaxVis.invoke(itemstack.getItem(), itemstack))){
									Thaumcraft.addVis.invoke(itemstack.getItem(), itemstack, al, 1, true);
									energy -= 10000;
								}
							}
						}
						
						itemstack.stackTagCompound.setInteger("energy", energy);
					}
				}
			}
		}catch(Exception e){e.printStackTrace();}
	}
	
	boolean isSmaller(Object i, Object j){
		return ((Integer)i).intValue()< ((Integer)j).intValue();
	}

	int checkInventory(EntityPlayer player) {
		ItemStack[] inv = player.inventory.mainInventory;
		for(int i = 0; i < inv.length; i++) {
			if(inv[i] != null && inv[i].getItem() instanceof IEnergyContainerItem) {
				if(((IEnergyContainerItem)inv[i].getItem()).extractEnergy(inv[i], 1000, true)>0) {
					return i;
				}
			}
		}
		return -1;
	}
}