package theflogat.technomancy.common.items.technom;

import java.util.List;

import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.tiles.base.IUpgradable;
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack items, EntityPlayer player, List l, boolean moreInfo) {
		l.add("Apply to a dynamo: multiply by");
		l.add("4 the RF/t and the Fuel Cost");
		l.add("Apply to a Node Fabricator:");
		l.add("Allow Fabricator to add new aspects");
	}
	
	@Override
	public boolean onItemUse(ItemStack items, EntityPlayer player, World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(w.getTileEntity(x, y, z) instanceof IUpgradable){
			IUpgradable tile = (IUpgradable)w.getTileEntity(x, y, z);
			if(tile.getBoost() == false){
				tile.setBoost(true);
				if(--player.inventory.mainInventory[player.inventory.currentItem].stackSize == 0) {
					player.inventory.mainInventory[player.inventory.currentItem] = null;
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
