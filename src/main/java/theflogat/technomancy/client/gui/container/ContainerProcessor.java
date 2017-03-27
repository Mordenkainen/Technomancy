package theflogat.technomancy.client.gui.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import theflogat.technomancy.common.items.technom.ItemProcessedOre;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import theflogat.technomancy.util.Ore;


public class ContainerProcessor extends Container {

    protected TileProcessorBase processor;
    protected int lastTime;
    protected int lastMax;
    
    public ContainerProcessor(final InventoryPlayer inventory, final TileProcessorBase processor, final int inputOffset, final int outputOffest, final int invOffset, final int hotBarOffset) {
        super();
        this.processor = processor;
        
        addSlotToContainer(new Slot(processor, 0, inputOffset, 27) {

            @Override
            public boolean isItemValid(final ItemStack stack) {
                return stack.getItem() instanceof ItemProcessedOre || Ore.isProcessableOre(stack);
            }
        });
        
        addSlotToContainer(new Slot(processor, 1, outputOffest, 27) {

            @Override
            public boolean isItemValid(final ItemStack stack) {
                return false;
            }
        });
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, invOffset + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventory, i, 8 + i * 18, hotBarOffset));
        }
    }
    
    @Override
    public void addCraftingToCrafters(final ICrafting craft) {
        super.addCraftingToCrafters(craft);
        craft.sendProgressBarUpdate(this, 0, this.processor.progress);
        craft.sendProgressBarUpdate(this, 1, TileProcessorBase.maxTime);
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); i++) {
            final ICrafting craft = (ICrafting) this.crafters.get(i);
            if (this.lastTime != this.processor.progress) {
                craft.sendProgressBarUpdate(this, 0, this.processor.progress);
            }
            if (this.lastMax != TileProcessorBase.maxTime) {
                craft.sendProgressBarUpdate(this, 1, TileProcessorBase.maxTime);
            }
        }
        this.lastTime = this.processor.progress;
        this.lastMax = TileProcessorBase.maxTime;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(final int i, final int j) {
        if (i == 0) {
            this.processor.progress = j;
        }
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer player, final int i) {
        ItemStack stack = null;
        final Slot slot = (Slot) this.inventorySlots.get(i);

        final int invTile = this.processor.inv.length;
        final int invPlayer = invTile + 27;
        final int invFull = invTile + 36;
        if (slot != null && slot.getHasStack()) {
            final ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();
            if (i == 1) {
                if (!mergeItemStack(stackInSlot, invTile, invFull, true)) {
                    return null;
                }
                slot.onSlotChange(stackInSlot, stack);
            } else if (i > 0) {
                if (processor.isItemValidForSlot(0, stackInSlot)) {
                    if (!mergeItemStack(stackInSlot, 0, 1, false)) {
                        return null;
                    }
                } else if (i >= invTile && i < invPlayer) {
                    if (!mergeItemStack(stackInSlot, invPlayer, invFull, false)) {
                        return null;
                    }
                } else if (i >= invPlayer && i < invFull && !mergeItemStack(stackInSlot, invTile, invPlayer, false)) {
                    return null;
                }
            } else if (!mergeItemStack(stackInSlot, invTile, invFull, false)) {
                return null;
            }
            if (stackInSlot.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return processor.isUseableByPlayer(entityplayer);
    }

}
