package theflogat.technomancy.common.blocks.base;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import theflogat.technomancy.Technomancy;

public abstract class BlockContainerBase extends BlockContainer {
	
	public BlockContainerBase() {
		super(Material.IRON);
		setCreativeTab(Technomancy.tabsTM);
		setHardness(2F);
	}
}