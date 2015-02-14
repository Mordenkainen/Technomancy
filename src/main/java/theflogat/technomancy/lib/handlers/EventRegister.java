package theflogat.technomancy.lib.handlers;

import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.common.tiles.technom.TileFakeAir;
import theflogat.technomancy.util.Ore;
import theflogat.technomancy.util.RedstoneSet;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class EventRegister {

	public EventRegister(){
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void handleLowState(PlayerInteractEvent event) {
		if(event.action==Action.RIGHT_CLICK_BLOCK){
			if(event.world.getTileEntity(event.x, event.y, event.z) instanceof TileDynamoBase){
				TileDynamoBase tile = (TileDynamoBase) event.world.getTileEntity(event.x, event.y, event.z);
				if(event.entityPlayer.getHeldItem().getItem()==Item.getItemFromBlock(Blocks.redstone_torch) && tile.set != RedstoneSet.LOW){
					if(event.entityPlayer.getHeldItem().stackSize==1){
						event.entityPlayer.inventory.mainInventory[event.entityPlayer.inventory.currentItem] = null;
					}else{
						event.entityPlayer.inventory.mainInventory[event.entityPlayer.inventory.currentItem].stackSize--;
					}
					tile.set = RedstoneSet.LOW;
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void handleBreakAir(PlayerInteractEvent event) {
		if(event.action==Action.LEFT_CLICK_BLOCK){
			if(event.world.getTileEntity(event.x, event.y, event.z) instanceof TileFakeAir){
				((TileFakeAir) event.world.getTileEntity(event.x, event.y, event.z)).getMain().setAirAndDrop();
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
