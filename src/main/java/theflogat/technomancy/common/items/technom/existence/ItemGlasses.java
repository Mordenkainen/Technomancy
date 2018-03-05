package theflogat.technomancy.common.items.technom.existence;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemGlasses extends ItemArmor{

	public ItemGlasses() {
		super(Technomancy.existencePower, 0, EntityEquipmentSlot.HEAD);
		setUnlocalizedName(Ref.getId(Names.itemGlasses));
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		player.addPotionEffect(new PotionEffect(Potion.getPotionById(13), 1, 0));
		player.addPotionEffect(new PotionEffect(Potion.getPotionById(12), 1, 2));
		player.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 1, 0));
		player.addPotionEffect(new PotionEffect(Potion.getPotionById(Ids.slowFall), 1, 0));

		if(stack.getTagCompound()==null){
			stack.setTagCompound(new NBTTagCompound());
		}else{
			if(stack.getItemDamage()!=0 && stack.getTagCompound().hasKey("repair")){
				stack.setItemDamage(stack.getItemDamage()-1);
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {
		if(stack.getTagCompound()==null){
			stack.setTagCompound(new NBTTagCompound());
		}else{
			if(stack.getTagCompound().hasKey("repair")){
				l.add("Auto-Repairs");
			}
		}
		super.addInformation(stack, worldIn, l, flagIn);
	}

	@Nullable
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return Ref.MOD_ID + ":textures/armor/" + Names.itemGlasses + ".png";
	}

	/**
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return Ref.MOD_ID + ":textures/armor/" + Names.itemGlasses + ".png";
	}
	
	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(Ref.getAsset(Names.itemGlasses));
	}
	*/
}