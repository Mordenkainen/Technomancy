package theflogat.technomancy.common.items.technom.existence;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import theflogat.technomancy.common.tiles.technom.existence.TileExistencePylon.Type;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ItemBlockExistencePylon extends ItemBlock{

	public ItemBlockExistencePylon(Block b) {
		super(b);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack items) {
		return Ref.getId(Names.existencePylon + items.getItemDamage());
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> l) {
		for(Type t:Type.allTypes){
			l.add(new ItemStack(this, 1, t.id));
		}
	}

	@Override
	public void addInformation(ItemStack s, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {
		l.add("Tier: " + (s.getItemDamage()+1));
	}
}