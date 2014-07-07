package democretes.items;

import java.util.List;

import cofh.util.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import democretes.lib.Names;
import democretes.lib.Ref;

public class ItemProcessedOre extends ItemBase {
	
	String[] processors = {"Thaumcraft", "Botania", "Blood Magic", "Ars Magica", "Witchery", "Totemic" };
	String name;
	
	public ItemProcessedOre(int id) {
		super(id);
		setMaxStackSize(64);
		setHasSubtypes(true);
	}

	public Icon[] itemIcon = new Icon[5];

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		itemIcon[0] = icon.registerIcon(Ref.TEXTURE_PREFIX + "ore0");
		itemIcon[1] = icon.registerIcon(Ref.TEXTURE_PREFIX + "ore1");
		itemIcon[2] = icon.registerIcon(Ref.TEXTURE_PREFIX + "ore2");
		itemIcon[3] = icon.registerIcon(Ref.TEXTURE_PREFIX + "ore3");
		itemIcon[4] = icon.registerIcon(Ref.TEXTURE_PREFIX + "ore4");
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (int i = 0; i < itemIcon.length; i++) {
			ItemStack stack  = new ItemStack(id, 1, i);
			list.add(stack);
		}
	}

	@Override	
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par) {
		return itemIcon[par];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Ref.MOD_PREFIX + name;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		if(!StringHelper.isShiftKeyDown()) {
			list.add(StatCollector.translateToLocal("info.techno:purity") + ": " + (stack.getItemDamage() + 1));
			list.add(StringHelper.getFlavorText("info.techno:shift"));
		}else{
			list.add(StringHelper.localize(this.getUnlocalizedName()));
			list.remove("item.null");
			list.add(StringHelper.getActivationText("info.techno:purity") + ": " + (stack.getItemDamage() + 1));
			list.add(StringHelper.getInfoText("info.techno:process") + ":");
			for(int i = 0; i < processors.length; i++) {
				if(stack.stackTagCompound != null) {
					if(stack.stackTagCompound.getBoolean(processors[i])) {
						list.add(processors[i]);
					}
				}
			}
		}
	}

	public static class ItemProcessedIron extends ItemProcessedOre {	
		
		public ItemProcessedIron(int id) {
			super(id);
			setMaxStackSize(64);
			setHasSubtypes(true);
			this.name = Names.pureIron;
		}
	
		@Override
		@SideOnly(Side.CLIENT)
		public int getColorFromItemStack(ItemStack stack, int par2)	  {
			return 14211288;
		}
	}
	
	public static class ItemProcessedGold extends ItemProcessedOre {	

		public ItemProcessedGold(int id) {
			super(id);
			setMaxStackSize(64);
			setHasSubtypes(true);
			this.name = Names.pureGold;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public int getColorFromItemStack(ItemStack stack, int par2)	  {
			return 14605824;
		}
    
	}
	
	public static class ItemProcessedCopper extends ItemProcessedOre {	

		public ItemProcessedCopper(int id) {
			super(id);
			setMaxStackSize(64);
			setHasSubtypes(true);
			this.name = Names.pureCopper;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public int getColorFromItemStack(ItemStack stack, int par2)	  {
			return 16758834;
		}
	}
	
	public static class ItemProcessedTin extends ItemProcessedOre {	

		public ItemProcessedTin(int id) {
			super(id);
			setMaxStackSize(64);
			setHasSubtypes(true);
			this.name = Names.pureTin;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public int getColorFromItemStack(ItemStack stack, int par2)	  {
			return 12243942;
		}
	}
	
	public static class ItemProcessedSilver extends ItemProcessedOre {	

		public ItemProcessedSilver(int id) {
			super(id);
			setMaxStackSize(64);
			setHasSubtypes(true);
			this.name = Names.pureSilver;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public int getColorFromItemStack(ItemStack stack, int par2)	  {
			return 13163770;
		}    
	}
	
	public static class ItemProcessedLead extends ItemProcessedOre {

		public ItemProcessedLead(int id) {
			super(id);
			setMaxStackSize(64);
			setHasSubtypes(true);
			this.name = Names.pureLead;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public int getColorFromItemStack(ItemStack stack, int par2)	  {
			return 8163006;
		}		
	}
	
	public static class ItemProcessedNickel extends ItemProcessedOre {	

		public ItemProcessedNickel(int id) {
			super(id);
			setMaxStackSize(64);
			setHasSubtypes(true);
			this.name = Names.pureNickel;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public int getColorFromItemStack(ItemStack stack, int par2)	  {
			return 16053453;
		}
	}

}
