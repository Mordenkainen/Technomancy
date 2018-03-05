package theflogat.technomancy.client.gui.container;

import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.common.items.technom.ItemProcessedOre;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import theflogat.technomancy.common.tiles.botania.machines.TileBOProcessor;
import theflogat.technomancy.util.Ore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBOProcessor extends Container {

	private TileBOProcessor processor;
	
	private int lastTime;
	private int lastMax;
	
	public ContainerBOProcessor(InventoryPlayer inventory, TileBOProcessor processor) {
		this.processor = processor;
		
	    addSlotToContainer(new Slot(processor, 0, 52, 27) {
	    	@Override
	    	public boolean isItemValid(ItemStack stack)
	        {
	            return stack.getItem() instanceof ItemProcessedOre || Ore.isProcessableOre(stack);
	        }
	    });
	    addSlotToContainer(new Slot(processor, 1, 108, 27) {
	    	@Override
	    	public boolean isItemValid(ItemStack stack)
	        {
	            return false;
	        }
	    });
	    
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
	public void addListener(IContainerListener listener) {
		listener.sendWindowProperty(this, 0, this.processor.progress);
		listener.sendWindowProperty(this, 1, TileProcessorBase.maxTime);
		super.addListener(listener);
	}

	@Override
	public void detectAndSendChanges() {
		for (int i = 0; i < this.listeners.size(); i++) {
			IContainerListener craft = (IContainerListener)this.listeners.get(i);
			if(this.lastTime != this.processor.progress) {
				craft.sendWindowProperty(this, 0, this.processor.progress);
			}
			if(this.lastMax != TileProcessorBase.maxTime) {
				craft.sendWindowProperty(this, 1, TileProcessorBase.maxTime);
			}
		}
		this.lastTime = this.processor.progress;
		this.lastMax = TileProcessorBase.maxTime;
		super.detectAndSendChanges();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	  public void updateProgressBar(int i, int j)	  {
		if(i == 0) {
			this.processor.progress = j;
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return processor.isUsableByPlayer(entityplayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i)  {
		ItemStack stack = ItemStack.EMPTY;
	    Slot slot = (Slot)this.inventorySlots.get(i);
	    
	    int invTile = this.processor.inv.size();
	    int invPlayer = invTile + 27;
	    int invFull = invTile + 36;
	    if ((slot != null) && (slot.getHasStack()))    {
	    	ItemStack stackInSlot = slot.getStack();
	    	stack = stackInSlot.copy();
	    	if (i == 1)      {
	    		if (!mergeItemStack(stackInSlot, invTile, invFull, true)) {
	    			return ItemStack.EMPTY;
	    		}
	    		slot.onSlotChange(stackInSlot, stack);
	    	}else if (i != 0) {
	    		if (processor.isItemValidForSlot(0, (stackInSlot))) {
	    			if (!mergeItemStack(stackInSlot, 0, 1, false)) {
	    				return ItemStack.EMPTY;
	    			}
	    		}else if ((i >= invTile) && (i < invPlayer)) {
	    			if (!mergeItemStack(stackInSlot, invPlayer, invFull, false)) {
	    				return ItemStack.EMPTY;
	    			}
	    		}else if ((i >= invPlayer) && (i < invFull) && (!mergeItemStack(stackInSlot, invTile, invPlayer, false))) {
	    			return ItemStack.EMPTY;
	    		}
	    	}else if (!mergeItemStack(stackInSlot, invTile, invFull, false)) {
	    		return ItemStack.EMPTY;
	    	}
	    	if (stackInSlot.getCount() == 0) {
	    		slot.putStack((ItemStack)ItemStack.EMPTY);
	    	}else{
	    		slot.onSlotChanged();
	    	}
	    	if(stackInSlot.getCount() == stack.getCount()) {
	    		return ItemStack.EMPTY;
	    	}
	    	slot.onTake(player, stackInSlot);
	    }
	    return stack;
	}	

}
