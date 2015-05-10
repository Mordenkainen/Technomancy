package theflogat.technomancy.common.blocks.botania.machines;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.BlockProcessor;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import theflogat.technomancy.common.tiles.botania.machines.TileBOProcessor;
import theflogat.technomancy.lib.compat.Botania;
import vazkii.botania.api.wand.IWandHUD;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBOProcessor extends BlockProcessor implements IWandHUD {
	
	public BlockBOProcessor() {
		name = "BO";
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ))
			return true;
		if(player!=null) {
			TileEntity tile = w.getTileEntity(x, y, z);
			if(tile instanceof TileBOProcessor) {
				player.openGui(Technomancy.instance, 2, w, x, y, z);
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
	    if (te instanceof TileProcessorBase && ((TileProcessorBase)te).isActive)	    {
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