package theflogat.technomancy.common.tiles.technom;

import net.minecraft.tileentity.TileEntity;
import theflogat.technomancy.common.blocks.technom.BlockCrystal;

public class TileCrystal extends TileEntity {

    public int getStage() {
        int i = 0;
        if (worldObj != null && worldObj.getBlock(xCoord, yCoord - 1, zCoord) instanceof BlockCrystal) {
            i++;
            if (worldObj.getBlock(xCoord, yCoord - 2, zCoord) instanceof BlockCrystal) {
                i++;
            }
        }
        return i;
    }
}
