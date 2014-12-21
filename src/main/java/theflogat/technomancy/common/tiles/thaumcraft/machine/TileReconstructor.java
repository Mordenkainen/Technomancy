package theflogat.technomancy.common.tiles.thaumcraft.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.aspects.IEssentiaTransport;
import theflogat.technomancy.common.tiles.base.TileMachineBase;
import theflogat.technomancy.handlers.compat.Thaumcraft;
import theflogat.technomancy.handlers.util.Coords;
import theflogat.technomancy.handlers.util.InventoryHelper;
import cofh.api.energy.EnergyStorage;

public class TileReconstructor extends TileMachineBase /*implements IAspectSource, IEssentiaTransport, IInventory*/ {

	public AspectList aspects = new AspectList();
	public ItemStack items;

	public TileReconstructor() {
		super(500000);
	}
//	
//	@Override
//	public void updateEntity() {
//		try{
//			if(!worldObj.isRemote) {
//				if(canReproduce()){
//					if(ener.getEnergyStored()>5000 && eject()){
//						ener.extractEnergy(5000, false);
//					}
//				}else{
//					fill();
//				}
//			}
//		}catch(Exception e){e.printStackTrace();}
//	}
//	
//	void fill() {
//		TileEntity te = Thaumcraft.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.UP);
//		if (te != null && items!=null) {
//			IEssentiaTransport ic = (IEssentiaTransport)te;
//			if (!ic.canOutputTo(ForgeDirection.DOWN)) {
//				return;
//			}
//			Aspect ta = null;
//			
//			AspectList al = Thaumcraft.getObjectAspects(items);
//			if(al==null)
//				return;
//			
//			if (getSuctionType(null)!=null) {
//				ta = getSuctionType(null);
//			} else if ((ic.getEssentiaAmount(ForgeDirection.DOWN) > 0) && 
//					(ic.getSuctionAmount(ForgeDirection.DOWN) < getSuctionAmount(ForgeDirection.UP)) && (getSuctionAmount(ForgeDirection.UP) >= ic.getMinimumSuction())) {
//				ta = ic.getEssentiaType(ForgeDirection.DOWN);
//			}
//			if ((ta != null) && (ic.getSuctionAmount(ForgeDirection.DOWN) < getSuctionAmount(ForgeDirection.UP))) {
//				addToContainer(ta, ic.takeEssentia(ta, 1, ForgeDirection.DOWN));
//			}
//		}
//	}
//	
//	private boolean eject() {
//		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
//			Coords coords = new Coords(xCoord, yCoord, zCoord, worldObj);
//			if(coords.getTile()!=null && coords.getTile() instanceof IInventory){
//				ItemStack it = items.copy();it.stackSize = 1;
//				return InventoryHelper.insert((IInventory)coords.getTile(), it, dir.getOpposite())==null;
//			}
//		}
//		return false;
//	}
//
//	private boolean canReproduce() {
//		if(items==null)
//			return false;
//		
//		AspectList al = Thaumcraft.getObjectAspects(items);
//		if(al==null)
//			return false;
//		
//		for(Aspect as : al.getAspects()){
//			int neededAmount = al.getAmount(as);
//			if(aspects.aspects.containsKey(as)){
//				if(neededAmount - aspects.getAmount(as)>0){
//					return false;
//				}
//			}else{
//				return false;
//			}
//		}
//		
//		return true;
//	}
//
//	@Override
//	public int getSizeInventory() {
//		return 1;
//	}
//
//	@Override
//	public ItemStack getStackInSlot(int i) {
//		return items;
//	}
//
//	@Override
//	public ItemStack decrStackSize(int i, int j) {
//		ItemStack it = items.copy();it.stackSize = Math.max(0, it.stackSize - j);if(it.stackSize==0)it=null;
//		return it;
//	}
//
//	@Override
//	public ItemStack getStackInSlotOnClosing(int i) {
//		ItemStack it = items.copy(); items = null;
//		return it;
//	}
//
//	@Override
//	public void setInventorySlotContents(int i, ItemStack itemstack) {
//		items = itemstack;
//	}
//
//	@Override
//	public int getInventoryStackLimit() {
//		return 1;
//	}
//
//	@Override
//	public boolean isUseableByPlayer(EntityPlayer player) {
//		return player.getDistanceSq(xCoord, yCoord, zCoord) <= 64;
//	}
//
//	@Override
//	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
//		return true;
//	}
//
//	@Override
//	public AspectList getAspects() {
//		return aspects;
//	}
//
//	@Override
//	public void setAspects(AspectList al) {}
//
//	@Override
//	public boolean doesContainerAccept(Aspect as) {
//		if(items==null)
//			return false;
//		
//		AspectList al = Thaumcraft.getObjectAspects(items);
//		if(al==null)
//			return false;
//		
//		if(al.aspects.containsKey(as)){
//			int neededAmount = al.getAmount(as);
//			if(aspects.aspects.containsKey(as)){
//				if(neededAmount - aspects.getAmount(as) > 0){
//					return true;
//				}
//			}else{
//				return true;
//			}
//		}
//		return false;
//	}
//
//	@Override
//	public int addToContainer(Aspect as, int amount) {
//		if(items==null)
//			return 0;
//		
//		AspectList al = Thaumcraft.getObjectAspects(items);
//		if(al==null)
//			return 0;
//		
//		if(al.aspects.containsKey(as)){
//			int neededAmount = al.getAmount(as);
//			if(aspects.aspects.containsKey(as)){
//				int ex = Math.max(neededAmount - aspects.getAmount(as), amount);
//				aspects.add(as, ex);
//				return amount - ex;
//			}else{
//				int ex = Math.max(neededAmount, amount);
//				aspects.add(as, ex);
//				return amount - ex;
//			}
//		}
//		return 0;
//	}
//
//	@Override
//	public boolean takeFromContainer(Aspect as, int amount) {
//		return false;
//	}
//
//	@Override
//	public boolean takeFromContainer(AspectList al) {
//		return false;
//	}
//
//	@Override
//	public boolean doesContainerContainAmount(Aspect as, int amount) {
//		return aspects.aspects.containsKey(as) && aspects.getAmount(as)>=amount;
//	}
//
//	@Override
//	public boolean doesContainerContain(AspectList al) {
//		return false;
//	}
//
//	@Override
//	public int containerContains(Aspect as) {
//		return aspects.aspects.containsKey(as) ? aspects.getAmount(as) : 0;
//	}
//
	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		aspects.readFromNBT(comp);
		items = ItemStack.loadItemStackFromNBT(comp);
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		aspects.writeToNBT(comp);
		items.writeToNBT(comp);
	}
//
//	@Override
//	public boolean isConnectable(ForgeDirection dir) {
//		return true;
//	}
//
//	@Override
//	public boolean canInputFrom(ForgeDirection dir) {
//		return true;
//	}
//
//	@Override
//	public boolean canOutputTo(ForgeDirection dir) {
//		return false;
//	}
//
//	@Override
//	public void setSuction(Aspect paramAspect, int paramInt) {}
//
//	@Override
//	public Aspect getSuctionType(ForgeDirection dir) {
//		if(items==null)
//			return null;
//		
//		AspectList al = Thaumcraft.getObjectAspects(items);
//		if(al==null)
//			return null;
//		
//		for(Aspect as : al.getAspects()){
//			int neededAmount = al.getAmount(as);
//			if(aspects.aspects.containsKey(as)){
//				if(neededAmount - aspects.getAmount(as)>0){
//					return as;
//				}
//			}else{
//				return as;
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public int getSuctionAmount(ForgeDirection dir) {
//		if(items==null)
//			return 0;
//		return 128;
//	}
//
//	@Override
//	public int takeEssentia(Aspect as, int amt, ForgeDirection dir) {
//		return takeFromContainer(as, amt) ? 0 : amt;
//	}
//
//	@Override
//	public int addEssentia(Aspect as, int amt, ForgeDirection dir) {
//		return addToContainer(as, amt);
//	}
//
//	@Override
//	public Aspect getEssentiaType(ForgeDirection dir) {
//		return getSuctionType(dir);
//	}
//
//	@Override
//	public int getEssentiaAmount(ForgeDirection dir) {
//		return 0;
//	}
//
//	@Override
//	public int getMinimumSuction() {
//		return 128;
//	}
//
//	@Override
//	public boolean renderExtendedTube() {
//		return true;
//	}
//
//	@Override
//	public boolean canConnectEnergy(ForgeDirection from) {
//		return true;
//	}
//
//	@Override
//	public String getInventoryName() {
//		return null;
//	}
//
//	@Override
//	public boolean hasCustomInventoryName() {
//		return false;
//	}
//
//	@Override
//	public void openInventory() {}
//
//	@Override
//	public void closeInventory() {}
}