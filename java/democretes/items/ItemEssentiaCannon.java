package democretes.items;

import java.util.List;

import democretes.Technomancy;
import democretes.lib.Names;
import democretes.lib.Ref;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


//Currently on hold.
public class ItemEssentiaCannon extends ItemBase {
	
	
    

	public ItemEssentiaCannon(int id) {
		super(id);
		setUnlocalizedName(Ref.MOD_PREFIX + Names.essentiaCannon);
	}	
	
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
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List info, boolean useExtraInformation) {
		info.add("Charge: " + (itemstack.getItemDamage() + 1));
	}
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int sideHit, float hitVecX, float hitVecY, float hitVecZ) {
	    if (world.isRemote) {
	            if(player.isSneaking()){
	               
	            }
	    }
        return true;        
	}
	
	
	
}

