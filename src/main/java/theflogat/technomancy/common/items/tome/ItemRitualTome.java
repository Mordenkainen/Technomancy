package theflogat.technomancy.common.items.tome;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class ItemRitualTome extends Item{
	
	public ItemRitualTome() {
		setCreativeTab(Technomancy.tabsTM);
		setUnlocalizedName(Ref.getId(Names.ritualTome));
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World w, EntityPlayer player) {
		player.openGui(Technomancy.instance, 3, w, 0, 0, 0);
		return item;
	}

}
