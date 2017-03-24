package theflogat.technomancy.common.items.botania;

import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ManaFluid extends Fluid {

    public ManaFluid() {
        super(Names.MANAFLUID);
        setUnlocalizedName(Reference.MOD_PREFIX + Names.MANAFLUID);
        FluidRegistry.registerFluid(this);
    }
}
