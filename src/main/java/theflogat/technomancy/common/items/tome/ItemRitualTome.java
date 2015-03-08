package theflogat.technomancy.common.items.tome;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class ItemRitualTome extends Item{
	
	public ItemRitualTome() {
		setCreativeTab(Technomancy.tabsTM);
		setUnlocalizedName(Ref.getId(Names.ritualTome));
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World w, EntityPlayer player) {
		player.openGui(Technomancy.instance, 3, w, 0, 0, 0);
		return item;
	}
	
	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(Ref.getAsset(Names.ritualTome));
	}

	public static class Res{
		public static final String E = ".png";
		
		public static final String BHT1 = "blackHoleT1"+E;
		public static final String BHT2 = "blackHoleT2"+E;
		public static final String BHT3 = "blackHoleT3"+E;
		
		public static final String CIT1 = "caveInT1"+E;
		public static final String CIT2 = "caveInT2"+E;
		public static final String CIT3 = "caveInT3"+E;
		
		public static final String PUT1 = "purificationT1"+E;
		public static final String PUT2 = "purificationT2"+E;
		public static final String PUT3 = "purificationT3"+E;
	}
	
}
