/**
 * 
 * This class was created by <Azanor>. It's distributed as
 * part of the Thaumcraft Mod.
 * 
 **/


package democretes.compat.thaumcraft;

import java.util.Random;
import net.minecraft.world.World;

public class TileJar extends TileThaumcraft{
	public float wobblex = 0.0F;
	public float wobblez = 0.0F;
	protected static Random rand = new Random();
	protected int spazattack = 0;
	protected boolean canSpaz = false;

	public boolean canUpdate(){
		return true;
	}

	public void updateEntity(){
		super.updateEntity();

		if (worldObj.isRemote) {
			if ((canSpaz) && (spazattack == 0) && (rand.nextInt(2000) == 0)) {
				spazattack = (5 + rand.nextInt(50));
			}

			if (spazattack > 0) {
				spazattack -= 1;
				if ((wobblez == 0.0F) && (wobblex == 0.0F)) {
					worldObj.playSound(xCoord + 0.5F, yCoord + 0.5F, zCoord + 0.5F, "thaumcraft:jar", 0.1F, 1.0F, false);
					switch (rand.nextInt(4)) {
					case 0:  wobblez = (2 + rand.nextInt(5)); break;
					case 1:  wobblez = (-2 - rand.nextInt(5)); break;
					case 2:  wobblex = (2 + rand.nextInt(5)); break;
					case 3:  wobblex = (-2 - rand.nextInt(5));
					}

				}
			}
			if (wobblex > 0.0F) wobblex -= 1.0F;
			if (wobblez > 0.0F) wobblez -= 1.0F;
			if (wobblex < 0.0F) wobblex += 1.0F;
			if (wobblez < 0.0F) wobblez += 1.0F;
		}
	}
}