package theflogat.technomancy.common.blocks.base;

import theflogat.technomancy.Technomancy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockBase extends Block{

	protected BlockBase() {
		super(Material.ROCK);
		setCreativeTab(Technomancy.tabsTM);
		setHardness(2F);
	}
}
