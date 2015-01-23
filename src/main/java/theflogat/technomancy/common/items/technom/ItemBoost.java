package theflogat.technomancy.common.items.technom;

import java.util.List;

import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBoost extends Item{
	
	public ItemBoost() {
		setCreativeTab(Technomancy.tabsTM);
		setUnlocalizedName(Ref.getId(Names.itemBoost));
	}
	
	@Override
	public void addInformation(ItemStack items, EntityPlayer player, List l, boolean moreInfo) {
		l.add("Apply to a dynamo: multiply by");
		l.add("4 the RF/t and the Fuel Cost");
	}
	
	@Override
	public boolean onItemUse(ItemStack items, EntityPlayer player, World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(w.getTileEntity(x, y, z)instanceof TileDynamoBase){
			TileDynamoBase tile = (TileDynamoBase)w.getTileEntity(x, y, z);
			if(tile.boost == false){
				tile.boost = true;
				if(items.stackSize==1){
					items = null;
				}else{
					items.stackSize--;
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(Ref.TEXTURE_PREFIX + Names.itemBoost);
	}
}
