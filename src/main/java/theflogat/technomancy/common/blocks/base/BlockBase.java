package theflogat.technomancy.common.blocks.base;

import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockBase extends BlockContainer {
	
	public BlockBase() {
		super(Material.iron);
		setCreativeTab(Technomancy.tabsTM);
		setHardness(2F);
	}	
	
	@SideOnly(Side.CLIENT)
	public IIcon icon;
	
	@SideOnly(Side.CLIENT)
	public void RegisterIcons(IIconRegister icon) {
		this.icon = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.condenserBlock);
	}

}