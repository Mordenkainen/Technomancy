package theflogat.technomancy.lib.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.items.technom.existence.ItemExistenceGem;
import theflogat.technomancy.common.items.technom.existence.ItemTreasure;
import theflogat.technomancy.common.player.PlayerData;
import theflogat.technomancy.common.potions.TMPotions;
import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.network.PacketHandler;
import theflogat.technomancy.util.Ore;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EventRegister {

	public static Map<Block, Item> buckets = new HashMap<Block, Item>();
	public static Random rand = new Random();
	public static PacketHandler pckHandler = new PacketHandler();

	public EventRegister(){
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderOverlayEvent(RenderTickEvent event){
		if (event.phase==Phase.END && Minecraft.getMinecraft().theWorld!=null){
			PlayerData.renderHUD(Minecraft.getMinecraft().thePlayer);
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onEntityUpdate(LivingUpdateEvent event){
		if(event.entityLiving.getActivePotionEffect(TMPotions.drown)!=null && event.entityLiving.getActivePotionEffect(TMPotions.drown).getDuration()==0){
			event.entityLiving.removePotionEffect(TMPotions.drown.id);
		}else if(event.entityLiving.getActivePotionEffect(TMPotions.slowFall)!=null && event.entityLiving.getActivePotionEffect(TMPotions.slowFall).getDuration()==0){
			event.entityLiving.removePotionEffect(TMPotions.slowFall.id);
		}

		if(event.entityLiving.isPotionActive(Ids.drown)){
			int air = event.entityLiving.getEntityData().getInteger("idrown");
			decreaseAirSupply(air, event.entityLiving);
			event.entityLiving.getEntityData().setInteger("idrown", air);

			if (air == -20){
				air = 0;
				event.entityLiving.getEntityData().setInteger("idrown", air);
				event.entityLiving.attackEntityFrom(DamageSource.drown, 2.0F);
			}
		}else if(event.entityLiving.isPotionActive(Ids.slowFall)){
			if(event.entityLiving.fallDistance>3.0F){
				event.entityLiving.fallDistance = 3.0F;
			}
		}

		if(event.entityLiving instanceof EntityVillager){
			if(event.entityLiving.getEntityData().hasKey("seal") && event.entityLiving.getEntityData().getBoolean("seal")){
				if(!event.entityLiving.getEntityData().hasKey("cooldown")){
					event.entityLiving.getEntityData().setInteger("cooldown", 80);
				}
				int cooldown = event.entityLiving.getEntityData().getInteger("cooldown");
				if(cooldown==0){
					event.entityLiving.getEntityData().setInteger("cooldown", 80);
					event.entityLiving.getEntityData().setBoolean("seal", false);
				}else{
					event.entityLiving.getEntityData().setInteger("cooldown", cooldown-1);
				}
			}
		}
	}

	public int decreaseAirSupply(int i, EntityLivingBase ent){
		int j = EnchantmentHelper.getRespiration(ent);
		return j > 0 && rand.nextInt(j + 1) > 0 ? i : i - 1;
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onSpawn(EntityJoinWorldEvent event){
		if(event.entity instanceof EntityPlayer){
			PlayerData.prepareData((EntityPlayer) event.entity);
		}

		if(event.entity instanceof EntityVillager && !event.entity.getEntityData().hasKey("treasureAttempt")){
			event.entity.getEntityData().setBoolean("treasureAttempt", true);
			ArrayList<Integer> l = new ArrayList<Integer>();
			for(int i=0;i<ItemTreasure.rarity.length;i++){
				if(rand.nextInt(10000)<=ItemTreasure.rarity[i]){
					l.add(i);
				}
			}
			int c=-1;
			for(int i:l){
				if(c==-1){
					c=i;
				}else{
					if(ItemTreasure.rarity[i]<ItemTreasure.rarity[c]){
						c=i;
					}
				}
			}
			if(c!=-1){
				event.entity.getEntityData().setString("treasure", Names.treasures[c]);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onHurt(LivingHurtEvent event){
		if(event.entityLiving instanceof EntityVillager && event.entityLiving.getEntityData().hasKey("treasure")){
			if(!event.entityLiving.getEntityData().hasKey("seal") || !event.entityLiving.getEntityData().getBoolean("seal")){
				TMItems.treasures.onUserHit(event, TMItems.treasures.getTreasureId(event.entityLiving.getEntityData().getString("treasure")));
			}
		}else if(event.entityLiving instanceof EntityPlayer && ((EntityPlayer)event.entityLiving).inventory.hasItem(TMItems.treasures)){
			EntityPlayer player = ((EntityPlayer)event.entityLiving);
			for(int i=0;i<player.inventory.mainInventory.length;i++){
				ItemStack stack = player.inventory.mainInventory[i];
				if(stack!=null && stack.getItem()==TMItems.treasures){
					TMItems.treasures.onUserHit(event, stack.getItemDamage());
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void entityDeath(LivingDeathEvent event){
		if(event.entityLiving instanceof EntityVillager && event.entityLiving.getEntityData().hasKey("treasure")){
			if(!event.entityLiving.getEntityData().hasKey("seal") || !event.entityLiving.getEntityData().getBoolean("seal")){
				TMItems.treasures.onTreasureDestroyed(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, 
						event.entityLiving, TMItems.treasures.getTreasureId(event.entityLiving.getEntityData().getString("treasure")));
			}
		}
		if(event.source instanceof EntityDamageSource){
			EntityDamageSource dmger = (EntityDamageSource)event.source;
			if(dmger.damageType=="player"){
				if(((EntityPlayer)dmger.getEntity()).inventory.hasItem(TMItems.exGem)){
					tryToFillGem((EntityPlayer) dmger.getEntity(), ExistenceConversion.getGem(event.entityLiving));
				}else{
					PlayerData.addExistencePower(dmger.getEntity().worldObj.rand, (EntityPlayer) dmger.getEntity());
				}
			}
		}
	}

	public void tryToFillGem(EntityPlayer entityPlayer, int amt){
		for(int i=0;i<entityPlayer.inventory.mainInventory.length;i++){
			ItemStack items = entityPlayer.inventory.mainInventory[i];
			if(items!=null && items.getItem() instanceof ItemExistenceGem && items.getItemDamage()!=0){
				entityPlayer.inventory.mainInventory[i].setItemDamage(Math.max(items.getItemDamage() - amt, 0));
				return;
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void handleBreakAir(PlayerInteractEvent event) {
		if(CompatibilityHandler.th) {
			if(event.action==Action.LEFT_CLICK_BLOCK){
				if(event.world.getTileEntity(event.x, event.y, event.z) instanceof TileFakeAirNG){
					((TileFakeAirNG) event.world.getTileEntity(event.x, event.y, event.z)).getMain().setAirAndDrop();
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

	private static ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
		Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
		Item bucket = buckets.get(block);

		if (bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
			world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
			return new ItemStack(bucket);
		} else
			return null;
	}
}
