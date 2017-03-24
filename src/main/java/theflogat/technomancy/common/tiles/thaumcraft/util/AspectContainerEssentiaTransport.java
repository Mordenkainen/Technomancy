package theflogat.technomancy.common.tiles.thaumcraft.util;

import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;

public class AspectContainerEssentiaTransport implements IAspectContainer {

    private final IEssentiaTransport trans;

    public AspectContainerEssentiaTransport(final IEssentiaTransport trans) {
        this.trans = trans;
    }

    @Override
    public AspectList getAspects() {
        final AspectList as = new AspectList();
        for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (trans.getEssentiaType(dir) != null) {
                as.add(trans.getEssentiaType(dir), trans.getEssentiaAmount(dir));
            }
        }
        return as;
    }

    @Override
    public void setAspects(final AspectList aspects) {}

    @Override
    public boolean doesContainerAccept(final Aspect tag) {
        final AspectList as = new AspectList();
        for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (trans.getEssentiaType(dir) != null) {
                as.add(trans.getEssentiaType(dir), 1);
            }
        }
        return as.getAmount(tag) == 1;
    }

    @Override
    public int addToContainer(final Aspect tag, final int amount) {
        int amountSent = 0;
        int amountToSend = amount;
        for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            final int i = trans.addEssentia(tag, amountToSend, dir);
            amountToSend -= i;
            amountSent += i;
            if (amountToSend <= 0) {
                break;
            }
        }
        return amount - amountSent;
    }

    @Override
    public boolean takeFromContainer(final Aspect tag, final int amount) {
        for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (trans.getEssentiaType(dir) == tag && trans.getEssentiaAmount(dir) >= amount) {
                trans.takeEssentia(tag, amount, dir);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean takeFromContainer(final AspectList ot) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(final Aspect tag, final int amount) {
        for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (trans.getEssentiaType(dir) == tag && trans.getEssentiaAmount(dir) >= amount) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean doesContainerContain(final AspectList ot) {
        return false;
    }

    @Override
    public int containerContains(final Aspect tag) {
        for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (trans.getEssentiaType(dir) == tag) {
                return trans.getEssentiaAmount(dir);
            }
        }
        return 0;
    }
}
