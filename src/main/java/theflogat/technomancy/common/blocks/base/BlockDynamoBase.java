package theflogat.technomancy.common.blocks.base;


public abstract class BlockDynamoBase extends BlockContainerAdvanced {
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}