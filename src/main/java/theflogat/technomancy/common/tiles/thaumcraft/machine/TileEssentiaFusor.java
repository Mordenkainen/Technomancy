package theflogat.technomancy.common.tiles.thaumcraft.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemEssence;
import theflogat.technomancy.common.tiles.base.TileMachineRedstone;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.lib.handlers.Rate;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEssentiaFusor extends TileMachineRedstone implements IAspectContainer, IEssentiaTransport {

    private final Map<ForgeDirection, SideInfo> sides = new HashMap<ForgeDirection, SideInfo>();
    private static final int MAX_AMOUNT = 64;
    
    private enum SideType {
        INPUT,
        OUTPUT,
        NONE;
    }

    private class SideInfo {

        public Aspect aspect;
        public int amount;
        public SideType type = SideType.NONE;
        public ForgeDirection side = ForgeDirection.UNKNOWN;

        public SideInfo(final ForgeDirection side) {
            this.side = side;
        }

        public NBTTagCompound save() {
            final NBTTagCompound tags = new NBTTagCompound();
            tags.setInteger("amount", amount);
            tags.setByte("type", (byte) type.ordinal());
            tags.setByte("side", (byte) side.ordinal());
            if (aspect != null) {
                tags.setString("aspect", aspect.getTag());
            }
            return tags;
        }

        public void load(final NBTTagCompound tags) {
            aspect = tags.hasKey("aspect") ? Aspect.getAspect(tags.getString("aspect")) : null;
            type = SideType.values()[tags.getByte("type")];
            amount = tags.getInteger("amount");
        }
    }

    public TileEssentiaFusor() {
        super(Rate.fusorCost * 10, RedstoneSet.HIGH);
        sides.put(ForgeDirection.EAST, new SideInfo(ForgeDirection.EAST));
        sides.put(ForgeDirection.WEST, new SideInfo(ForgeDirection.WEST));
        sides.put(ForgeDirection.NORTH, new SideInfo(ForgeDirection.NORTH));
        sides.put(ForgeDirection.SOUTH, new SideInfo(ForgeDirection.SOUTH));
    }

    @Override
    public void updateEntity() {
        if (fullyMarked()) {
            boolean flag = false;
            flag |= fill();
            flag |= fuse();
            if (flag) {
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                markDirty();
            }
        }
    }

    private boolean fill() {
        boolean ret = false;
        for (final SideInfo side : getInputSides()) {
            if (side.amount < MAX_AMOUNT) {
                final TileEntity te = Thaumcraft.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, side.side);
                if (te != null) {
                    final IEssentiaTransport ic = (IEssentiaTransport) te;
                    final Aspect ta = ic.getEssentiaType(side.side.getOpposite());
                    if (ta == side.aspect && ic.getEssentiaAmount(side.side.getOpposite()) > 0 && ic.getSuctionAmount(side.side.getOpposite()) < getSuctionAmount(side.side) && getSuctionAmount(side.side) >= ic.getMinimumSuction()) {
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
        if (set.canRun(this) && outputMarked() && getOutputSideInfo().amount < MAX_AMOUNT && getEnergyStored(ForgeDirection.UNKNOWN) >= Rate.fusorCost) {
            SideInfo[] inputSides = getInputSides();
            if (inputSides.length == 2 && inputSides[0].amount > 0 && inputSides[1].amount > 0) {
                extractEnergy(ForgeDirection.UNKNOWN, Rate.fusorCost, false);
                inputSides[0].amount--;
                inputSides[1].amount--;
                getOutputSideInfo().amount++;
                ret = true;
            }
        }
        return ret;
    }

    public boolean isSideOccupied(final ForgeDirection side) {
        return sides.get(side).type != SideType.NONE;
    }

    public boolean markSide(final ForgeDirection side, final ItemStack stack) {
        if (stack.getItemDamage() == 0) {
            if (!outputMarked()) {
                sides.get(side).type = SideType.OUTPUT;
                setOutputAspect();
                return true;
            }
        } else {
            final SideInfo[] inputSides = getInputSides();
            if (inputSides.length == 0) {
                sides.get(side).type = SideType.INPUT;
                sides.get(side).aspect = getAspectFromStack(stack);
                return true;
            } else if (inputSides.length == 1) {
                final Aspect newAspect = getAspectFromStack(stack);
                final Aspect comboAspect = getAspectCombo(newAspect, inputSides[0].aspect);
                if (comboAspect != null) {
                    sides.get(side).type = SideType.INPUT;
                    sides.get(side).aspect = newAspect;
                    setOutputAspect(comboAspect);
                    return true;
                }
            }
        }
        return false;
    }

    private void setOutputAspect() {
        final SideInfo outputSide = getOutputSideInfo();
        final SideInfo[] inputSides = getInputSides();
        if (outputSide != null && inputSides.length == 2) {
            outputSide.aspect = getAspectCombo(inputSides[0].aspect, inputSides[1].aspect);
        }
    }

    private void setOutputAspect(final Aspect comboAspect) {
        final SideInfo outputSide = getOutputSideInfo();
        if (outputSide != null) {
            outputSide.aspect = comboAspect;
        }
    }

    private static Aspect getAspectCombo(final Aspect priAspect, final Aspect secAspect) {
        if (priAspect != secAspect) {
            for (final Aspect curAspect : Aspect.getCompoundAspects()) {
                final Aspect aspect1 = curAspect.getComponents()[0];
                final Aspect aspect2 = curAspect.getComponents()[1];
                if ((aspect1 == priAspect || aspect2 == priAspect) && (aspect1 == secAspect || aspect2 == secAspect)) {
                    return curAspect;
                }
            }
        }
        return null;
    }

    public ItemStack clearSide(final ForgeDirection side) {
        ItemStack output = null;
        final SideInfo outputSide = getOutputSideInfo();
        if (outputSide == null || outputSide.amount == 0) {
            final SideInfo targetSide = sides.get(side);
            if (targetSide.amount == 0) {
                output = getItemForSlot(side);
                targetSide.type = SideType.NONE;
                targetSide.aspect = null;
                if (outputSide != null) {
                    outputSide.aspect = null;
                }
            }
        }
        return output;
    }

    public ItemStack getItemForSlot(final ForgeDirection side) {
        ItemStack output = null;
        final SideInfo target = sides.get(side);
        if (target.type == SideType.OUTPUT) {
            output = new ItemStack(ConfigItems.itemEssence, 1, 0);
        } else if (target.type == SideType.INPUT) {
            output = new ItemStack(ConfigItems.itemEssence, 1, 1);
            ((ItemEssence) ConfigItems.itemEssence).setAspects(output, new AspectList().add(target.aspect, 8));
        }
        return output;
    }

    public Aspect getOutputAspect() {
        final SideInfo output = getOutputSideInfo();
        return output == null ? null : output.aspect;
    }

    private boolean outputMarked() {
        return getOutputSideInfo() != null;
    }

    public ForgeDirection getOutputSide() {
        final SideInfo output = getOutputSideInfo();
        return output == null ? ForgeDirection.UNKNOWN : output.side;
    }

    public SideInfo getOutputSideInfo() {
        for (final SideInfo side : sides.values()) {
            if (side.type == SideType.OUTPUT) {
                return side;
            }
        }
        return null;
    }

    private SideInfo[] getInputSides() {
        final ArrayList<SideInfo> inputSides = new ArrayList<SideInfo>();
        for (final SideInfo side : sides.values()) {
            if (side.type == SideType.INPUT) {
                inputSides.add(side);
            }
        }
        return inputSides.toArray(new SideInfo[inputSides.size()]);
    }

    private static Aspect getAspectFromStack(final ItemStack stack) {
        if (stack.getItem() instanceof ItemEssence) {
            return ((ItemEssence) stack.getItem()).getAspects(stack).getAspects()[0];
        }
        return null;
    }

    private boolean fullyMarked() {
        return getInputSides().length == 2 && outputMarked();
    }

    private static boolean isValidSide(final ForgeDirection side) {
        return side != ForgeDirection.UP && side != ForgeDirection.DOWN;
    }

    @Override
    public boolean canConnectEnergy(final ForgeDirection from) {
        return from != ForgeDirection.UP;
    }

    @Override
    public void writeSyncData(final NBTTagCompound comp) {
        super.writeSyncData(comp);
        final NBTTagList list = new NBTTagList();

        for (final SideInfo side : sides.values()) {
            final NBTTagCompound sideVal = side.save();
            list.appendTag(sideVal);
        }
        comp.setTag("SideInfo", list);
    }

    @Override
    public void readSyncData(final NBTTagCompound comp) {
        super.readSyncData(comp);
        final NBTTagList list = comp.getTagList("SideInfo", 10);

        for (int i = 0; i < list.tagCount(); i++) {
            final NBTTagCompound sideVal = list.getCompoundTagAt(i);
            sides.get(ForgeDirection.getOrientation(sideVal.getByte("side"))).load(sideVal);
        }
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public int addToContainer(final Aspect aspect, final int amount) {
        for (SideInfo side : sides.values()) {
            if (side.type == SideType.INPUT && side.aspect == aspect && side.amount < MAX_AMOUNT) {
                final int amountToAdd = Math.min(amount, MAX_AMOUNT - side.amount);
                side.amount += amountToAdd;
                return amount - amountToAdd;
            }
        }
        return 0;
    }

    @Override
    public int containerContains(final Aspect aspect) {
        for (final SideInfo side : sides.values()) {
            if (side.type != SideType.NONE && side.aspect == aspect) {
                return side.amount;
            }
        }
        return 0;
    }

    @Override
    public boolean doesContainerAccept(final Aspect aspect) {
        for (final SideInfo side : sides.values()) {
            if (side.type == SideType.INPUT && side.aspect == aspect && side.amount < MAX_AMOUNT) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean doesContainerContain(final AspectList aspects) {
        for (final Aspect aspect : aspects.getAspects()) {
            if (!doesContainerContainAmount(aspect, aspects.getAmount(aspect))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean doesContainerContainAmount(final Aspect aspect, final int amount) {
        for (final SideInfo side : sides.values()) {
            if (side.type == SideType.INPUT && side.aspect == aspect && side.amount >= amount) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AspectList getAspects() {
        final AspectList aspects = new AspectList();
        for (final SideInfo side : sides.values()) {
            if (side.type != SideType.NONE && side.aspect != null && side.amount > 0) {
                aspects.add(side.aspect, side.amount);
            }
        }
        return aspects;
    }

    @Override
    public void setAspects(final AspectList arg0) {}

    @Override
    public boolean takeFromContainer(final AspectList al) {
        return false;
    }

    @Override
    public boolean takeFromContainer(final Aspect aspect, final int amount) {
        for (SideInfo side : sides.values()) {
            if (side.type != SideType.NONE && (aspect == null || side.aspect == aspect) && side.amount >= amount) {
                side.amount -= amount;
                return true;
            }
        }
        return false;
    }

    @Override
    public int addEssentia(final Aspect aspect, final int amount, final ForgeDirection side) {
        if (isValidSide(side)) {
            SideInfo targetSide = sides.get(side);
            if (targetSide.type == SideType.INPUT && targetSide.aspect == aspect) {
                final int amountToAdd = Math.min(amount, MAX_AMOUNT - targetSide.amount);
                targetSide.amount += amountToAdd;
                return amountToAdd;
            }
        }
        return 0;
    }

    @Override
    public boolean canInputFrom(final ForgeDirection side) {
        return isValidSide(side) && sides.get(side).type == SideType.INPUT && fullyMarked() ? true : false;
    }

    @Override
    public boolean canOutputTo(final ForgeDirection side) {
        return isValidSide(side) && (sides.get(side).type == SideType.OUTPUT || sides.get(side).type == SideType.INPUT && !fullyMarked()) ? true : false;
    }

    @Override
    public int getEssentiaAmount(final ForgeDirection side) {
        return isValidSide(side) && sides.get(side).type != SideType.NONE ? sides.get(side).amount : 0;
    }

    @Override
    public Aspect getEssentiaType(final ForgeDirection side) {
        return isValidSide(side) && sides.get(side).type != SideType.NONE ? sides.get(side).aspect : null;
    }

    @Override
    public int getMinimumSuction() {
        return -48;
    }

    @Override
    public int getSuctionAmount(final ForgeDirection side) {
        return isValidSide(side) && fullyMarked() ? (sides.get(side).type == SideType.INPUT ? 48 : sides.get(side).type == SideType.OUTPUT ? -48 : 0) : 0;
    }

    @Override
    public Aspect getSuctionType(final ForgeDirection side) {
        return isValidSide(side) && sides.get(side).type != SideType.NONE ? sides.get(side).aspect : null;
    }

    @Override
    public boolean isConnectable(final ForgeDirection side) {
        return isValidSide(side) && sides.get(side).type != SideType.NONE;
    }

    @Override
    public boolean renderExtendedTube() {
        return false;
    }

    @Override
    public void setSuction(final Aspect aspect, final int amount) {}

    @Override
    public int takeEssentia(final Aspect aspect, final int amount, final ForgeDirection side) {
        if (side != ForgeDirection.UP && side != ForgeDirection.DOWN) {
            SideInfo curSide = sides.get(side);
            if (curSide.type != SideType.NONE && curSide.aspect == aspect) {
                final int amountToTake = Math.min(amount, curSide.amount);
                curSide.amount -= amountToTake;
                return amountToTake;
            }
        }
        return 0;
    }
}
