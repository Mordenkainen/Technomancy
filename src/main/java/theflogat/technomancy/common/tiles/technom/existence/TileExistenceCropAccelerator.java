package theflogat.technomancy.common.tiles.technom.existence;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraftforge.common.IPlantable;
import theflogat.technomancy.common.tiles.base.TileExistenceRedstoneBase;

public class TileExistenceCropAccelerator extends TileExistenceRedstoneBase {

    public TileExistenceCropAccelerator() {
        super(RedstoneSet.LOW, 10000);
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote && set.canRun(this) && power > 605) {
            for (int xx = -5; xx <= 5; xx++) {
                for (int zz = -5; zz <= 5; zz++) {
                    final Block b = worldObj.getBlock(xCoord + xx, yCoord + 2, zCoord + zz);
                    if (b instanceof IPlantable) {
                        if (b instanceof IGrowable) {
                            if (((IGrowable) b).func_149851_a(worldObj, xCoord + xx, yCoord + 2, zCoord + zz, worldObj.isRemote)) {
                                ((IGrowable) b).func_149853_b(worldObj, worldObj.rand, xCoord + xx, yCoord + 2, zCoord + zz);
                                power -= 30;
                            }
                        } else {
                            b.updateTick(worldObj, xCoord + xx, yCoord + 2, zCoord + zz, worldObj.rand);
                            power -= 5;
                        }
                    }
                }
            }
        }
    }
}