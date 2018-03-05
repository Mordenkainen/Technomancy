package theflogat.technomancy.common.items.base;

import java.util.ArrayList;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.items.technom.ItemBoost;
import theflogat.technomancy.common.tiles.base.IUpgradable;
import theflogat.technomancy.lib.Conf;

import javax.annotation.Nullable;

public class ItemAdvancedBase extends ItemBlock {

	ArrayList<String> tileInterfaces = new ArrayList<String>();

	public ItemAdvancedBase(Block b) {
		super(b);
		TileEntity te = ((BlockContainerAdvanced)b).createNewTileEntity(null, 0);
		Class<?>[] interfaces = te.getClass().getInterfaces();
		for(Class<?> curInterface: interfaces) {
			String intName = curInterface.getName();
			tileInterfaces.add(intName.substring(intName.lastIndexOf('.') + 1, intName.length()));
		}
		setUnlocalizedName(b.getUnlocalizedName());
		if(tileInterfaces.contains("IUpgradable")) {
			IUpgradable up = (IUpgradable)te;
			String name = String.format(I18n.format(b.getUnlocalizedName() + ".name"));
			ItemBoost.upgradeable.add(name + ": " + up.getInfos());
		}
	}

	@Override
	public void onUpdate(ItemStack items, World w, Entity player, int dmg, boolean held) {
		if(items.getTagCompound()==null) {
			initializeNBT(items);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack items, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {
		if(items==null)
			return;
		try {
			if(items.getTagCompound()==null) {
				initializeNBT(items);
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				((BlockContainerAdvanced)block).getNBTInfo(items.getTagCompound(), (ArrayList<String>) l, items.getItemDamage());
			} else {
				l.add(ChatFormatting.WHITE.toString() + ChatFormatting.ITALIC + I18n.format("info.techno:shift"));
			}
		} catch(Exception e) {
			Conf.ex(e);
		}
	}

	public void initializeNBT(ItemStack items) {
		items.setTagCompound(new NBTTagCompound());
		((BlockContainerAdvanced)block).createNewTileEntity(null, 0).writeToNBT(items.getTagCompound());
	}
}
