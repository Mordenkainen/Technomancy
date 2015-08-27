package theflogat.technomancy.common.items.technom.existence;

import java.util.ArrayList;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import theflogat.technomancy.common.items.base.ItemBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTreasure extends ItemBase{

	/**
	 * Out of 100
	 */
	public static final int[] rarity = {75};

	public String getUnlocalizedName(ItemStack stack){
		return Ref.getId(Names.treasures[stack.getItemDamage()%Names.treasures.length]);
	}

	@SideOnly(Side.CLIENT)
	IIcon[] icons = new IIcon[Names.treasures.length];

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		for(int i=0;i<icons.length;i++){
			icons[i] = reg.registerIcon(Ref.getAsset(Names.treasures[i]));
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int meta) {
		return icons[meta%icons.length];
	}

	@Override
	public void onUpdate(ItemStack stack, World w, Entity player, int pos, boolean inHand) {
		if(!(player instanceof EntityPlayer)){
			return;
		}
		switch(stack.getItemDamage()){
		case 0:
			((EntityPlayer)player).addPotionEffect(new PotionEffect(Potion.fireResistance.id, 1, 3));
			return;
		}
	}

	public void onUserHit(LivingHurtEvent event, int itemDamage) {
		switch(itemDamage){
		case 0:
			if(event.source.isFireDamage()){
				if(event.entityLiving.getEntityData().getString("treasure").contains(Names.treasures[0])){
					event.setCanceled(true);
				}
			}
			if(event.source instanceof EntityDamageSource){
				if(event.entityLiving.getEntityData().getString("treasure").contains(Names.treasures[0])){
					((EntityDamageSource)event.source).getEntity().setFire(15);
				}
			}
			return;
		}
	}

	public void onTreasureDestroyed(World w, double posX, double posY, double posZ, EntityLivingBase entityLiving, int meta){
		switch(meta){
		case 0:
			entityLiving.worldObj.createExplosion(entityLiving, posX, posY, posZ, 30, true);
			int radius = 25;
			ArrayList<EntityLivingBase> l = (ArrayList<EntityLivingBase>) entityLiving.worldObj.getEntitiesWithinAABB(EntityLivingBase.class,
					AxisAlignedBB.getBoundingBox(entityLiving.posX-radius, entityLiving.posY-radius, entityLiving.posZ-radius,
					entityLiving.posX+radius, entityLiving.posY+radius, entityLiving.posZ+radius));
			for(EntityLivingBase ent:l){
				if(!ent.isEntityInvulnerable()){
					ent.setFire(120);
				}
			}
			return;
		}
	}

	public int getTreasureId(String name){
		for(int i=0;i<Names.treasures.length;i++){
			if(name == Names.treasures[i]){
				return i;
			}
		}
		return -1;
	}

	public ItemStack getTreasure(String name){
		int i = getTreasureId(name);
		return i==-1 ? null : new ItemStack(this, 1, i);
	}
}