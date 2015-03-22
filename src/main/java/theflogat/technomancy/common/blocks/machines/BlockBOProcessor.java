package theflogat.technomancy.common.blocks.machines;

import java.util.Random;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.tiles.TileBOProcessor;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import theflogat.technomancy.lib.compat.Botania;
import vazkii.botania.api.wand.IWandHUD;

@Optional.Interface(iface = "vazkii.botania.api.wand.IWandHUD", modid = "Botania")
public class BlockBOProcessor extends BlockProcessor implements IWandHUD {
	
	public BlockBOProcessor() {
		this.name = "BO";
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(player != null) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileBOProcessor) {
				player.openGui(Technomancy.instance, 2, world, x, y, z);
			}
		}		
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileBOProcessor();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random r)	  {
		TileEntity te = world.getTileEntity(x, y, z);
	    if ((te != null) && ((TileProcessorBase)te).isActive)	    {
	    	float f = x + 0.6F;
    		float f1 = (y + 0.2F + r.nextFloat() * 5.0F / 16.0F) + 0.1F;
    		float f2 = z + 0.6F;
    		float f3 = 0.52F;
    		float f4 = r.nextFloat() * 0.5F - 0.25F;	
	    	Botania.sparkle(world, (double)f - f3, f1, f2 + f4, r);
	    	Botania.sparkle(world, (double)f + f3, f1, f2 + f4, r);
	    	Botania.sparkle(world, (double)f + f4, f1, f2 - f3, r);
	    	Botania.sparkle(world, (double)f + f4, f1, f2 + f3, r);
	    }
	}

	@Override
	public void renderHUD(Minecraft minecraft, ScaledResolution res, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileBOProcessor) {
			((TileBOProcessor)tile).renderHUD(minecraft, res);
		}
	}
}