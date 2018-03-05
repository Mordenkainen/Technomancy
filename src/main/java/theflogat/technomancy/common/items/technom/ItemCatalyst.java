package theflogat.technomancy.common.items.technom;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemCatalyst extends ItemBlock {

	public ItemCatalyst(Block catalyst) {
		super(catalyst);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {
		switch(stack.getItemDamage()){
		case 0:
			l.add("Dark ritual core");
			l.add("Possible sizes: 3x3, 5x5, 9x9");
			break;
		case 1:
			l.add("Light ritual core");
			l.add("Possible sizes: 3x3, 5x5, 9x9");
			break;
		case 2:
			l.add("Fire ritual core");
			l.add("Possible sizes: 3x3, 5x5, 9x9");
			break;
		case 3:
			l.add("Nature ritual core");
			l.add("Possible sizes: 3x3, 5x5, 9x9");
			break;
		case 4:
			l.add("Water ritual core");
			l.add("Possible sizes: 3x3, 5x5, 9x9");
			break;
		}
	}
}
