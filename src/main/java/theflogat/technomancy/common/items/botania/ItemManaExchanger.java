package theflogat.technomancy.common.items.botania;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theflogat.technomancy.common.items.base.ItemAdvancedBase;

import javax.annotation.Nullable;

public class ItemManaExchanger extends ItemAdvancedBase{

	public ItemManaExchanger(Block block) {
		super(block);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack items, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {
		super.addInformation(items, worldIn, l, flagIn);
		l.add("Right-Click With A Wrench To Switch");
		l.add("Between Importing And Exporting Mana");
	}
}
