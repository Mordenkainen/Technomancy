package theflogat.technomancy.common.tiles.thaumcraft.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import theflogat.technomancy.common.tiles.IUpgradable;
import theflogat.technomancy.common.tiles.base.TileTechnomancy;
import theflogat.technomancy.lib.compat.Thaumcraft;

public class TileAdvDeconTable extends TileTechnomancy implements IInventory, IUpgradable{
	
	public int breakSpeed = 80;
	public Aspect aspect;
	public int breaktime;
	private ItemStack[] items = new ItemStack[1];
	public String owner;

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		owner = comp.getString("owner");
		aspect = Aspect.getAspect(comp.getString("Aspect"));
		NBTTagList list = comp.getTagList("Items", 10);
		items = new ItemStack[getSizeInventory()];
		for (int i = 0; i < list.tagCount(); i++){
			NBTTagCompound stack = list.getCompoundTagAt(i);
			byte slot = stack.getByte("Slot");
			if ((slot >= 0) && (slot < this.items.length)) {
				items[slot] = ItemStack.loadItemStackFromNBT(stack);
			}
		}
		breakSpeed = comp.getInteger("breakspeed");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		comp.setString("owner", owner);
		if (aspect != null) {
			comp.setString("Aspect", aspect.getTag());
		}
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null){
				NBTTagCompound stack = new NBTTagCompound();
				stack.setByte("Slot", (byte)i);
				items[i].writeToNBT(stack);
				list.appendTag(stack);
			}
		}
		comp.setTag("Items", list);
		comp.setInteger("breakspeed", breakSpeed);
	}

	@Override
	public int getSizeInventory() {
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return items[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.items[i] != null)
		{
			if (this.items[i].stackSize <= j)
			{
				ItemStack itemstack = this.items[i];
				this.items[i] = null;
				return itemstack;
			}
			ItemStack itemstack = this.items[i].splitStack(j);
			if (this.items[i].stackSize == 0) {
				this.items[i] = null;
			}
			return itemstack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack it = items[i].copy();
		items[i] = null;
		return it;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack it) {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		items[i] = it;
	}

	@Override
	public String getInventoryName() {
		return "AdvDeconTable";
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
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(xCoord, yCoord, zCoord)<=64;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack j) {
		return false;
	}
	
	@Override
	public boolean canUpdate()
	{
		return true;
	}
	
	@Override
	public void updateEntity() {
		boolean update = false;
		if (!worldObj.isRemote){
			if (breaktime == 0 && canBreak()){
				breaktime = breakSpeed;
				update = true;
			}
			if (breaktime > 0 && canBreak()){
				breaktime -= 1;
				if (breaktime == 0){
					breaktime = 0;
					breakItem();
					update = true;
				}
			}else{
				breaktime = 0;
			}
		}
		
		if(worldObj.getWorldTime() % 20 == 0 && aspect!=null){
			short curr = Thaumcraft.getAspectPoolFor(owner, aspect);
			if(curr<Short.MAX_VALUE){
				curr++;
				Thaumcraft.addAspectsToPool(owner, aspect, curr);
				aspect = null;
			}
		}
		
		if (update){
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	private boolean canBreak(){
		if (items[0] == null || aspect != null) {
			return false;
		}
		AspectList al = Thaumcraft.getObjectAspects(items[0]);
		al = (AspectList) Thaumcraft.getBonusTags(items[0], al);
		if (al == null || al.size() == 0) {
			return false;
		}
		return true;
	}

	private void breakItem(){
		if (canBreak()){
			AspectList al = Thaumcraft.getObjectAspects(this.items[0]);
			al = Thaumcraft.getBonusTags(items[0], al);

			AspectList primals = new AspectList();
			getPrimals(al, primals);
			
			if (worldObj.rand.nextInt(80) < primals.visSize())
				aspect = primals.getAspects()[worldObj.rand.nextInt(primals.getAspects().length)];
			
			items[0].stackSize -= 1;
			if (items[0].stackSize <= 0)
				items[0] = null;
		}
	}
	
	private AspectList getPrimals(AspectList al, AspectList primals) {
		if(al!=null){
			if(primals==null)
				primals = new AspectList();
			
			for(Aspect as : al.getAspects()){
				if(as.isPrimal()){
					if(!primals.aspects.containsKey(as)){
						primals.add(as, 1);
					}
				}else{
					AspectList loop = new AspectList();
					loop.add(as.getComponents()[0], 1);
					loop.add(as.getComponents()[1], 1);
					getPrimals(loop, primals);
				}
			}
		}
		return primals;
	}

	@Override
	public boolean toggleBoost() {
		if(getBoost()){
			breakSpeed = 80;
		}else{
			breakSpeed = 40;
		}
		return getBoost();
	}

	@Override
	public boolean getBoost() {
		return breakSpeed==40;
	}

	@Override
	public void setBoost(boolean newBoost) {
		if(newBoost){
			breakSpeed = 40;
		}else{
			breakSpeed = 80;
		}
	}
	
}

