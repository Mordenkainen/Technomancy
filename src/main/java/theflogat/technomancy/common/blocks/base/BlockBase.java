package theflogat.technomancy.common.blocks.base;

import theflogat.technomancy.Technomancy;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public abstract class BlockBase extends BlockContainer {
	
	public BlockBase() {
		super(Material.iron);
		setCreativeTab(Technomancy.tabsTM);
		setHardness(2F);
	}

}