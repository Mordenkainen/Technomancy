package theflogat.technomancy.common.items.thaumcraft;

import java.util.List;

import theflogat.technomancy.common.items.base.ItemBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


//Currently on hold.
public class ItemEssentiaCannon extends ItemBase {
	
	public ItemEssentiaCannon() {
		setUnlocalizedName(Ref.MOD_PREFIX + Names.essentiaCannon);
	}	
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player){
	  	if (!world.isRemote) {
	  	    if(!player.isSneaking()) {
	  	        if (isCharged(itemstack.getItemDamage())) {
	  	            itemstack.setItemDamage(0);
	  	        }else{
	  	            itemstack.setItemDamage(itemstack.getItemDamage() + 1);
	  	        }
			}			
		}
	return itemstack;
	}
	
	private boolean isCharged(int damage) {
		return damage >= 2;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List info, boolean useExtraInformation) {
		info.add("Charge: " + (itemstack.getItemDamage() + 1));
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int sideHit, float hitVecX, float hitVecY, float hitVecZ) {
	    if (world.isRemote) {
	            if(player.isSneaking()){
	               
	            }
	    }
        return true;        
	}
	
	
	
}

