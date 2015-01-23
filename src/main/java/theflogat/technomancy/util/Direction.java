package theflogat.technomancy.util;

import net.minecraftforge.common.util.ForgeDirection;

public class Direction {
	public static int getSideFromDir(ForgeDirection dir) {
		for(int i=0; i<ForgeDirection.VALID_DIRECTIONS.length; i++){
			if(ForgeDirection.VALID_DIRECTIONS[i]==dir)
				return i;
		}
		return 0;
	}
}
