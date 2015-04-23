package theflogat.technomancy.proxies;

import theflogat.technomancy.client.renderers.gui.container.ContainerBMProcessor;
import theflogat.technomancy.client.renderers.gui.container.ContainerBOProcessor;
import theflogat.technomancy.client.renderers.gui.container.ContainerTCProcessor;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBMProcessor;
import theflogat.technomancy.common.tiles.botania.machines.TileBOProcessor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileTCProcessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler{

    public void initSounds() {

    }

    public void initRenderers() {

    }
    
    @Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
			case 0: 
				return new ContainerTCProcessor(player.inventory, ((TileTCProcessor)world.getTileEntity(x, y, z)));
			case 1:
				return new ContainerBMProcessor(player.inventory, ((TileBMProcessor)world.getTileEntity(x, y, z)));
			case 2:
				return new ContainerBOProcessor(player.inventory, ((TileBOProcessor)world.getTileEntity(x, y, z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

}
