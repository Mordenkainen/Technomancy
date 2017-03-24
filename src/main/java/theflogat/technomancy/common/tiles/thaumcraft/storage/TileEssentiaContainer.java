package theflogat.technomancy.common.tiles.thaumcraft.storage;

import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.tiles.TileJarFillable;

public class TileEssentiaContainer extends TileJarFillable implements IAspectSource, IEssentiaTransport {

    public int max = 640;

    public TileEssentiaContainer() {
        super();
        this.maxAmount = this.max;
    }

    @Override
    public int getMinimumSuction() {
        return this.aspectFilter == null ? 48 + (amount / 50) : 56 + (amount / 50);
    }

    @Override
    public int getSuctionAmount(final ForgeDirection loc) {
        if (this.amount < this.maxAmount) {
            if (this.aspectFilter != null) {
                return 56 + (amount / 50);
            }
            return 48 + (amount / 50);
        }
        return 0;
    }
}