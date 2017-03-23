package theflogat.technomancy.common.items.botania;

import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ManaFluid extends Fluid {

    public ManaFluid() {
        super(Names.manaFluid);
        setUnlocalizedName(Ref.MOD_PREFIX + Names.manaFluid);
        FluidRegistry.registerFluid(this);
    }
}
