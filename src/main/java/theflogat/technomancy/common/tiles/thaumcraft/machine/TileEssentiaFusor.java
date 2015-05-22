package theflogat.technomancy.common.tiles.thaumcraft.machine;

import java.util.ArrayList;
import java.util.HashMap;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemEssence;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.base.TileMachineBase;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.lib.handlers.Rate;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEssentiaFusor extends TileMachineBase implements IAspectContainer, IEssentiaTransport, IRedstoneSensitive {
	private enum SideType {
		INPUT,
		OUTPUT,
		NONE;		
	}

	private class SideInfo {
		public Aspect aspect = null;
		public int amount = 0;
		public SideType type = SideType.NONE;
		public ForgeDirection side = ForgeDirection.UNKNOWN;

		public SideInfo(ForgeDirection side) {
			this.side = side;
		}

		public NBTTagCompound save() {
			NBTTagCompound tags = new NBTTagCompound();
			tags.setInteger("amount", amount);
			tags.setByte("type", (byte)type.ordinal());
			tags.setByte("side", (byte)side.ordinal());
			if(aspect != null) {
				tags.setString("aspect", aspect.getTag());
			}
			return tags;
		}

		public void load(NBTTagCompound tags) {
			aspect = tags.hasKey("aspect") ? Aspect.getAspect(tags.getString("aspect")) : null;
			type = SideType.values()[(int)tags.getByte("type")];
			amount = tags.getInteger("amount");
		}
	}

	private HashMap<ForgeDirection, SideInfo> sides = new HashMap<ForgeDirection, SideInfo>();
	private static final int MAX_AMOUNT = 64;
	public RedstoneSet set = RedstoneSet.HIGH;

	public TileEssentiaFusor() {
		super(Rate.fusorCost * 10);
		sides.put(ForgeDirection.EAST, new SideInfo(ForgeDirection.EAST));
		sides.put(ForgeDirection.WEST, new SideInfo(ForgeDirection.WEST));
		sides.put(ForgeDirection.NORTH, new SideInfo(ForgeDirection.NORTH));
		sides.put(ForgeDirection.SOUTH, new SideInfo(ForgeDirection.SOUTH));
	}

	@Override
	public void updateEntity() {
		boolean flag = false;
		flag |= fill();
		flag |= fuse();
		if(flag) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			markDirty();
		}
	}

	private boolean fill() {
		boolean ret = false;
		for (SideInfo side : getInputSides()) {
			if(side.amount < MAX_AMOUNT) {
				TileEntity te = Thaumcraft.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, side.side);
				if (te != null) {
					IEssentiaTransport ic = (IEssentiaTransport)te;
					Aspect ta = ic.getEssentiaType(side.side.getOpposite());
					if (ta == side.aspect && ic.getEssentiaAmount(side.side.getOpposite()) > 0 && ic.getSuctionAmount(side.side.getOpposite()) <
							getSuctionAmount(side.side) && getSuctionAmount(side.side) >= ic.getMinimumSuction()) {
						addEssentia(ta, ic.takeEssentia(ta, 1, side.side.getOpposite()), side.side);
						ret = true;
					}
				}
			}
		}
		return ret;
	}

	private boolean fuse() {
		boolean ret = false;
		if(set.canRun(this) && outputMarked() && getOutputSideInfo().amount < MAX_AMOUNT && getEnergyStored(ForgeDirection.UNKNOWN) >= Rate.fusorCost) {
			SideInfo[] inputSides = getInputSides();
			if(inputSides.length == 2) {
				if(inputSides[0].amount > 0 && inputSides[1].amount > 0) {
					extractEnergy(ForgeDirection.UNKNOWN, Rate.fusorCost, false);
					inputSides[0].amount--;
					inputSides[1].amount--;
					getOutputSideInfo().amount++;
					ret = true;
				}
			}
		}
		return ret;
	}

	public boolean isSideOccupied(ForgeDirection side) {
		return sides.get(side).type != SideType.NONE;
	}

	public boolean markSide(ForgeDirection side, ItemStack stack) {
		if(stack.getItemDamage() == 0){
			if(!outputMarked()) {
				sides.get(side).type = SideType.OUTPUT;
				setOutputAspect();
				return true;
			}
		} else {
			AspectList aspects = new AspectList();
			SideInfo[] inputSides = getInputSides();
			for(SideInfo curSide : inputSides) {
				aspects.add(curSide.aspect, 1);
			}
			if(aspects.visSize() == 0) {
				sides.get(side).type = SideType.INPUT;
				sides.get(side).aspect = getAspectFromStack(stack);
				return true;
			} else if (aspects.visSize() == 1) {
				Aspect newAspect = getAspectFromStack(stack);
				Aspect existAspect = aspects.getAspects()[0];
				if(getAspectCombo(newAspect, existAspect) != null) {
					sides.get(side).type = SideType.INPUT;
					sides.get(side).aspect = getAspectFromStack(stack);
					setOutputAspect();
					return true;
				}
			}
		}
		return false;
	}

	private void setOutputAspect() {
		ForgeDirection outputSide = null;
		AspectList aspects = new AspectList();
		for(ForgeDirection side : sides.keySet()) {
			SideInfo curSide = sides.get(side);
			if(curSide.type == SideType.OUTPUT) {
				outputSide = side;
			} else if(curSide.type == SideType.INPUT) {
				aspects.add(curSide.aspect, 1);
			}
		}
		if(outputSide != null && aspects.visSize() == 2) {
			sides.get(outputSide).aspect = getAspectCombo(aspects.getAspects()[0], aspects.getAspects()[1]);
		}
	}

	private Aspect getAspectCombo(Aspect priAspect, Aspect secAspect) {
		if(priAspect != secAspect) {
			for(Aspect curAspect : Aspect.getCompoundAspects()) {
				Aspect aspect1 = curAspect.getComponents()[0];
				Aspect aspect2 = curAspect.getComponents()[1];
				if((aspect1 == priAspect || aspect2 == priAspect) && (aspect1 == secAspect || aspect2 == secAspect)) {
					return curAspect;
				}
			}
		}
		return null;
	}

	public ItemStack clearSide(ForgeDirection side) {
		ItemStack output = null;
		SideInfo outputSide = getOutputSideInfo();
		if(outputSide != null && outputSide.amount > 0) {
			return null;
		}
		if(sides.get(side).amount == 0) {
			output = getItemForSlot(side);
			sides.get(side).type = SideType.NONE;
			sides.get(side).aspect = null;
			if(outputSide != null) {
				outputSide.aspect = null;
			}
		}
		return output;
	}

	public ItemStack getItemForSlot(ForgeDirection side) {
		ItemStack output = null;
		SideInfo target = sides.get(side);
		if (target.type == SideType.OUTPUT) {
			output = new ItemStack(ConfigItems.itemEssence, 1, 0);
		} else if(target.type == SideType.INPUT){
			output = new ItemStack(ConfigItems.itemEssence, 1, 1);
			((ItemEssence)ConfigItems.itemEssence).setAspects(output, new AspectList().add(target.aspect, 8));
		}
		return output;
	}

	public Aspect getOutputAspect() {
		for(SideInfo side : sides.values()) {
			if(side.type == SideType.OUTPUT) {
				return side.aspect;
			}
		}
		return null;
	}

	private boolean outputMarked() {
		for(SideInfo side : sides.values()) {
			if(side.type == SideType.OUTPUT) {
				return true;
			}
		}
		return false;
	}

	public SideInfo getOutputSideInfo() {
		for(SideInfo side : sides.values()) {
			if(side.type == SideType.OUTPUT) {
				return side;
			}
		}
		return null;
	}

	private SideInfo[] getInputSides() {
		ArrayList<SideInfo> inputSides = new ArrayList<SideInfo>();
		for(SideInfo side : sides.values()) {
			if(side.type == SideType.INPUT) {
				inputSides.add(side);
			}
		}
		return inputSides.toArray(new SideInfo[inputSides.size()]);
	}

	private Aspect getAspectFromStack(ItemStack stack) {
		if(stack.getItem() instanceof ItemEssence) {
			return ((ItemEssence)stack.getItem()).getAspects(stack).getAspects()[0];
		}
		return null;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from != ForgeDirection.UP;
	}

	@Override
	public void readCustomNBT(NBTTagCompound comp) {
		NBTTagList list = comp.getTagList("SideInfo", 10);

		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound sideVal = list.getCompoundTagAt(i);
			sides.get(ForgeDirection.getOrientation((int)sideVal.getByte("side"))).load(sideVal);
			ForgeDirection tubeDir = ForgeDirection.getOrientation((int)sideVal.getByte("side"));
			worldObj.markBlockForUpdate(xCoord + tubeDir.offsetX, yCoord + tubeDir.offsetY, zCoord + tubeDir.offsetZ);
		}

		RedstoneSet.load(comp);
	}

	@Override
	public void writeCustomNBT(NBTTagCompound comp) {
		NBTTagList list = new NBTTagList();

		for(SideInfo side : sides.values()) {
			NBTTagCompound sideVal = side.save();
			list.appendTag(sideVal);
		}
		comp.setTag("SideInfo", list);
		set.save(comp);
	}

	@Override
	public int addToContainer(Aspect aspect, int amount) {
		for(SideInfo side : sides.values()) {
			if(side.type == SideType.INPUT && side.aspect == aspect && side.amount < MAX_AMOUNT) {
				int amountToAdd = Math.min(amount, MAX_AMOUNT - side.amount);
				side.amount += amountToAdd;
				return amount - amountToAdd;
			}
		}
		return 0;
	}

	@Override
	public int containerContains(Aspect aspect) {
		for(SideInfo side : sides.values()) {
			if(side.type != SideType.NONE && side.aspect == aspect) {
				return side.amount;
			}
		}
		return 0;
	}

	@Override
	public boolean doesContainerAccept(Aspect aspect) {
		for(SideInfo side : sides.values()) {
			if(side.type == SideType.INPUT && side.aspect == aspect && side.amount < MAX_AMOUNT) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doesContainerContain(AspectList aspects) {
		for(Aspect aspect : aspects.getAspects()) {
			if(!doesContainerContainAmount(aspect, aspects.getAmount(aspect))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect aspect, int amount) {
		for(SideInfo side : sides.values()) {
			if(side.type == SideType.INPUT && side.aspect == aspect && side.amount >= amount) {
				return true;
			}
		}
		return false;
	}

	@Override
	public AspectList getAspects() {
		AspectList aspects = new AspectList();
		for(SideInfo side : sides.values()) {
			if(side.type != SideType.NONE && side.aspect != null && side.amount > 0) {
				aspects.add(side.aspect, side.amount);
			}
		}
		return aspects.visSize() > 0 ? aspects : null;
	}

	@Override
	public void setAspects(AspectList arg0) {}

	@Override
	public boolean takeFromContainer(AspectList arg0) {
		return false;
	}

	@Override
	public boolean takeFromContainer(Aspect aspect, int amount) {
		for(SideInfo side : sides.values()) {
			if(side.type != SideType.NONE && (aspect == null || side.aspect == aspect) && side.amount >= amount) {
				side.amount -= amount;
				return true;
			}
		}
		return false;
	}

	@Override
	public int addEssentia(Aspect aspect, int amount, ForgeDirection side) {
		if(side != ForgeDirection.UP && side != ForgeDirection.DOWN) {
			SideInfo targetSide = sides.get(side);
			if(targetSide.type == SideType.INPUT && targetSide.aspect == aspect) {
				int amountToAdd = Math.min(amount, MAX_AMOUNT - targetSide.amount);
				targetSide.amount += amountToAdd;
				return amountToAdd;
			}
		}
		return 0;
	}

	@Override
	public boolean canInputFrom(ForgeDirection side) {
		return side != ForgeDirection.UP && side != ForgeDirection.DOWN && sides.get(side).type == SideType.INPUT ? true : false;
	}

	@Override
	public boolean canOutputTo(ForgeDirection side) {
		return side != ForgeDirection.UP && side != ForgeDirection.DOWN && sides.get(side).type == SideType.OUTPUT ? true : false;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection side) {
		return side != ForgeDirection.UP && side != ForgeDirection.DOWN && sides.get(side).type != SideType.NONE ? sides.get(side).amount : 0;
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection side) {
		return side != ForgeDirection.UP && side != ForgeDirection.DOWN && sides.get(side).type != SideType.NONE ? sides.get(side).aspect : null;
	}

	@Override
	public int getMinimumSuction() {
		return 48;
	}

	@Override
	public int getSuctionAmount(ForgeDirection side) {
		return side != ForgeDirection.UP && side != ForgeDirection.DOWN && sides.get(side).type == SideType.INPUT ? 48 : 0;
	}

	@Override
	public Aspect getSuctionType(ForgeDirection side) {
		return side != ForgeDirection.UP && side != ForgeDirection.DOWN && sides.get(side).type != SideType.NONE ? sides.get(side).aspect : null;
	}

	@Override
	public boolean isConnectable(ForgeDirection side) {
		return side != ForgeDirection.UP && side != ForgeDirection.DOWN && sides.get(side).type != SideType.NONE ? true : false;
	}

	@Override
	public boolean renderExtendedTube() {
		return false;
	}

	@Override
	public void setSuction(Aspect aspect, int amount) {}

	@Override
	public int takeEssentia(Aspect aspect, int amount, ForgeDirection side) {
		if(side != ForgeDirection.UP && side != ForgeDirection.DOWN) {
			SideInfo curSide = sides.get(side);
			if(curSide.type != SideType.NONE && curSide.aspect == aspect) {
				int amountToTake = Math.min(amount, curSide.amount);
				curSide.amount -= amountToTake;
				return amountToTake;
			}
		}
		return 0;
	}

	@Override
	public RedstoneSet getCurrentSetting() {
		return set;
	}

	@Override
	public void setNewSetting(RedstoneSet newSet) {
		set = newSet;
	}
}
