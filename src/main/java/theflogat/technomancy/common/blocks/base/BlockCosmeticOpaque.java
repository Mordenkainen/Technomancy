package theflogat.technomancy.common.blocks.base;

import java.util.Random;

import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCosmeticOpaque extends Block {

	public BlockCosmeticOpaque() {
		super(Material.glass);
		setStepSound(Block.soundTypeGlass);
		setHardness(.25F);
		setBlockName(Ref.MOD_PREFIX + Names.cosmeticOpaque);
		this.setCreativeTab(Technomancy.tabsTM);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.cosmeticOpaque);
	}
	
	@Override
	public int quantityDropped(Random par1Random) {
	       return 1;
	   }
	@Override
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
		return 1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

}
