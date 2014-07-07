package democretes.blocks.machines.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.blocks.TileTechnomancy;
import democretes.items.TMItems;

public class TileProcessorBase extends TileTechnomancy implements ISidedInventory {

	public ItemStack[] inv = new ItemStack[2];
	public byte facing;
	public int time = 0;
	public int maxTime = 100;
	public String tagCompound;
	public boolean active;
	public int multiplier = 0;

	public static ItemStack[] pureOres = { 
		new ItemStack(TMItems.processedIron), 
		new ItemStack(TMItems.processedGold), 
		new ItemStack(TMItems.processedCopper), 
		new ItemStack(TMItems.processedTin), 
		new ItemStack(TMItems.processedSilver), 
		new ItemStack(TMItems.processedLead), 
		new ItemStack(TMItems.processedNickel)};

	String[] processors = {"Thaumcraft", "Botania", "Blood Magic", "Ars Magica", "Witchery", "Totemic" }; 

	boolean ore1 = false;
	boolean ore2 = false;
	int f;

	@Override
	public void updateEntity() {
		if(inv[0] == null) {
			time = 0;
		}
		if((ore1 || ore2) && inv[0] != null) {
			if(inv[0].getTagCompound() != null) {
				if(inv[0].stackTagCompound.getBoolean(tagCompound)) {
					return;
				}
			}
			if(canProcess()) {
				if(inv[1] != null) {
					if(ore2) {
						if(inv[1].getItem().itemID != getOreEquivalencies(OreDictionary.getOreID(inv[0])).itemID) {
							active = false;
							return;
						}
					}
					if(ore1) {
						if(inv[0].getItem().itemID != inv[1].getItem().itemID) {
							active = false;
							return;
						}
					}
				}
				if(inv[0] != null) {
					active = true;
					time++;
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}else{
					return;
				}
				if(time >= maxTime) {
					getFuel();
					processStuff(f, ore1, ore2);
					time = 0;
					return;
				}else{
					return;
				}
			}
		}
		active = false;
		if(canProcess() && inv[0] != null) {
			int id = OreDictionary.getOreID(inv[0]);
			if(!OreDictionary.getOres(id).isEmpty()) {
				ore2 = true;
				multiplier = inv[0].getItemDamage() + 1;
			}
			for(int i = 0; i < pureOres.length; i++) {
				if(inv[0].getItem() == pureOres[i].getItem()) {
					if(inv[0].getItemDamage() < 5) {
						f = i;					
						ore1 = true;
						break;
					}
				}
			}
		}
	}

	void processStuff(int j, boolean ore1, boolean ore2) {
		if(ore1) {
			if(inv[1] == null) {
				ItemStack stack = new ItemStack(pureOres[j].getItem(), 1, inv[0].getItemDamage() + 1);
				stack.stackTagCompound = new NBTTagCompound();
				for(int i = 0; i < processors.length; i++) {
					if(inv[0].stackTagCompound.hasKey(processors[i])) {
						stack.stackTagCompound.setBoolean(processors[i], inv[0].stackTagCompound.getBoolean(processors[i]));
					}
				}
				stack.stackTagCompound.setBoolean(tagCompound, true);
				inv[1] = stack.copy();
				if(inv[0] != null) {
					inv[0].stackSize -= 1;
					if(inv[0].stackSize == 0) {
						inv[0] = null;
					}
				}
			}else if(inv[1].getItem() == pureOres[j].getItem()) {
				if(inv[1].stackSize < inv[1].getMaxStackSize()) {
					inv[1].stackSize += 1;
					if(inv[0] != null) {
						inv[0].stackSize -= 1;
						if(inv[0].stackSize == 0) {
							inv[0] = null;
						}
					}
				}
			}
		}
		if(ore2) {
			if(inv[1] == null) {
				ItemStack stack = new ItemStack(getOreEquivalencies(OreDictionary.getOreID(inv[0])), 1, 0);
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setBoolean(tagCompound, true);
				inv[1] = stack.copy();
				if(inv[0] != null) {
					inv[0].stackSize -= 1;
					if(inv[0].stackSize == 0) {
						inv[0] = null;
					}
				}
			}else if(inv[1] != null) {
				if(getOreEquivalencies(OreDictionary.getOreID(inv[0])) == inv[1].getItem()) {
					if(inv[1].stackSize < inv[1].getMaxStackSize()) {
						inv[1].stackSize += 1;
						inv[1].stackTagCompound.setBoolean(tagCompound, true);
						if(inv[0] != null) {
							inv[0].stackSize -= 1;
							if(inv[0].stackSize == 0) {
								inv[0] = null;
							}
						}
					}
				}
			}
		}
	}

	Item getOreEquivalencies(int id) {
		if(OreDictionary.getOreName(id).equals("oreIron")) {
			return pureOres[0].getItem();
		}else if(OreDictionary.getOreName(id).equals("oreGold")) {
			return pureOres[1].getItem();
		}else if(OreDictionary.getOreName(id).equals("oreCopper")) {
			return pureOres[2].getItem();
		}else if(OreDictionary.getOreName(id).equals("oreTin")) {
			return pureOres[3].getItem();
		}else if(OreDictionary.getOreName(id).equals("oreSilver")) {
			return pureOres[4].getItem();
		}else if(OreDictionary.getOreName(id).equals("oreLead")) {
			return pureOres[5].getItem();
		}else if(OreDictionary.getOreName(id).equals("oreNickel")) {
			return pureOres[6].getItem();
		}
		return null;		
	}

	boolean canProcess() {
		return false;
	}	

	void getFuel() {}

	@SideOnly(Side.CLIENT)
	public int getTimeScaled(int j) {		
		return time * j / maxTime;
	}

	public boolean isActive() {
		return active;
	}

	@Override
	public void readCustomNBT(NBTTagCompound compound)  {
		facing = compound.getByte("Facing");
		time = compound.getInteger("Time");
		active = compound.getBoolean("Active");	    
	}	

	@Override
	public void writeCustomNBT(NBTTagCompound compound)  {
		compound.setByte("Facing", facing);
		compound.setInteger("Time", time);
		compound.setBoolean("Active", active);		 
	}	

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inv.length; ++i) {
			if (inv[i] != null) {
				NBTTagCompound compound1 = new NBTTagCompound();
				compound1.setByte("Slot", (byte)i);
				inv[i].writeToNBT(compound1);
				nbttaglist.appendTag(compound1);
			}
		}
		compound.setTag("Items", nbttaglist);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList nbttaglist = compound.getTagList("Items");
		inv = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound compound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			int j = compound1.getByte("Slot") & 255;
			if (j >= 0 && j < inv.length)            {
				inv[j] = ItemStack.loadItemStackFromNBT(compound1);
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return 2;
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
		if(stack != null) {
			if(stack.stackSize > getInventoryStackLimit()) {
				inv[i].stackSize = getInventoryStackLimit();
			}
		}
	}

	@Override
	public String getInvName() {
		return "processorInv" + tagCompound;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
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
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int i) {
		if(i == 0 || i == 1) {
			return new int[] {0};
		}
		return new int[] {1};
	}

	@Override
	public boolean canInsertItem(int i, ItemStack stack, int j) {
		if(j == 1 && stack == inv[0] && i == 0) {
			return true;
		}else if(stack == inv[1]){
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		if(i == 0 && j == 1) {
			return true;
		}
		return false;
	}

}
