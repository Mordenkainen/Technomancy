package theflogat.technomancy.common.items.technom.existence;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.items.base.ItemBase;
import theflogat.technomancy.common.player.PlayerData;
import theflogat.technomancy.common.player.PlayerData.Affinity;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class ItemTreasure extends ItemBase{

	public ItemTreasure() {
		Technomancy.proxy.registerWithMapper(this, getNames());
	}

	/**
	 * Out of 100
	 */
	public static final int[] rarity = {75, 50, 75};

	public String getUnlocalizedName(ItemStack stack){
		return Ref.getId(Names.treasures[stack.getItemDamage()%Names.treasures.length]);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> l) {
		for(int i=0;i<Names.treasures.length;i++){
			l.add(new ItemStack(this, 1, i));
		}
	}

	/**
	@SideOnly(Side.CLIENT)
	IIcon[] icons;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		icons = new IIcon[Names.treasures.length];
		for(int i=0;i<icons.length;i++){
			icons[i] = reg.registerIcon(Ref.getAsset(Names.treasures[i]));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int meta) {
		return icons[meta%icons.length];
	}
	*/

	public static String[] getNames() {
		String[] str = new String[Names.treasures.length];
		String[] values = Names.treasures;
		for (int i = 0; i < values.length; i++) {
			String type = values[i];
			str[i] = type;
		}
		return str;
	}
	
	public Affinity getAffinity(int meta){
		switch(meta){
		case 0:
			return Affinity.FIRE;
		case 1:
			return Affinity.DARK;
		case 2:
			return Affinity.LIGHT;
		}
		return Affinity.NORMAL;
	}

	@Override
	public void onUpdate(ItemStack stack, World w, Entity player, int pos, boolean inHand) {
		if(!(player instanceof EntityPlayer)){
			return;
		}
		if(getAffinity(stack.getItemDamage())!=Affinity.NORMAL && w.getTotalWorldTime()%20==0){
			PlayerData.addAffinity(w, (EntityPlayer) player, getAffinity(stack.getItemDamage()), 1);
		}
		switch(stack.getItemDamage()){
		case 0:
			((EntityPlayer)player).addPotionEffect(new PotionEffect(Potion.getPotionById(12), 1, 2));
			return;
		case 1:
			((EntityPlayer)player).addPotionEffect(new PotionEffect(Potion.getPotionById(11), 1, 1));
			return;
		case 2:
			((EntityPlayer)player).addPotionEffect(new PotionEffect(Potion.getPotionById(Ids.slowFall), 1, 0));
			return;
		}
	}

	public void onUserHit(LivingHurtEvent event, int itemDamage) {
		switch(itemDamage){
		case 0:
			if(event.getSource().isFireDamage()){
				event.setCanceled(true);
			}
			if(event.getSource() instanceof EntityDamageSource){
				((EntityDamageSource)event.getSource()).getTrueSource().setFire(15);
			}
			return;
		case 1:
			event.getEntityLiving().addPotionEffect(new PotionEffect(Potion.getPotionById(11), 1, 4));
			return;
		case 2:
			if(event.getSource() instanceof EntityDamageSource){
				Entity hit = ((EntityDamageSource)event.getSource()).getTrueSource();
				int knockBack = 3;
				hit.addVelocity(-MathHelper.sin((float)(Math.PI- hit.rotationYaw) * (float)Math.PI / 180.0F) * knockBack * 0.5F, 2.1D,
						MathHelper.cos((float)(Math.PI - hit.rotationYaw) * (float)Math.PI / 180.0F) * knockBack * 0.5F);
			}
			return;
		}
	}

	public void onTreasureDestroyed(World w, double posX, double posY, double posZ, EntityLivingBase entityLiving, int meta){
		switch(meta){
		case 0:
			entityLiving.world.createExplosion(entityLiving, posX, posY, posZ, 30, true);
			int radius = 25;
			ArrayList<EntityLivingBase> l = (ArrayList<EntityLivingBase>) entityLiving.world.getEntitiesWithinAABB(EntityLivingBase.class,
					new AxisAlignedBB(entityLiving.posX-radius, entityLiving.posY-radius, entityLiving.posZ-radius,
							entityLiving.posX+radius, entityLiving.posY+radius, entityLiving.posZ+radius));
			for(EntityLivingBase ent:l){
				if(!ent.getIsInvulnerable()){
					ent.setFire(120);
				}
			}
			return;
		case 1:
			int range = 5;
			ArrayList<EntityLivingBase> l2 = (ArrayList<EntityLivingBase>) entityLiving.world.getEntitiesWithinAABB(EntityLivingBase.class,
					new AxisAlignedBB(entityLiving.posX-range, entityLiving.posY-range, entityLiving.posZ-range,
							entityLiving.posX+range, entityLiving.posY+range, entityLiving.posZ+range));
			for(EntityLivingBase ent:l2){
				if(!ent.getIsInvulnerable()){
					ent.addPotionEffect(new PotionEffect(Potion.getPotionById(4), 1, 2));
				}
			}
			if(!w.isRemote){
				for(int xx=-range;xx<=range;xx++){
					for(int yy=-range;yy<=range;yy++){
						for(int zz=-range;zz<=range;zz++){
							if(w.getBlockState(new BlockPos((int)posX+xx, (int)posY+yy, (int)posZ+zz)).getBlockHardness(w, new BlockPos((int)posX+xx, (int)posY+yy, (int)posZ+zz))!=-1){
								w.setBlockState(new BlockPos((int)posX+xx, (int)posY+yy, (int)posZ+zz), Blocks.OBSIDIAN.getDefaultState());
							}
						}
					}
				}
			}
			return;
		case 2:
			int range2 = 25;
			ArrayList<EntityLivingBase> l3 = (ArrayList<EntityLivingBase>) entityLiving.world.getEntitiesWithinAABB(EntityLivingBase.class,
					new AxisAlignedBB(entityLiving.posX-range2, entityLiving.posY-range2, entityLiving.posZ-range2,
							entityLiving.posX+range2, entityLiving.posY+range2, entityLiving.posZ+range2));
			int knockBack = 3;
			for(EntityLivingBase hit:l3){
				hit.addVelocity(-MathHelper.sin((float)(Math.PI- hit.rotationYaw) * (float)Math.PI / 180.0F) * knockBack * 0.5F, 8D,
						MathHelper.cos((float)(Math.PI - hit.rotationYaw) * (float)Math.PI / 180.0F) * knockBack * 0.5F);
			}
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