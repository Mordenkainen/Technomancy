package theflogat.technomancy.common.blocks.base;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;;

public class BlockCosmeticOpaque extends Block {

	public BlockCosmeticOpaque() {
		super(Material.GLASS);
		setSoundType(SoundType.GLASS);
		setHardness(.25F);
		setUnlocalizedName(Ref.MOD_PREFIX + Names.cosmeticOpaque);
		this.setCreativeTab(Technomancy.tabsTM);
	}

	/**
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.cosmeticOpaque);
	}
	 */
	
	@Override
	public int quantityDropped(Random par1Random) {
		return 1;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
}
