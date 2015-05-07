package theflogat.technomancy.common.blocks.botania;

import java.awt.Color;
import java.util.Random;

import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import vazkii.botania.common.Botania;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockManaFluid extends BlockFluidClassic {

	public BlockManaFluid() {
            super(TMBlocks.manaFluid, Material.water);
            setCreativeTab(Technomancy.tabsTM);
            setBlockName(Ref.MOD_PREFIX + Names.manaFluid);
    }
    
    @Override
    public IIcon getIcon(int side, int meta) {
            return Blocks.flowing_water.getIcon(side, 0);
    }
    
    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
            if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
            return super.canDisplace(world, x, y, z);
    }
    
    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
            if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
            return super.displaceIfPossible(world, x, y, z);
    }
    
    @SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random r)	  {
		Color color = new Color(0x00C6FF);
		if(Math.random() > 0.5)
			Botania.proxy.wispFX(world, x + 0.3 + Math.random() * 0.5, y + 0.6 + Math.random() * 0.25, z + Math.random(), color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, (float) Math.random() / 3F, (float) -Math.random() / 25F, 2F);
	}
    
    @SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
    	TMBlocks.manaFluid.setIcons(Blocks.water.getIcon(0, 0), Blocks.flowing_water.getIcon(2, 0));
    }
}
