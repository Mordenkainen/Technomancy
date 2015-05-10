package theflogat.technomancy.util.helpers;

import theflogat.technomancy.common.blocks.technom.BlockCrystal;
import net.minecraft.world.World;

public class RitualHelper {
	
	public static void removeT(World w, int x, int y, int z, int tier) {
		for(int yy=0; yy<3; yy++){
			for(int xx=-(1+2*tier); xx<=(1+2*tier); xx+=(2+4*tier)){
				for(int zz=-(1+2*tier); zz<=(1+2*tier); zz+=(2+4*tier)){
					w.setBlockToAir(x + xx, y + yy, z + zz);
				}
			}
		}
	}
	
	public static boolean checkForT(World w, int x, int y, int z, int meta, int tier) {
		for(int yy=0; yy<3; yy++){
			for(int xx=-(1+(2*tier)); xx<=(1+(2*tier)); xx+=(2+4*tier)){
				for(int zz=-(1+(2*tier)); zz<=(1+(2*tier)); zz+=(2+(4*tier))){
					if(!(w.getBlock(x + xx, y + yy, z + zz)instanceof BlockCrystal && w.getBlockMetadata(x + xx, y + yy, z + zz)==meta)){
						return false;
					}
				}
			}
		}
		return true;
	}
	
}
