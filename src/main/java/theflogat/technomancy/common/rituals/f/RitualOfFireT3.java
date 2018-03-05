package theflogat.technomancy.common.rituals.f;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import theflogat.technomancy.api.rituals.IRitualEffectHandler;
import theflogat.technomancy.api.rituals.Ritual;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.util.Area;
import theflogat.technomancy.util.AreaProtocolBuilder;
import theflogat.technomancy.util.Coords;
import theflogat.technomancy.util.helpers.MathHelper;

public class RitualOfFireT3 extends Ritual implements IRitualEffectHandler{

	static final int top = 90;
	static final Area area = new Area(50, top, 50);

	public RitualOfFireT3() {
		super(new Type[]{Type.FIRE,Type.FIRE,Type.FIRE},Type.FIRE);
	}

	@Override
	public boolean canApplyEffect(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {
		TileCatalyst te = ((TileCatalyst)w.getTileEntity(new BlockPos(x, y, z)));
		te.data = new Object[1];
		te.data[0] = new AreaProtocolBuilder(new Coords(x-area.lengthX/2, 0, z-area.lengthZ/2, w), area){
			int[] tempC = {0,-1,-1,-1,-1};
			
			@Override
			public boolean isPosValid(Coords c) {
				if(tempC[0]!=c.y){
					tempC[0] = c.y;
					tempC[1] = rand.nextInt(getScale(c.y));
					tempC[2] = rand.nextInt(getScale(c.y));
					tempC[3] = rand.nextInt(getScale(c.y));
					tempC[4] = rand.nextInt(getScale(c.y));
				}
				return MathHelper.within(area.lengthX-tempC[1], area.lengthX+tempC[2], c.x) && MathHelper.within(area.lengthZ-tempC[3], area.lengthZ+tempC[4], c.z);
			}
			
			private int getScale(int y) {
				if(y==area.lengthY){
					return 1;
				}
				if(y==area.lengthY-1){
					return 3;
				}
				if(MathHelper.withinStrict(area.lengthY-1, area.lengthY-5, y)){
					return 5;
				}
				if(MathHelper.withinStrict(area.lengthY-5, area.lengthY-15, y)){
					return 9;
				}
				if(MathHelper.withinStrict(area.lengthY-15, area.lengthY-35, y)){
					return 15;
				}
				if(MathHelper.withinStrict(area.lengthY-35, area.lengthY-65, y)){
					return 21;
				}
				return 25;
			}
		};
		te.remCount = 20;
		te.handler = this;
		removeFrame(w, x, y, z);
	}

	@Override
	public void applyEffect(TileCatalyst te) {
		if(te.data!=null && ((AreaProtocolBuilder)te.data[0]).buildNext(te.getWorld(), TMBlocks.basalt.getDefaultState(), new Coords(te))){
			te.remCount = 20;
			return;
		}
		te.remCount = 0;
	}

}