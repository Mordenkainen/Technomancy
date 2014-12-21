package theflogat.technomancy.common.blocks.base;

import java.util.Random;

import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
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
	private IIcon glassIcon;
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		glassIcon = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.cosmeticOpaque);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		return glassIcon;
		
	}
	
	public int quantityDropped(Random par1Random) {
	       return 1;
	   }
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
		return 1;
	}
	
	public boolean isOpaqueCube() {
		return false;
	}
	public boolean renderAsNormalBlock() {
		return false;
	}

}
