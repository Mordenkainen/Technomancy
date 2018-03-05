package theflogat.technomancy.common.tiles.technom.existence;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;
import theflogat.technomancy.common.tiles.base.TileExistenceRedstoneBase;

public class TileExistenceCropAccelerator extends TileExistenceRedstoneBase{

	public TileExistenceCropAccelerator() {
		super(RedstoneSet.LOW, 10000);
	}

	@Override
	public void update() {
		if(!world.isRemote && set.canRun(this) && power>605){
			for(int xx=-5;xx<=5; xx++){
				for(int zz=-5;zz<=5; zz++){
					Block b = world.getBlockState(new BlockPos(pos.getX() + xx, pos.getY() + 2, pos.getZ() + zz)).getBlock();
					if(b instanceof IPlantable){
						if(b instanceof IGrowable){
							if(((IGrowable)b).canGrow(world, new BlockPos(pos.getX() + xx, pos.getY() + 2, pos.getZ() + zz), world.getBlockState(new BlockPos(pos.getX() + xx, pos.getY() + 2, pos.getZ() + zz)), world.isRemote)){
								((IGrowable)b).canUseBonemeal(world, world.rand, new BlockPos(pos.getX() + xx, pos.getY() + 2, pos.getZ() + zz), world.getBlockState(new BlockPos(pos.getX() + xx, pos.getY() + 2, pos.getZ() + zz)));
								power -= 30;
							}
						}else{
							b.updateTick(world, new BlockPos(pos.getX() + xx, pos.getY() + 2, pos.getZ() + zz), world.getBlockState(new BlockPos(pos.getX() + xx, pos.getY() + 2, pos.getZ() + zz)), world.rand);
							power -= 5;
						}
					}
				}
			}
		}
	}
}