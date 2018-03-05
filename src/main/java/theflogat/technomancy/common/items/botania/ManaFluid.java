package theflogat.technomancy.common.items.botania;

import net.minecraft.util.ResourceLocation;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ManaFluid extends Fluid {

	public ManaFluid() {
		super(Names.manaFluid, new ResourceLocation("technom", "blocks/manafluid_still"), new ResourceLocation("technom", "blocks/manafluid_flow"));
		setUnlocalizedName(Ref.MOD_PREFIX + Names.manaFluid);
		FluidRegistry.registerFluid(this);
	}
}
