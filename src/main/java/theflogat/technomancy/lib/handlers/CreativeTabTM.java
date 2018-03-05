package theflogat.technomancy.lib.handlers;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.util.Ore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

/**
public class CreativeTabTM extends CreativeTabs {

    public CreativeTabTM(int id, String tabLabel) {
        super(id, tabLabel);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getTabLabel() {
        return "technomancy";
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getIconItemStack() {
		if(TMItems.itemMaterial!=null){
			return new ItemStack(TMItems.itemMaterial);
		}else if(Ore.ores.get(0) != null){
			return new ItemStack(Ore.ores.get(0).getPure(), 1, 5);
		}
		return super.getIconItemStack();
	}

    @SideOnly(Side.CLIENT)
	@Override
	public ItemStack getTabIconItem() {
    	return new ItemStack(Blocks.STONE);
	}
}
 */