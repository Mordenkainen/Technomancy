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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.items.technom.existence.ItemExistenceGem;
import theflogat.technomancy.common.items.technom.existence.ItemTreasure;
import theflogat.technomancy.common.player.PlayerData;
import theflogat.technomancy.common.potions.TMPotions;
import theflogat.technomancy.lib.Conf;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.network.PacketHandler;
import theflogat.technomancy.util.Ore;

public class EventRegister {

	public static Map<Block, Item> buckets = new HashMap<Block, Item>();
	public static Random rand = new Random();

	public EventRegister(){
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderOverlayEvent(TickEvent.RenderTickEvent event){
		Minecraft mc = FMLClientHandler.instance().getClient();
		if ((mc.inGameHasFocus || mc.currentScreen == null) && !Minecraft.getMinecraft().gameSettings.showDebugInfo) {
			if (event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().world!= null && Conf.showHUD){
				PlayerData.renderHUD(Minecraft.getMinecraft().player);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onEntityUpdate(LivingUpdateEvent event){
		if(event.getEntityLiving().getActivePotionEffect(TMPotions.drown)!=null && event.getEntityLiving().getActivePotionEffect(TMPotions.drown).getDuration()==0){
			event.getEntityLiving().removePotionEffect(TMPotions.drown);
		}else if(event.getEntityLiving().getActivePotionEffect(TMPotions.slowFall)!=null && event.getEntityLiving().getActivePotionEffect(TMPotions.slowFall).getDuration()==0){
			event.getEntityLiving().removePotionEffect(TMPotions.slowFall);
		}

		if(event.getEntityLiving().isPotionActive(TMPotions.drown)){
			int air = event.getEntityLiving().getEntityData().getInteger("idrown");
			decreaseAirSupply(air, event.getEntityLiving());
			event.getEntityLiving().getEntityData().setInteger("idrown", air);

			if (air == -20){
				air = 0;
				event.getEntityLiving().getEntityData().setInteger("idrown", air);
				event.getEntityLiving().attackEntityFrom(DamageSource.DROWN, 2.0F);
			}
		}else if(event.getEntityLiving().isPotionActive(TMPotions.slowFall)){
			if(event.getEntityLiving().fallDistance>3.0F){
				event.getEntityLiving().fallDistance = 3.0F;
			}
		}

		if(event.getEntityLiving() instanceof EntityVillager){
			if(event.getEntityLiving().getEntityData().hasKey("seal") && event.getEntityLiving().getEntityData().getBoolean("seal")){
				if(!event.getEntityLiving().getEntityData().hasKey("cooldown")){
					event.getEntityLiving().getEntityData().setInteger("cooldown", 80);
				}
				int cooldown = event.getEntityLiving().getEntityData().getInteger("cooldown");
				if(cooldown==0){
					event.getEntityLiving().getEntityData().setInteger("cooldown", 80);
					event.getEntityLiving().getEntityData().setBoolean("seal", false);
				}else{
					event.getEntityLiving().getEntityData().setInteger("cooldown", cooldown-1);
				}
			}
		}
	}

	public int decreaseAirSupply(int i, EntityLivingBase ent){
		int j = EnchantmentHelper.getRespirationModifier(ent);
		return j > 0 && rand.nextInt(j + 1) > 0 ? i : i - 1;
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onSpawn(EntityJoinWorldEvent event){
		if(event.getEntity() instanceof EntityPlayer){
			PlayerData.prepareData((EntityPlayer) event.getEntity());
		}

		if(Ids.treasures){
			if(event.getEntity() instanceof EntityVillager && !event.getEntity().getEntityData().hasKey("treasureAttempt")){
				event.getEntity().getEntityData().setBoolean("treasureAttempt", true);
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
					event.getEntity().getEntityData().setString("treasure", Names.treasures[c]);
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onHurt(LivingHurtEvent event){

		if(Ids.treasures){
			if(event.getEntityLiving() instanceof EntityVillager && event.getEntityLiving().getEntityData().hasKey("treasure")){
				if(!event.getEntityLiving().getEntityData().hasKey("seal") || !event.getEntityLiving().getEntityData().getBoolean("seal")){
					TMItems.treasures.onUserHit(event, TMItems.treasures.getTreasureId(event.getEntityLiving().getEntityData().getString("treasure")));
				}
			}else if(event.getEntityLiving() instanceof EntityPlayer && ((EntityPlayer)event.getEntityLiving()).inventory.hasItemStack(new ItemStack(TMItems.treasures))){
				EntityPlayer player = ((EntityPlayer)event.getEntityLiving());
				for(int i=0;i<player.inventory.mainInventory.size();i++){
					ItemStack stack = player.inventory.mainInventory.get(i);
					if(stack!=null && stack.getItem()==TMItems.treasures){
						TMItems.treasures.onUserHit(event, stack.getItemDamage());
					}
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void entityDeath(LivingDeathEvent event){
		if(Ids.treasures){
			if(event.getEntityLiving() instanceof EntityVillager && event.getEntityLiving().getEntityData().hasKey("treasure")){
				if(!event.getEntityLiving().getEntityData().hasKey("seal") || !event.getEntityLiving().getEntityData().getBoolean("seal")){
					TMItems.treasures.onTreasureDestroyed(((EntityVillager) event.getEntityLiving()).world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ,
							event.getEntityLiving(), TMItems.treasures.getTreasureId(event.getEntityLiving().getEntityData().getString("treasure")));
				}
			}
		}
		if(event.getSource() instanceof EntityDamageSource){
			EntityDamageSource dmger = (EntityDamageSource)event.getSource();
			if(dmger.damageType=="player"){
				if(((EntityPlayer)dmger.getTrueSource()).inventory.hasItemStack(new ItemStack(TMItems.exGem))){
					tryToFillGem((EntityPlayer)dmger.getTrueSource(), ExistenceConversion.getGem(event.getEntityLiving()));
				}else{
					PlayerData.addExistencePower(dmger.getTrueSource().world.rand, (EntityPlayer) dmger.getTrueSource());
				}
			}
		}
	}

	public void tryToFillGem(EntityPlayer entityPlayer, int amt){
		for(int i=0;i<entityPlayer.inventory.mainInventory.size();i++){
			ItemStack items = entityPlayer.inventory.mainInventory.get(i);
			if(items!=null && items.getItem() instanceof ItemExistenceGem && items.getItemDamage()!=0){
				entityPlayer.inventory.mainInventory.get(i).setItemDamage(Math.max(items.getItemDamage() - amt, 0));
				return;
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void handleBreakAir(PlayerInteractEvent event) {
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void stitchEventPost(TextureStitchEvent.Post event) {
		if (event.getMap().getGlTextureId() == 1) {
			Ore.init();
			ConfigHandler.initColorConfigs();
		}
	}

	/**
	@SubscribeEvent
	public void onBucketFill(FillBucketEvent event) {
		ItemStack result = fillCustomBucket(event.getWorld(), event.getTarget());

		if (result == null)
			return;

		event.setFilledBucket(result);
		event.setResult(Event.Result.ALLOW);
	}


	private static ItemStack fillCustomBucket(World world, RayTraceResult pos) {
		Block block = world.getBlockState(pos.getBlockPos()).getBlock();
		Item bucket = buckets.get(block);

		if (bucket != null) {
			world.setBlockToAir(pos.getBlockPos());
			return new ItemStack(bucket);
		} else
			return null;
	}
	*/
}
