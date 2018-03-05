package theflogat.technomancy.common.items.technom;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.tiles.base.IUpgradable;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

import javax.annotation.Nullable;

public class ItemBoost extends Item {

	public static ArrayList<String> upgradeable = new ArrayList<String>();

	public ItemBoost() {
		setCreativeTab(Technomancy.tabsTM);
		setUnlocalizedName(Ref.getId(Names.itemBoost));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {
		l.add("Apply to:");
		for(String s:upgradeable){
			l.add(s);
		}
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
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
//		PlayerData.addAffinity(w, player, Affinity.FIRE, 50);
//		if(!w.isRemote){
//			player.addChatComponentMessage(new ChatComponentText("Power:" + PlayerData.getCurrentPower(player) + "/" + PlayerData.getExistenceLevel(player)));
//		}
		if(w.getTileEntity(pos) instanceof IUpgradable){
			IUpgradable tile = (IUpgradable)w.getTileEntity(pos);
			if(tile.getBoost() == false){
				if(!w.isRemote) {
					tile.setBoost(true);
					w.notifyBlockUpdate(pos, w.getBlockState(pos), w.getBlockState(pos), 3);
				}
				if(-player.inventory.mainInventory.get(player.inventory.currentItem).getCount() == 0) {
					player.inventory.mainInventory.set(player.inventory.currentItem, null);
				}
				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.FAIL;
	}

	/**
	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(Ref.TEXTURE_PREFIX + Names.itemBoost);
	}
	*/
}
