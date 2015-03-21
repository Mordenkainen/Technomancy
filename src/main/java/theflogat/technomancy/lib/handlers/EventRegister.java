package theflogat.technomancy.lib.handlers;

import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.util.Ore;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class EventRegister {

	public EventRegister(){
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void handleBreakAir(PlayerInteractEvent event) {
		if(event.action==Action.LEFT_CLICK_BLOCK){
			if(event.world.getTileEntity(event.x, event.y, event.z) instanceof TileFakeAirNG){
				((TileFakeAirNG) event.world.getTileEntity(event.x, event.y, event.z)).getMain().setAirAndDrop();
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void stitchEventPost(TextureStitchEvent.Post event) {
		if (event.map.getTextureType() == 1) {
			Ore.initColors();
			ConfigHandler.initColorConfigs();
		}
	}
}
