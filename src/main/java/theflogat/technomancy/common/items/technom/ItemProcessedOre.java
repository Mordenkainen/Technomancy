package theflogat.technomancy.common.items.technom;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import theflogat.technomancy.common.items.base.ItemBase;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.util.Ore;

import javax.annotation.Nullable;

public class ItemProcessedOre extends ItemBase {
	
	public static final int MAXSTAGE = 6;
	
	protected String[] processors = {"Botania", "Blood Magic", "Ars Magica", "Totemic" };
	protected Ore ore;
	
	public ItemProcessedOre(Ore ore) {
		this.ore = ore;
		setMaxStackSize(64);
		setHasSubtypes(true);
	}

	/*
	@SideOnly(Side.CLIENT)
	public IIcon[] itemIcon;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon) {
		itemIcon = new IIcon[MAXSTAGE];
		for(int i = 0; i < MAXSTAGE; i++){
			itemIcon[i] = icon.registerIcon(Ref.TEXTURE_PREFIX + "ore" + i);
		}
	}
	*/
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		for (int i = 0; i < MAXSTAGE; i++) {
			ItemStack stack  = new ItemStack(this, 1, i);
			items.add(stack);
		}
	}

	/**
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par) {
		return itemIcon[par%itemIcon.length];
	}
	 */

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Ref.MOD_PREFIX + "pure" + ore.oreName().substring(3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.format("item.techno.pure.name") + ore.name();
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World w, EntityPlayer player, EnumHand handIn) {
		ItemStack items = player.getHeldItem(handIn);
		for(int i = 0; i < processors.length; i++) {
			if(items.getTagCompound() != null) {
				if(items.getTagCompound().hasKey(processors[i])) {
					player.sendMessage(new TextComponentString(processors[i] + " " + items.getTagCompound().getInteger(processors[i]) + "x/2x"));
				}
			}
		}
		return super.onItemRightClick(w, player, handIn);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		if(!(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
			list.add(ChatFormatting.BLUE.toString() + ChatFormatting.BLUE.ITALIC +
					I18n.format("info.techno:purity") + ": " + (stack.getItemDamage() + 1));
			list.add(ChatFormatting.BLUE.WHITE.toString() + ChatFormatting.BLUE.ITALIC + I18n.format("info.techno:shift"));
		}else{
			list.add(I18n.format(this.getUnlocalizedName()));
			list.remove("item.null");
			list.add(ChatFormatting.BLUE.BLUE.toString() + ChatFormatting.BLUE.ITALIC +
					I18n.format("info.techno:purity") + ": " + (stack.getItemDamage() + 1));
			list.add(ChatFormatting.BLUE.WHITE.toString() + ChatFormatting.BLUE.ITALIC +
					I18n.format("info.techno:process") + ":");
			for(int i = 0; i < processors.length; i++) {
				if(stack.getTagCompound() != null) {
					if(stack.getTagCompound().hasKey(processors[i])) {
						list.add(processors[i] + " " + stack.getTagCompound().getInteger(processors[i]) + "x/2x");
					}
				}
			}
		}
	}


	/**
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		return ore.color();
	}
	*/
}