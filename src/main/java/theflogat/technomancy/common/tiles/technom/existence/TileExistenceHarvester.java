package theflogat.technomancy.common.tiles.technom.existence;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import theflogat.technomancy.common.tiles.base.TileExistenceRedstoneBase;
import theflogat.technomancy.util.helpers.InvHelper;

public class TileExistenceHarvester extends TileExistenceRedstoneBase implements IInventory {

    public TileExistenceHarvester() {
        super(RedstoneSet.LOW, 10000);
    }

    ItemStack[] items;
    public boolean flag = false;

    @Override
    public void updateEntity() {
        if (items != null && flag) {
            checkInv();
            flag = false;
        }

        if (!worldObj.isRemote && set.canRun(this) && items == null && power >= 30) {
            for (int xx = -5; xx <= 5; xx++) {
                for (int zz = -5; zz <= 5; zz++) {
                    Block b = worldObj.getBlock(xCoord + xx, yCoord, zCoord + zz);
                    if (b instanceof IGrowable) {
                        IGrowable grow = (IGrowable) b;
                        if (!(grow.func_149851_a(worldObj, xCoord + xx, yCoord, zCoord + zz, worldObj.isRemote))) {
                            int meta = worldObj.getBlockMetadata(xCoord + xx, yCoord, zCoord + zz);
                            ArrayList<ItemStack> drops = b.getDrops(worldObj, xCoord + xx, yCoord, zCoord + zz, meta, 1);
                            items = new ItemStack[drops.size()];
                            worldObj.setBlockMetadataWithNotify(xCoord + xx, yCoord, zCoord + zz, b.damageDropped(meta), 3);
                            for (ItemStack drop : drops) {
                                if (drop.getItem() == Item.getItemFromBlock(b)) {
                                    items[drops.indexOf(drop)] = drop;
                                    InvHelper.decrItemStack(items[drops.indexOf(drop)]);
                                } else {
                                    items[drops.indexOf(drop)] = drop;
                                }
                            }
                            power -= 30;
                        }
                    }
                    if (power < 30) {
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void readCustomNBT(final NBTTagCompound comp) {
        super.readCustomNBT(comp);
        flag = true;
        if (comp.hasKey("items")) {
            NBTTagList list = comp.getTagList("items", 10);

            for (int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound item = list.getCompoundTagAt(i);
                int slot = item.getByte("slots");

                if (slot >= 0 && slot < getSizeInventory()) {
                    setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
                }
            }
        }
    }

    @Override
    public void writeCustomNBT(final NBTTagCompound comp) {
        super.writeCustomNBT(comp);
        if (items != null) {
            NBTTagList list = new NBTTagList();

            for (int i = 0; i < this.getSizeInventory(); i++) {
                ItemStack itemstack = this.getStackInSlot(i);

                if (itemstack != null) {
                    NBTTagCompound item = new NBTTagCompound();

                    item.setByte("slots", (byte) i);
                    itemstack.writeToNBT(item);
                    list.appendTag(item);
                }
            }

            comp.setTag("items", list);
        }
    }

    public void checkInv() {
        for (ItemStack item : items) {
            if (item != null) {
                return;
            }
        }
        items = null;
    }

    @Override
    public int getSizeInventory() {
        return items != null ? items.length : 0;
    }

    @Override
    public ItemStack getStackInSlot(final int i) {
        return items != null ? items[i] : null;
    }

    @Override
    public ItemStack decrStackSize(final int slot, final int count) {
        flag = true;
        ItemStack itemstack = getStackInSlot(slot);

        if (itemstack != null) {
            if (itemstack.stackSize <= count) {
                setInventorySlotContents(slot, null);
            } else {
                itemstack = itemstack.splitStack(count);
            }
        }
        return itemstack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(final int i) {
        if (items != null) {
            ItemStack it = items[i].copy();
            setInventorySlotContents(i, null);
            return it;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(final int i, final ItemStack newItems) {
        flag = true;
        if (items != null) {
            items[i] = newItems;
        }
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(final EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(final int i, final ItemStack newItems) {
        if (items != null) {
            return items[i] == null ? true : (items[i].getItem() == newItems.getItem() && items[i].stackSize < 64);
        }
        return false;
    }
}
