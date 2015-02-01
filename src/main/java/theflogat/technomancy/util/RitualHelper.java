package theflogat.technomancy.util;

import theflogat.technomancy.common.blocks.technom.BlockCrystal;
import net.minecraft.world.World;

public class RitualHelper {
	
	public static void removeT1(World w, int x, int y, int z) {
		for(int yy=0; yy<3; yy++){
			for(int xx=-1; xx<=1; xx+=2){
				for(int zz=-1; zz<=1; zz+=2){
					w.setBlockToAir(x + xx, y + yy, z + zz);
				}
			}
		}
	}
	
	public static void removeT2(World w, int x, int y, int z) {
		for(int yy=0; yy<3; yy++){
			for(int xx=-3; xx<=3; xx+=6){
				for(int zz=-3; zz<=3; zz+=6){
					w.setBlockToAir(x + xx, y + yy, z + zz);
				}
			}
		}
	}
	
	public static void removeT3(World w, int x, int y, int z) {
		for(int yy=0; yy<3; yy++){
			for(int xx=-5; xx<=5; xx+=10){
				for(int zz=-5; zz<=5; zz+=10){
					w.setBlockToAir(x + xx, y + yy, z + zz);
				}
			}
		}
	}
	
	public static boolean checkForT1(World w, int x, int y, int z, int meta) {
		for(int yy=0; yy<3; yy++){
			for(int xx=-1; xx<=1; xx+=2){
				for(int zz=-1; zz<=1; zz+=2){
					if(!(w.getBlock(x + xx, y + yy, z + zz)instanceof BlockCrystal && w.getBlockMetadata(x + xx, y + yy, z + zz)==meta)){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public static boolean checkForT2(World w, int x, int y, int z, int meta) {
		for(int yy=0; yy<3; yy++){
			for(int xx=-3; xx<=3; xx+=6){
				for(int zz=-3; zz<=3; zz+=6){
					if(!(w.getBlock(x + xx, y + yy, z + zz)instanceof BlockCrystal && w.getBlockMetadata(x + xx, y + yy, z + zz)==meta)){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public static boolean checkForT3(World w, int x, int y, int z, int meta) {
		for(int yy=0; yy<3; yy++){
			for(int xx=-5; xx<=5; xx+=10){
				for(int zz=-5; zz<=5; zz+=10){
					if(!(w.getBlock(x + xx, y + yy, z + zz)instanceof BlockCrystal && w.getBlockMetadata(x + xx, y + yy, z + zz)==3)){
						return false;
					}
				}
			}
		}
		return true;
	}
	
}
