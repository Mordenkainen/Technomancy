package theflogat.technomancy.common.tiles.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import theflogat.technomancy.common.items.technom.ItemProcessedOre;
import theflogat.technomancy.util.Ore;

public abstract class TileProcessorBase extends TileTechnomancy implements ISidedInventory {

	public NonNullList<ItemStack> inv = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);
	public boolean isActive;
	public int progress = 0;
	public static final int maxTime = 60;
	public String tagCompound;
	
	public TileProcessorBase(int tag) {
		tagCompound = processors[tag];
	}
	protected String[] processors = {"Botania", "Blood Magic" };

	@Override
	public void update() {
		if (!world.isRemote) {
			if(inv.get(0).isEmpty()) {
				progress = 0;
				isActive = false;
				markDirty();
				world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
				return;
			}
	
			if(canProcess(inv.get(0))) {
				if(isActive) {
					if(progress==0) {
						isActive = !process();
					} else {
						ItemStack stack = getOutput(inv.get(0));
						if(getFuel(stack, stack.getCount(), 5)) {
							progress--;
						}
					}
				} else {
					isActive = true;
					progress = maxTime;
				}
				markDirty();
				world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
			}
		}
	}

	protected boolean process() {
		ItemStack out = getOutput(inv.get(0));
		if(out.isEmpty()) {
			return false;
		}
		if(inv.get(1).isEmpty()) {
			inv.set(1, out);
			inv.get(0).shrink(1);
			if(inv.get(0).getCount()==0) {
				inv.set(0, ItemStack.EMPTY);
			}
			return true;
		} else {
			boolean suc = inv.get(1).getItem() == out.getItem() && inv.get(1).getItemDamage() == out.getItemDamage()
					&& ItemStack.areItemStackTagsEqual(out, inv.get(1));
			if(suc && inv.get(1).getCount() < 64) {
				inv.get(1).grow(1);
				inv.get(0).shrink(1);
				if(inv.get(0).getCount() == 0) {
					inv.set(0, ItemStack.EMPTY);
				}
				return true;
			}
		}
		return false;
	}

	protected ItemStack getOutput(ItemStack items){
		if(Ore.isProcessableOre(items)) {
			return addTag(new ItemStack(itemFromOreDictName(items), 1));
		} else if(isProcessed(items) && isProcessable(items.getTagCompound())) {
			ItemStack it = items.copy();
			it.setCount(1);
			it.setItemDamage(it.getItemDamage() + 1);
			return addTag(it);
		}
		return ItemStack.EMPTY;
	}

	protected ItemStack addTag(ItemStack items) {
		if(items.isEmpty())
			return ItemStack.EMPTY;
		
		if(items.getTagCompound() == null) {
			items.setTagCompound(new NBTTagCompound());
		}
		
		items.getTagCompound().setInteger(tagCompound, items.getTagCompound().getInteger(tagCompound) + 1);
		
		return items;
	}

	protected boolean canProcess(ItemStack items) {
		return Ore.isProcessableOre(items) || (isProcessed(items) && items.getTagCompound()!=null && isProcessable(items.getTagCompound()));
	}

	protected boolean isProcessable(NBTTagCompound comp) {
		return comp.getInteger(tagCompound) < 2;
	}

	protected Item itemFromOreDictName(ItemStack items) {
		for(int i : OreDictionary.getOreIDs(items)) {
			for(int j = 0; j < Ore.oreNames.size(); j++) {
				if(Ore.oreNames.get(j) == OreDictionary.getOreName(i)) {
					return Ore.ores.get(j).getPure();
				}
			}
		}
		return null;
	}

	protected boolean isProcessed(ItemStack items) {
		return items.getItem() instanceof ItemProcessedOre;
	}

	protected abstract boolean getFuel(ItemStack items, int multiplier, int reprocess);

	@SideOnly(Side.CLIENT)
	public int getTimeScaled(int j) {
		return progress * j / maxTime;
	}

	@Override
	public void readCustomNBT(NBTTagCompound compound)  {
		NBTTagList list = compound.getTagList("ItemsTile", 10);
		inv = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);

		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound item = list.getCompoundTagAt(i);
			int slot = item.getByte("SlotsTile");

			if(slot >= 0 && slot < getSizeInventory()) {
				inv.set(slot, new ItemStack(item));
			}
		}
	}	

	@Override
	public void writeCustomNBT(NBTTagCompound compound)  {
		NBTTagList list = new NBTTagList();

		for(int i = 0; i < this.getSizeInventory(); i++) {
			ItemStack itemstack = this.getStackInSlot(i);

			if(!itemstack.isEmpty()) {
				NBTTagCompound item = new NBTTagCompound();

				item.setByte("SlotsTile", (byte) i);
				itemstack.writeToNBT(item);
				list.appendTag(item);
			}
		}
		
		compound.setTag("ItemsTile", list);
	}
	
	public void writeSyncData(NBTTagCompound compound) {
		compound.setInteger("Time", progress);
		compound.setBoolean("Active", isActive);
	}
	
	public void readSyncData(NBTTagCompound compound) {
		progress = compound.getInteger("Time");
		isActive = compound.getBoolean("Active");
	}
	
	@Override
	public int getSizeInventory() {
		return inv.size();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv.get(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(!inv.get(i).isEmpty()) {
			if(inv.get(i).getCount() <= j) {
				ItemStack stack = this.inv.get(i);
				inv.set(i, ItemStack.EMPTY);
				return stack;
			}
			ItemStack stack = inv.get(i).splitStack(j);
			if (inv.get(i).getCount() == 0) {
				inv.set(i, ItemStack.EMPTY);
			}
			return stack;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		inv.set(i, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack items) {
		return i == 0 && canProcess(items);
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] {0, 1};
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStackIn, EnumFacing direction) {
		return i == 0;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, EnumFacing direction) {
		return i == 1;
	}
}