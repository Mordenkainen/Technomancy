package theflogat.technomancy.common.items.technom;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.tiles.base.IUpgradable;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class ItemBoost extends Item {

	public static ArrayList<String> upgradeable = new ArrayList<String>();

	public ItemBoost() {
		setCreativeTab(Technomancy.tabsTM);
		setUnlocalizedName(Ref.getId(Names.itemBoost));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack items, EntityPlayer player, List l, boolean moreInfo) {
		l.add("Apply to:");
		for(String s:upgradeable){
			l.add(s);
		}
	}

	@Override
	public boolean onItemUse(ItemStack items, EntityPlayer player, World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
//		if(!w.isRemote){
//			if(w.getTileEntity(x, y, z) instanceof IExistenceProducer){
//				player.addChatComponentMessage(new ChatComponentText("Power:" + ((IExistenceProducer)w.getTileEntity(x, y, z)).getPower()));
//			}
//			if(w.getTileEntity(x, y, z) instanceof TileExistencePylon){
//				player.addChatComponentMessage(new ChatComponentText("Power:" + ((TileExistencePylon)w.getTileEntity(x, y, z)).getPower()));
//			}
//			if(w.getTileEntity(x, y, z) instanceof IExistenceConsumer){
//				player.addChatComponentMessage(new ChatComponentText("Power:" + ((IExistenceConsumer)w.getTileEntity(x, y, z)).getPower()));
//			}
//		}
		if(w.getTileEntity(x, y, z) instanceof IUpgradable){
			IUpgradable tile = (IUpgradable)w.getTileEntity(x, y, z);
			if(tile.getBoost() == false){
				if(!w.isRemote) {
					tile.setBoost(true);
					w.markBlockForUpdate(x, y, z);
				}
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
