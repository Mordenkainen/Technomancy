package theflogat.technomancy.proxies;

import theflogat.technomancy.client.gui.container.ContainerProcessor;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

    public void initSounds() {

    }

    public void initRenderers() {

    }

    @Override
    public Object getServerGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        switch (id) {
            case 0:
                return new ContainerProcessor(player.inventory, ((TileProcessorBase) world.getTileEntity(x, y, z)), 50, 107, 84, 142);
            case 1:
                return new ContainerProcessor(player.inventory, ((TileProcessorBase) world.getTileEntity(x, y, z)), 52, 107, 56, 114);
            case 2:
                return new ContainerProcessor(player.inventory, ((TileProcessorBase) world.getTileEntity(x, y, z)), 52, 108, 56, 114);
            default:
                break;
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        return null;
    }

}
