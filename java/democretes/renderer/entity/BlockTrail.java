package democretes.renderer.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockTrail extends EntityItem{

	public BlockTrail(World w, double x, double y, double z, double xx, double yy, double zz, ItemStack items) {
		super(w, x, y, z, items);
        motionX = xx + (double)((float)(Math.random() * 2.0D - 1.0D) * 0.4F);
        motionY = yy + (double)((float)(Math.random() * 2.0D - 1.0D) * 0.4F);
        motionZ = zz + (double)((float)(Math.random() * 2.0D - 1.0D) * 0.4F);
        float f = (float)(Math.random() + Math.random() + 1.0D) * 0.15F;
        float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX = motionX / (double)f1 * (double)f * 0.4000000059604645D;
        motionY = motionY / (double)f1 * (double)f * 0.4000000059604645D + 0.10000000149011612D;
        motionZ = motionZ / (double)f1 * (double)f * 0.4000000059604645D;
        yOffset = height / 2.0F;
	}
	
	@Override
	public void onUpdate() {
		lifespan += 1;
		motionY += 0.03999999910593033D;
		super.onUpdate();
	}
	
	@Override
	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {}
	
	@Override
	public boolean combineItems(EntityItem par1EntityItem) {
		return false;
	}
	
	

}
