package democretes.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.Technomancy;
import democretes.lib.CreativeTabTM;
import democretes.lib.Names;
import democretes.lib.Ref;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public abstract class BlockBase extends BlockContainer {
	
	public BlockBase(int id) {
		super(id, Material.iron);
		setCreativeTab(Technomancy.tabsTM);
		setHardness(2F);
	}	
	
	@SideOnly(Side.CLIENT)
	public Icon icon;
	
	@SideOnly(Side.CLIENT)
	public void RegisterIcons(IconRegister icon) {
		this.icon = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.condenserBlock);
	}

}