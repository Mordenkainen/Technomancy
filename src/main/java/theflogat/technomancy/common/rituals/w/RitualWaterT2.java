package theflogat.technomancy.common.rituals.w;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theflogat.technomancy.api.rituals.IRitualEffectHandler;
import theflogat.technomancy.api.rituals.Ritual;
import theflogat.technomancy.api.rituals.Ritual.Type;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.util.RitualHelper;

public class RitualWaterT2 extends Ritual implements IRitualEffectHandler{

	public RitualWaterT2() {
		super(new Type[]{Type.WATER,Type.WATER},Type.WATER);
	}

	@Override
	public boolean canApplyEffect(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void applyEffect(World w, int x, int y, int z) {
		((TileCatalyst)w.getTileEntity(x, y, z)).handler = this;
	}

	@Override
	public void applyEffect(TileCatalyst te) {
		for(int i=-3; i<4; i++){
			for(int j=-3; j<4; i++){
				if(te.getWorldObj().getBlock(te.xCoord+i, te.yCoord-1, te.zCoord+j).isAir(te.getWorldObj(), te.xCoord+i, te.yCoord-1, te.zCoord+j)||
						te.getWorldObj().getBlock(te.xCoord+i, te.yCoord-1, te.zCoord+j).canReplace(te.getWorldObj(), te.xCoord+i, te.yCoord-1, te.zCoord+j,
						te.getWorldObj().getBlockMetadata(te.xCoord+i, te.yCoord-1, te.zCoord+j), new ItemStack(Blocks.water))){
					te.getWorldObj().setBlock(te.xCoord+i, te.yCoord-1, te.zCoord+j, Blocks.water);
				}
			}
		}
	}
}