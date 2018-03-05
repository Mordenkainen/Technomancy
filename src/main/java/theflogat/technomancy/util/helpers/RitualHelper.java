package theflogat.technomancy.util.helpers;

import net.minecraft.util.math.BlockPos;
import theflogat.technomancy.common.blocks.technom.BlockCrystal;
import net.minecraft.world.World;

public class RitualHelper {
	
	public static void removeT(World w, int x, int y, int z, int tier) {
		for(int yy=0; yy<3; yy++){
			for(int xx=-(1+2*tier); xx<=(1+2*tier); xx+=(2+4*tier)){
				for(int zz=-(1+2*tier); zz<=(1+2*tier); zz+=(2+4*tier)){
					w.setBlockToAir(new BlockPos(x + xx, y + yy, z + zz));
				}
			}
		}
	}
	
	public static boolean checkForT(World w, int x, int y, int z, int meta, int tier) {
		for(int yy=0; yy<3; yy++){
			for(int xx=-(1+(2*tier)); xx<=(1+(2*tier)); xx+=(2+4*tier)){
				for(int zz=-(1+(2*tier)); zz<=(1+(2*tier)); zz+=(2+(4*tier))){
					if(!(w.getBlockState(new BlockPos(x + xx, y + yy, z + zz)).getBlock() instanceof BlockCrystal && w.getBlockState(new BlockPos(x + xx, y + yy, z + zz)).getBlock().getMetaFromState(w.getBlockState(new BlockPos(x + xx, y + yy, z + zz)))== meta)){
						return false;
					}
				}
			}
		}
		return true;
	}
	
}
