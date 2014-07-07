package democretes.proxies;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import democretes.blocks.gui.container.ContainerBMProcessor;
import democretes.blocks.gui.container.ContainerBOProcessor;
import democretes.blocks.gui.container.ContainerTCProcessor;
import democretes.blocks.machines.tiles.TileBMProcessor;
import democretes.blocks.machines.tiles.TileBOProcessor;
import democretes.blocks.machines.tiles.TileTCProcessor;

public class CommonProxy implements IGuiHandler{

    public void initSounds() {

    }

    public void initRenderers() {

    }
    
    public void essentiaTrail(World world, double x, double y, double z, double tx, double ty, double tz, int color) {
    }

    @Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
			case 0: 
				return new ContainerTCProcessor(player.inventory, ((TileTCProcessor)world.getBlockTileEntity(x, y, z)));
			case 1:
				return new ContainerBMProcessor(player.inventory, ((TileBMProcessor)world.getBlockTileEntity(x, y, z)));
			case 2:
				return new ContainerBOProcessor(player.inventory, ((TileBOProcessor)world.getBlockTileEntity(x, y, z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

}
