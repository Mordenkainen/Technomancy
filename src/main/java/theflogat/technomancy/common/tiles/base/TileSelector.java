package theflogat.technomancy.common.tiles.base;

import net.minecraft.tileentity.TileEntity;
import theflogat.technomancy.common.blocks.base.IMultiTiles;

public class TileSelector extends TileEntity {

    @Override
    public void updateEntity() {
        worldObj.setTileEntity(xCoord, yCoord, zCoord, ((IMultiTiles) worldObj.getBlock(xCoord, yCoord, zCoord)).getTile(worldObj.getBlockMetadata(xCoord, yCoord, zCoord)));
    }
}