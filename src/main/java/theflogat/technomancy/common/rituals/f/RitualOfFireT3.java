package theflogat.technomancy.common.rituals.f;

import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import theflogat.technomancy.api.rituals.IRitualEffectHandler;
import theflogat.technomancy.api.rituals.Ritual;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.util.MathHelper;

public class RitualOfFireT3 extends Ritual implements IRitualEffectHandler{

	static final int top = 90;

	public RitualOfFireT3() {
		super(new Type[]{Type.FIRE,Type.FIRE,Type.FIRE},Type.FIRE);
	}

	@Override
	public boolean canApplyEffect(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {
		((TileCatalyst)w.getTileEntity(x, y, z)).remCount = Math.max(top, y);
		((TileCatalyst)w.getTileEntity(x, y, z)).handler = this;
	}

	public static void appe(World w, int x, int y, int z, int count){
		double coeff = Math.pow(Math.max(top, y)/(Math.max(top, y)-count+1),6);
		coeff = MathHelper.round(coeff);
		if(coeff==1){
			coeff = 0;
		}
		for(int xx=(int) -coeff;xx<=coeff;xx++){
			for(int zz=(int) -coeff;zz<=coeff;zz++){
				if(!(xx==0 && count==y && zz==0)){
					if(((TileCatalyst)w.getTileEntity(x, y, z)).user!=null){
						if(MinecraftForge.EVENT_BUS.post(new BreakEvent(x+xx, count, z+zz, w, w.getBlock(x+xx, count, z+zz),
								w.getBlockMetadata(x+xx, count, z+zz), ((TileCatalyst)w.getTileEntity(x, y, z)).user)))
							w.setBlock(x+xx, count, z+zz, TMBlocks.basalt);
					}else{
						w.setBlock(x+xx, count, z+zz, TMBlocks.basalt);
					}
				}
			}
		}
	}

	@Override
	public void applyEffect(TileCatalyst te) {
		appe(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, te.remCount);
	}

}