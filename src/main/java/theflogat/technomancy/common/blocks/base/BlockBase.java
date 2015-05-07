package theflogat.technomancy.common.blocks.base;

import theflogat.technomancy.Technomancy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block{

	protected BlockBase() {
		super(Material.rock);
		setCreativeTab(Technomancy.tabsTM);
		setHardness(2F);
	}
	
	

}
