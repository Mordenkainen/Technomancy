package theflogat.technomancy.common.tiles.bloodmagic.dynamos;

import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.lib.compat.BloodMagic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileBloodDynamo extends TileDynamoBase implements IFluidHandler {

    public int liquid;
    public static final int CAPACITY = 8000;

    @Override
    public int extractFuel(final int ener) {
        final float ratio = (ener) / 80F;
        final int val = (int) Math.ceil(100 * ratio);
        if (val > liquid) {
            return 0;
        }
        liquid -= val;
        return 400;
    }

    public boolean emptyBucket() {
        if (liquid + 1000 <= CAPACITY) {
            liquid += 1000;
            return true;
        }
        return false;
    }

    @Override
    public int fill(final ForgeDirection from, final FluidStack resource, final boolean doFill) {
        if (resource == null || from == ForgeDirection.UNKNOWN || from.ordinal() == facing) {
            return 0;
        }
        if (resource.getFluid() == BloodMagic.lifeEssenceFluid) {
            final int liquid = this.liquid;
            this.liquid += doFill ? Math.min(resource.amount, CAPACITY - liquid) : 0;
            return Math.min(resource.amount, CAPACITY - liquid);
        }
        return 0;
    }

    @Override
    public FluidStack drain(final ForgeDirection from, final FluidStack resource, final boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(final ForgeDirection from, final int maxDrain, final boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(final ForgeDirection from, final Fluid fluid) {
        return from.ordinal() != facing;
    }

    @Override
    public boolean canDrain(final ForgeDirection from, final Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(final ForgeDirection from) {
        return new FluidTankInfo[] { new FluidTankInfo(new FluidStack(BloodMagic.lifeEssenceFluid, liquid), CAPACITY) };
    }

    @Override
    public void writeSyncData(final NBTTagCompound comp) {
        super.writeSyncData(comp);
        comp.setInteger("liquid", liquid);
    }

    @Override
    public void readSyncData(final NBTTagCompound comp) {
        super.readSyncData(comp);
        liquid = comp.getInteger("liquid");
    }
}
