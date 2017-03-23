package theflogat.technomancy.common.tiles.thaumcraft.dynamos;

import java.util.Random;

import cpw.mods.fml.common.Optional;
import me.jezza.thaumicpipes.api.interfaces.IThaumicInput;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.lib.compat.Thaumcraft;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;

@Optional.Interface(iface = "me.jezza.thaumicpipes.api.interfaces.IThaumicInput", modid = "ThaumicPipes")
public class TileEssentiaDynamo extends TileDynamoBase implements IAspectContainer, IEssentiaTransport, IThaumicInput {

    private int amount;
    private int maxAmount = 64;
    private Aspect aspect = null;
    public int count;

    @Override
    public int extractFuel(int ener) {
        if (amount <= 0 || aspect == null) {
            return 0;
        }
        float ratio = (ener) / 80F;
        int val = getAspectFuel(aspect);
        if (ratio > amount) {
            return 0;
        }
        amount -= Math.ceil(ratio);
        if (amount <= 0) {
            amount = 0;
            aspect = null;
        }
        return val;
    }

    @Override
    public void writeSyncData(NBTTagCompound comp) {
        super.writeSyncData(comp);
        comp.setShort("Amount", (short) amount);
        if (aspect != null) {
            comp.setString("Aspect", aspect.getTag());
        }
    }

    @Override
    public void readSyncData(NBTTagCompound comp) {
        super.readSyncData(comp);
        amount = comp.getShort("Amount");
        aspect = Aspect.getAspect(comp.getString("Aspect"));
    }

    public int getAspectFuel(Aspect aspect) {
        if (aspect == Aspect.FIRE || aspect == Aspect.ENERGY) {
            return 800;
        }
        if (aspect == Aspect.AIR || aspect == Aspect.ORDER) {
            return 75;
        }
        if (aspect == Aspect.WATER) {
            if (worldObj.getBiomeGenForCoords(xCoord, yCoord).isHighHumidity()) {
                return 200;
            }
            return 50;
        }
        if (aspect == Aspect.MAGIC || aspect == Aspect.ELDRITCH) {
            if (worldObj.getBiomeGenForCoords(xCoord, yCoord) == BiomeGenBase.sky || worldObj.getBiomeGenForCoords(xCoord, yCoord) == ThaumcraftWorldGenerator.biomeMagicalForest) {
                return 300;
            }
            return 75;
        }
        if (aspect == Aspect.MECHANISM || aspect == Aspect.METAL || aspect == Aspect.MOTION || aspect == Aspect.TOOL || aspect == Aspect.TRAP || aspect == Aspect.MINE || aspect == Aspect.CRAFT || aspect == Aspect.TRAVEL) {
            return 75;
        }
        if (aspect == Aspect.CRYSTAL || aspect == Aspect.ENTROPY) {
            return 75;
        }
        if (aspect == Aspect.MAN || aspect == Aspect.SENSES | aspect == Aspect.HEAL || aspect == Aspect.HARVEST || aspect == Aspect.HUNGER || aspect == Aspect.DEATH || aspect == Aspect.BEAST || aspect == Aspect.POISON || aspect == Aspect.MIND || aspect == Aspect.SOUL || aspect == Aspect.WEAPON || aspect == Aspect.WEATHER) {
            return 30;
        }

        if (aspect == Aspect.VOID) {
            if (yCoord < 60) {
                return 200;
            }
            return 50;
        }

        if (aspect == Aspect.TREE || aspect == Aspect.PLANT || aspect == Aspect.CROP || aspect == Aspect.CLOTH || aspect == Aspect.FLESH) {
            return 40;
        }
        if (aspect == Aspect.UNDEAD) {
            if (worldObj.getBiomeGenForCoords(xCoord, yCoord) == BiomeGenBase.sky) {
                return 100;
            }
        }

        if (aspect == Aspect.EXCHANGE) {
            Random rand = new Random();
            return rand.nextInt(1200);
        }
        if (aspect == Aspect.FLIGHT) {
            if (yCoord > 150) {
                return 200;
            }
            return 50;
        }
        if (aspect == Aspect.SLIME) {
            if (worldObj.getChunkFromBlockCoords(xCoord, zCoord).getRandomWithSeed(987234911L).nextInt(10) == 0) {
                return 200;
            }
            return 100;
        }
        if (aspect == Aspect.LIGHT) {
            if (worldObj.isDaytime()) {
                return 300;
            }
            return 50;
        }
        if (aspect == Aspect.DARKNESS) {
            if (!worldObj.isDaytime()) {
                return 300;
            }
            return 50;
        }
        if (aspect == Aspect.AURA) {
            if (worldObj.getBiomeGenForCoords(this.xCoord, this.yCoord) == ThaumcraftWorldGenerator.biomeMagicalForest) {
                return 600;
            }
            return 100;
        }
        if (aspect == Aspect.TAINT) {
            if (worldObj.getBiomeGenForCoords(this.xCoord, this.yCoord) == ThaumcraftWorldGenerator.biomeTaint) {
                return 600;
            }
            return 100;
        }
        return 25;
    }

    @Override
    public AspectList getAspects() {
        AspectList al = new AspectList();
        if ((aspect != null) && (amount > 0)) {
            al.add(this.aspect, this.amount);
        }
        return al;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote && amount < maxAmount) {
            fill();
        }
    }

    public void fill() {
        TileEntity te = null;
        IEssentiaTransport ic = null;
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (dir != ForgeDirection.getOrientation(facing)) {
                te = Thaumcraft.getConnectableTile(worldObj, xCoord, yCoord, zCoord, dir);
                if (te != null) {
                    ic = (IEssentiaTransport) te;
                    Aspect ta = ic.getEssentiaType(dir.getOpposite());
                    if (ic.getEssentiaAmount(dir.getOpposite()) > 0 && ic.getSuctionAmount(dir.getOpposite()) < getSuctionAmount(null) && getSuctionAmount(null) >= ic.getMinimumSuction()) {
                        addToContainer(ta, ic.takeEssentia(ta, 1, dir.getOpposite()));
                        return;
                    }
                }
            }
        }
    }

    @Override
    public int addToContainer(Aspect tag, int am) {
        if (am == 0) {
            return am;
        }
        if ((amount < maxAmount && tag == aspect) || amount == 0) {
            aspect = tag;
            int added = Math.min(am, maxAmount - amount);
            amount += added;
            am -= added;
        }
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        return am;
    }

    @Override
    public boolean takeFromContainer(Aspect tag, int amount) {
        if (this.amount >= amount && tag == aspect) {
            this.amount -= amount;
            if (this.amount <= 0) {
                aspect = null;
                this.amount = 0;
            }
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            return true;
        }
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect tag, int amount) {
        if ((this.amount >= amount) && (tag == aspect)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList ot) {
        if (ot.size() > 1) {
            return false;
        }
        for (Aspect tt : ot.getAspects()) {
            return doesContainerContainAmount(tt, ot.getAmount(tt));
        }
        return false;
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, ForgeDirection dir) {
        return takeFromContainer(aspect, amount) ? amount : 0;
    }

    @Override
    public int getMinimumSuction() {
        return 128;
    }

    @Override
    public boolean renderExtendedTube() {
        return true;
    }

    @Override
    public boolean isConnectable(ForgeDirection face) {
        return face != ForgeDirection.getOrientation(facing);
    }

    @Override
    public boolean canInputFrom(ForgeDirection face) {
        return true;
    }

    @Override
    public boolean doesContainerAccept(Aspect tag) {
        return (aspect == null || tag == aspect) && amount < maxAmount;
    }

    @Override
    public int containerContains(Aspect tag) {
        return (aspect != null && tag == aspect) ? amount : 0;
    }

    @Override
    public boolean takeFromContainer(AspectList ot) {
        return false;
    }

    @Override
    public boolean canOutputTo(ForgeDirection face) {
        return false;
    }

    @Override
    public void setAspects(AspectList aspects) {}

    @Override
    public void setSuction(Aspect aspect, int amount) {}

    @Override
    public Aspect getSuctionType(ForgeDirection face) {
        if (amount > 0) {
            return aspect;
        }
        return null;
    }

    @Override
    public int getSuctionAmount(ForgeDirection face) {
        if (amount < maxAmount) {
            return 128;
        }
        return 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, ForgeDirection dir) {
        return amount - addToContainer(aspect, amount);
    }

    @Override
    public Aspect getEssentiaType(ForgeDirection face) {
        return this.aspect;
    }

    @Override
    public int getEssentiaAmount(ForgeDirection face) {
        return this.amount;
    }
}
