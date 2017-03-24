package theflogat.technomancy.proxies;

import theflogat.technomancy.client.gui.container.ContainerBMProcessor;
import theflogat.technomancy.client.gui.container.ContainerBOProcessor;
import theflogat.technomancy.client.gui.container.ContainerTCProcessor;
import theflogat.technomancy.common.tiles.bloodmagic.machines.TileBMProcessor;
import theflogat.technomancy.common.tiles.botania.machines.TileBOProcessor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileTCProcessor;
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
                return new ContainerTCProcessor(player.inventory, ((TileTCProcessor) world.getTileEntity(x, y, z)));
            case 1:
                return new ContainerBMProcessor(player.inventory, ((TileBMProcessor) world.getTileEntity(x, y, z)));
            case 2:
                return new ContainerBOProcessor(player.inventory, ((TileBOProcessor) world.getTileEntity(x, y, z)));
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
