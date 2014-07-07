package democretes.blocks.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.blocks.machines.tiles.TileBMProcessor;
import democretes.blocks.machines.tiles.TileProcessorBase;
import democretes.blocks.machines.tiles.TileTCProcessor;

public class ContainerBMProcessor extends Container {

	private TileBMProcessor processor;
	
	private int lastTime;
	private int lastMax;
	
	public ContainerBMProcessor(InventoryPlayer inventory, TileBMProcessor processor) {
		this.processor = processor;
		
	    addSlotToContainer(new Slot(processor, 0, 52, 27));
	    addSlotToContainer(new Slot(processor, 1, 107, 27));
	    
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 56 + i * 18));
		    }
		}
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18,  114));
		}		
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting craft) {
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, 0, this.processor.time);
		craft.sendProgressBarUpdate(this, 1, this.processor.maxTime);		
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); i++) {
			ICrafting craft = (ICrafting)this.crafters.get(i);
			if(this.lastTime != this.processor.time) {
				craft.sendProgressBarUpdate(this, 0, this.processor.time);
		    }
			if(this.lastMax != this.processor.maxTime) {
				craft.sendProgressBarUpdate(this, 1, this.processor.maxTime);
			}
		}
		this.lastTime = this.processor.time;
		this.lastMax = this.processor.maxTime;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	  public void updateProgressBar(int i, int j)	  {
		if(i == 0) {
			this.processor.time = j;
		}
		if(i == 1) {
			this.processor.maxTime = j;
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return processor.isUseableByPlayer(entityplayer);
	}	
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i)  {
		ItemStack stack = null;
	    Slot slot = (Slot)this.inventorySlots.get(i);
	    
	    int invTile = this.processor.inv.length;
	    int invPlayer = invTile + 27;
	    int invFull = invTile + 36;
	    if ((slot != null) && (slot.getHasStack()))    {
	    	ItemStack stackInSlot = slot.getStack();
	    	stack = stackInSlot.copy();
	    	if (i == 1)      {
	    		if (!mergeItemStack(stackInSlot, invTile, invFull, true)) {
	    			return null;
	    		}
	    		slot.onSlotChange(stackInSlot, stack);
	    	}else if (i != 0) {
	    		if (processor.isItemValidForSlot(0, (stackInSlot))) {
	    			if (!mergeItemStack(stackInSlot, 0, 1, false)) {
	    				return null;
	    			}
	    		}else if ((i >= invTile) && (i < invPlayer)) {
	    			if (!mergeItemStack(stackInSlot, invPlayer, invFull, false)) {
	    				return null;
	    			}
	    		}else if ((i >= invPlayer) && (i < invFull) && (!mergeItemStack(stackInSlot, invTile, invPlayer, false))) {
	    			return null;
	    		}
	    	}else if (!mergeItemStack(stackInSlot, invTile, invFull, false)) {
	    		return null;
	    	}
	    	if (stackInSlot.stackSize == 0) {
	    		slot.putStack((ItemStack)null);
	    	}else{
	    		slot.onSlotChanged();
	    	}
	    	if(stackInSlot.stackSize == stack.stackSize) {
	    		return null;
	    	}
	    	slot.onPickupFromSlot(player, stackInSlot);
	    }
	    return stack;
	}		

}
