package theflogat.technomancy.common.tiles.base;

import java.util.ArrayList;

import theflogat.technomancy.common.items.base.TMItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class TileProcessorBase extends TileTechnomancy implements ISidedInventory {

	public ItemStack[] inv = new ItemStack[2];
	public boolean isActive;
	public byte facing;
	public int progress = 0;
	public static final int maxTime = 60;
	public String tagCompound;
	public ArrayList<String> ores = new ArrayList<String>();
	public ArrayList<Item> processed = new ArrayList<Item>();
	public Object[][] associatedObj = {
			{
				"oreIron", TMItems.processedIron
			},{
				"oreGold", TMItems.processedGold
			},{
				"oreCopper", TMItems.processedCopper
			},{
				"oreTin", TMItems.processedTin
			},{
				"oreSilver", TMItems.processedSilver
			},{
				"oreLead", TMItems.processedLead
			},{
				"oreNickel", TMItems.processedNickel
			}
	};

	public TileProcessorBase(int tag) {
		tagCompound = processors[tag];
		ores.add("oreIron");processed.add(TMItems.processedIron);
		ores.add("oreGold");processed.add(TMItems.processedGold);
		ores.add("oreCopper");processed.add(TMItems.processedCopper);
		ores.add("oreTin");processed.add(TMItems.processedTin);
		ores.add("oreSilver");processed.add(TMItems.processedSilver);
		ores.add("oreLead");processed.add(TMItems.processedLead);
		ores.add("oreNickel");processed.add(TMItems.processedNickel);
	}
	protected String[] processors = {"Thaumcraft", "Botania", "Blood Magic", "Ars Magica", "Witchery", "Totemic" }; 

	@Override
	public void updateEntity() {
		if(inv[0] == null) {
			return;
		}

		if(canProcess(inv[0])){
			if(isActive){
				if(progress==0){
					isActive = !process();
				}else{
					ItemStack stack = getOutput(inv[0]);
					if(getFuel(stack, stack.getItemDamage(), stack.stackTagCompound.getInteger(tagCompound))){
						progress--;
					}
				}
			}else{
				isActive = true;
				progress = maxTime;
			}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	protected boolean process() {
		ItemStack out = getOutput(inv[0]);if(out==null)return false;
		if(inv[1]==null){
			inv[1] = out;
			inv[0].stackSize--;
			if(inv[0].stackSize==0){
				inv[0] = null;
			}
			return true;
		}else{
			boolean suc = inv[1].getItem()==out.getItem() && inv[1].getItemDamage()==out.getItemDamage()
					&& ItemStack.areItemStackTagsEqual(out, inv[1]);
			if(suc && inv[1].stackSize<64){
				int ns = inv[1].stackSize;
				inv[1] = out;inv[1].stackSize+=ns;
				inv[0].stackSize--;
				if(inv[0].stackSize==0){
					inv[0] = null;
				}
				return true;
			}
		}
		return false;
	}

	protected ItemStack getOutput(ItemStack items){
		if(isOreDictName(items)){
			return addTag(new ItemStack(itemFormOreDictName(items), 1));
		}else if(isProcessed(items) && isProcessable(items.stackTagCompound)){
			ItemStack it = items.copy();it.stackSize = 1;it.setItemDamage(it.getItemDamage()+1);
			return addTag(it);
		}
		return null;
	}

	protected ItemStack addTag(ItemStack items) {
		if(items==null)
			return null;
		
		if(items.stackTagCompound==null)
			items.stackTagCompound = new NBTTagCompound();
		
		items.stackTagCompound.setInteger(tagCompound, items.stackTagCompound.getInteger(tagCompound) + 1);
		
		return items;
	}

	protected boolean canProcess(ItemStack items) {
		return isOreDictName(items) || (isProcessed(items) && isProcessable(items.stackTagCompound));
	}

	protected boolean isProcessable(NBTTagCompound comp) {
		return comp.getInteger(tagCompound)<2;
	}

	protected boolean isOreDictName(ItemStack items) {
		int[] ids = OreDictionary.getOreIDs(items);
		for (int id : ids) {
			if(ores.contains(OreDictionary.getOreName(id))) return true;
		}
		return false;
	}

	protected Item itemFormOreDictName(ItemStack items) {
		for(int i : OreDictionary.getOreIDs(items)){
			for(int j = 0; j<associatedObj.length; j++){
				if(associatedObj[j][0] == OreDictionary.getOreName(i)){
					return (Item) associatedObj[j][1];
				}
			}
		}
		return null;
	}

	protected boolean isProcessed(ItemStack items) {
		return processed.contains(items.getItem());
	}

	protected abstract boolean getFuel(ItemStack items, int multiplier, int reprocess);

	@SideOnly(Side.CLIENT)
	public int getTimeScaled(int j) {
		return progress * j / maxTime;
	}

	@Override
	public void readCustomNBT(NBTTagCompound compound)  {
		facing = compound.getByte("Facing");
		progress = compound.getInteger("Time");
		isActive = compound.getBoolean("Active");	    
	}	

	@Override
	public void writeCustomNBT(NBTTagCompound compound)  {
		compound.setByte("Facing", facing);
		compound.setInteger("Time", progress);
		compound.setBoolean("Active", isActive);		 
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		NBTTagList list = new NBTTagList();

		for(int i = 0; i < this.getSizeInventory(); i++) {
			ItemStack itemstack = this.getStackInSlot(i);

			if(itemstack != null) {
				NBTTagCompound item = new NBTTagCompound();

				item.setByte("SlotsTile", (byte) i);
				itemstack.writeToNBT(item);
				list.appendTag(item);
			}
		}
		
		compound.setTag("ItemsTile", list);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		NBTTagList list = compound.getTagList("ItemsTile", 10);

		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound item = list.getCompoundTagAt(i);
			int slot = item.getByte("SlotsTile");

			if(slot >= 0 && slot < getSizeInventory()) {
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
	}


	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(inv[i] != null) {
			if(inv[i].stackSize <= j) {
				ItemStack stack = this.inv[i];
				inv[i] = null;
				return stack;
			}
			ItemStack stack = inv[i].splitStack(j);
			if (inv[i].stackSize == 0) {
				inv[i] = null;
			}
			return stack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(inv[i] != null) {
			return inv[i];
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		inv[i] = stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack items) {
		return i==0 && (isOreDictName(items) || (isProcessed(items) && items.stackTagCompound!=null && isProcessable(items.stackTagCompound)));
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int i) {
		return new int[] {0, 1};
	}

	@Override
	public boolean canInsertItem(int i, ItemStack stack, int j) {
		if(i==0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		if(i==1) {
			return true;
		}
		return false;
	}

}