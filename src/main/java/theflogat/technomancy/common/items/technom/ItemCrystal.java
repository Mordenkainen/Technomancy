package theflogat.technomancy.common.items.technom;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemCrystal extends ItemBlock {
	private static String[] types = new String[] {"nature", "fire", "water", "light", "dark"};
	
	public ItemCrystal(Block crystal) {
		super(crystal);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {
		l.add("Used for " + types[stack.getItemDamage()] + " rituals. Safe for decoration.");
	}
}
