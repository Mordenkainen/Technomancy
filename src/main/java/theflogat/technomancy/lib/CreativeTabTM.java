package theflogat.technomancy.lib;

import theflogat.technomancy.common.items.base.TMItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabTM extends CreativeTabs {

    public CreativeTabTM(int id, String tabLabel) {
        super(id, tabLabel);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getTabLabel() {
        return "Technomancy";
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getIconItemStack() {
    	if(TMItems.itemMaterial!=null){
	    	  return new ItemStack(TMItems.itemMaterial);
	      }else if(TMItems.processedIron!=null){
	    	  return new ItemStack(TMItems.processedIron, 1, 5);
	      }
	      return super.getIconItemStack();
    }

    @SideOnly(Side.CLIENT)
	@Override
	public Item getTabIconItem() {
	      return Item.getItemFromBlock(Blocks.stone);
	}
}