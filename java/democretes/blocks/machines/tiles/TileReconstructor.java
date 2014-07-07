package democretes.blocks.machines.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import cofh.api.energy.EnergyStorage;

import com.google.common.io.ByteArrayDataInput;

import democretes.compat.Thaumcraft;
import democretes.handlers.ConfigHandler;

public class TileReconstructor extends TileMachineBase implements IAspectContainer, IInventory {

	public AspectList aspects = new AspectList();
	public Aspect aspectDrain;
	public ItemStack[] contents = new ItemStack[2];
	int list = 0;
	boolean pass = true;

	public TileReconstructor() {
		this.capacity = 40000;
		this.maxReceive = 5000;
		this.energyStorage = new EnergyStorage(capacity);
	}
	@Override
	public void updateEntity() {
		try{
			if(!worldObj.isRemote) {
				if(this.contents[0] != null) {
					for(int number : ConfigHandler.blacklist) {
						if(contents[0].itemID == number) {	
							pass = false;
							return;
						}
					}
					if(pass) {
						if(Thaumcraft.getObjectAspects(contents[0]) != null) {						
							AspectList al = Thaumcraft.getObjectAspects(contents[0]);					
							this.aspectDrain = al.getAspects()[list];
							if(this.aspectDrain != null) {
								if(boolean.class.cast(Thaumcraft.drainEssentia.invoke(null, this, aspectDrain, ForgeDirection.UNKNOWN, 6))) {
									this.aspects.add(aspectDrain, 1);
								}
							}
							if(this.aspects != null) {
								if(this.aspects.getAmount(al.getAspects()[list]) >= al.getAmount(al.getAspects()[list])) {
									list++;
									if(list == al.size()) {
										list = 0;
									}
									this.aspectDrain = al.getAspects()[list];		
								}
								if(this.contents[1] != null) {
									if(this.contents[1].stackSize >= this.contents[0].getMaxStackSize()) {
										return;
									}
								}
								int check = 0;
								for(Aspect aspect : this.aspects.getAspects()) {
									if(this.aspects.getAmount(aspect) >= al.getAmount(aspect)) {
										check++;
									}else{
										return;
									}
								}
								if(check == al.size()) {
									for(Aspect aspect : this.aspects.getAspects()) {
										this.aspects.remove(aspect);
									}
								}else{
									return;
								}
								if(this.contents[1] == null) {
									this.contents[1] = this.contents[0].copy();
								}else if(this.contents[1].getItem() == this.contents[0].getItem()) {
									this.contents[1] = new ItemStack(this.contents[1].itemID, this.contents[1].stackSize + 1, this.contents[1].getItemDamage());
								}	
							}
						}
					}
				}
			}
		}catch(Exception e){e.printStackTrace();}
	}


	@Override
	public AspectList getAspects()  {
		return this.aspects;
	}	  

	@Override
	public int addToContainer(Aspect tag, int amount)  {
		if (amount == 0 || this.contents[0] == null) {
			return amount;
		}
		this.aspects.add(tag, amount);
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		return amount;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount)  {
		if(this.aspects != null) {
			if(this.aspects.getAmount(tag) >= amount) {
				this.aspects.reduce(tag, amount);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amt) {
		for(Aspect aspect : this.aspects.getAspects()) {
			if(tag == aspect && this.aspects.getAmount(aspect) == amt) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doesContainerContain(AspectList ot)  {
		return false;
	}

	@Override
	public void setAspects(AspectList aspects) {}

	@Override
	public boolean canUpdate()  {
		return true;
	}	

	@Override
	public boolean takeFromContainer(AspectList ot) {
		return false;
	}		

	@Override
	public int containerContains(Aspect tag)  {
		return 0;
	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.contents[i];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)    {
		if (this.contents[slot] != null) {
			ItemStack itemstack;

			if (this.contents[slot].stackSize <= amount) {
				itemstack = this.contents[slot];
				this.contents[slot] = null;
				this.onInventoryChanged();
				return itemstack;
			}else{
				itemstack = this.contents[slot].splitStack(amount);

				if (this.contents[slot].stackSize == 0) {
					this.contents[slot] = null;
				}
				this.onInventoryChanged();
				return itemstack;
			}
		}else{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.contents[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "reconstructorInv";
	}

	@Override
	public boolean isInvNameLocalized() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
	public static void handlePacket(ByteArrayDataInput data) {

	}

}
