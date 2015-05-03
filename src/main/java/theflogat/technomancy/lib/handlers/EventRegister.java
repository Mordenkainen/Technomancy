package theflogat.technomancy.lib.handlers;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import theflogat.technomancy.common.items.technom.ItemCoilCoupler;
import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive;
import theflogat.technomancy.common.tiles.base.IRedstoneSensitive.RedstoneSet;
import theflogat.technomancy.common.tiles.base.IUpgradable;
import theflogat.technomancy.common.tiles.base.IWrenchable;
import theflogat.technomancy.common.tiles.technom.TileItemTransmitter;
import theflogat.technomancy.util.InvHelper;
import theflogat.technomancy.util.Ore;
import theflogat.technomancy.util.ToolWrench;
import theflogat.technomancy.util.WorldHelper;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EventRegister {

	public static Map<Block, Item> buckets = new HashMap<Block, Item>();
	public static HashMap<Item, RedstoneSet> map = new HashMap<Item, RedstoneSet>();

	public EventRegister(){
		MinecraftForge.EVENT_BUS.register(this);
		map.put(Items.gunpowder, RedstoneSet.NONE);
		map.put(Items.redstone, RedstoneSet.HIGH);
		map.put(Item.getItemFromBlock(Blocks.redstone_torch), RedstoneSet.LOW);
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
	public void rightClick(PlayerInteractEvent event){
		if(event.action==Action.RIGHT_CLICK_BLOCK){
			TileEntity te = event.world.getTileEntity(event.x, event.y, event.z);
			if(te instanceof IWrenchable){
				if (ToolWrench.isWrench(event.entityPlayer.getHeldItem()))
					((IWrenchable)te).onWrenched();
			}
			if(te instanceof IRedstoneSensitive){
				if(event.entityPlayer.getHeldItem()!=null && map.containsKey(event.entityPlayer.getHeldItem().getItem()))
					if(map.get(event.entityPlayer.getHeldItem().getItem())!=((IRedstoneSensitive)te).getCurrentSetting()){
						((IRedstoneSensitive)te).setNewSetting(map.get(event.entityPlayer.getHeldItem().getItem()));
						InvHelper.decrItemStack(event.entityPlayer.inventory.mainInventory[event.entityPlayer.inventory.currentItem]);
					}
			}
			if(te instanceof TileItemTransmitter){
				TileItemTransmitter tile = (TileItemTransmitter)te;
				if(!event.entityPlayer.isSneaking()){
					if(event.entityPlayer.getHeldItem()!=null && !(event.entityPlayer.getHeldItem().getItem() instanceof ItemCoilCoupler) && tile.boost
							&& tile.filter==null){
						tile.addFilter(event.entityPlayer.getHeldItem());
					}
				}else{
					if(tile.filter!=null)
						tile.filter = null;
					if(tile.boost){
						tile.toggleBoost();
						if(!event.world.isRemote)
							WorldHelper.dropBoost(event.world, event.x, event.y, event.z);
					}
				}
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

	@SubscribeEvent
	public void onBucketFill(FillBucketEvent event) {
		ItemStack result = fillCustomBucket(event.world, event.target);

		if (result == null)
			return;

		event.result = result;
		event.setResult(Result.ALLOW);
	}

	private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
		Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
		Item bucket = buckets.get(block);

		if (bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
			world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
			return new ItemStack(bucket);
		} else
			return null;
	}
}
