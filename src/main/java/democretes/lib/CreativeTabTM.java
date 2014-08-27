package democretes.lib;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.items.TMItems;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;

public class CreativeTabTM extends CreativeTabs {

    public CreativeTabTM(int id, String tabLabel) {
        super(id, tabLabel);
    }

    @SideOnly(Side.CLIENT)
    public String getTabLabel() {
        return "Technomancy";
    }
    
    @SideOnly(Side.CLIENT)
    public int getTabIconItemIndex()
    {
      if(TMItems.itemMaterial!=null){
    	  return TMItems.itemMaterial.itemID;
      }
      return Block.stone.blockID;
    }
}
